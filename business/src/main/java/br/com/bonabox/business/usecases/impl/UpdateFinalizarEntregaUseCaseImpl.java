package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.api.filter.MDC;
import br.com.bonabox.business.dataproviders.*;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.dataproviders.impl.EnviarEmailAWSDataProviderImpl;
import br.com.bonabox.business.domain.CompartimentoStatusTempRequest;
import br.com.bonabox.business.domain.CompartimentoStatusTempResponse;
import br.com.bonabox.business.domain.FinalizarEntrega;
import br.com.bonabox.business.domain.webclient.*;
import br.com.bonabox.business.usecases.NotificacaoUseCase;
import br.com.bonabox.business.usecases.UpdateFinalizarEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UpdateFinalizarEntregaUseCaseImpl implements UpdateFinalizarEntregaUseCase {

	private final StatusEntregaDataProvider statusEntregaDataProvider;
	private final ValidateDataProvider validateDataProvider;
	//private final EnviarNotificacaoDataProvider enviarNotificacaoDataProvider;
	private final PessoaDataProvider pessoaDataProvider;
	private final GenerateQRCodeDataProvider generateQRCodeDataProvider;
	private final CondominioDataProvider condominioDataProvider;
	private final CompartimentoDataProvider compartimentoDataProvider;
	private final EntregaDataProvider entregaDataProvider;
	private final EntregadorDataProvider entregadorDataProvider;
	private final EmpresaEntregaDataProvider empresaEntregaDataProvider;
	private final BoxDataProvider boxDataProvider;
	private final UnidadeDataProvider unidadeDataProvider;
	private final BlocoDataProvider blocoDataProvider;
	
	private final EnviarEmailAWSDataProviderImpl enviarEmailAWSDataProviderImpl;

	@Value("${entrega.mensagem.inquilino.retirada}")
	private String mensagemInquilinoRetirada;
	
	@Value("${entrega.mensagem.entregador.entrega}")
	private String mensagemEntregadorEntrega;
	
	@Value("${entrega.mensagem.admin}")
	private String [] telefonesAdmin;
	
	@Value("${mensagem.server.notificacao}")
	private String serverNotificacao;

	//public static Map<String, QRCodeDataType> MAP_QRCODE = new HashMap<>();
	
	@Autowired
	private NotificacaoUseCase notificacaoUseCase;

	public UpdateFinalizarEntregaUseCaseImpl(StatusEntregaDataProvider statusEntregaDataProvider,
			ValidateDataProvider validateDataProvider,
			//EnviarNotificacaoDataProvider enviarNotificacaoDataProvider,
			PessoaDataProvider pessoaDataProvider, GenerateQRCodeDataProvider generateQRCodeDataProvider,
			CondominioDataProvider condominioDataProvider, CompartimentoDataProvider compartimentoDataProvider,
			EntregaDataProvider entregaDataProvider,
			EntregadorDataProvider entregadorDataProvider,
			EmpresaEntregaDataProvider empresaEntregaDataProvider,
			BoxDataProvider boxDataProvider,
			UnidadeDataProvider unidadeDataProvider,
			BlocoDataProvider blocoDataProvider,
			EnviarEmailAWSDataProviderImpl enviarEmailAWSDataProviderImpl) {
		this.statusEntregaDataProvider = statusEntregaDataProvider;
		this.validateDataProvider = validateDataProvider;
		//this.enviarNotificacaoDataProvider = enviarNotificacaoDataProvider;
		this.pessoaDataProvider = pessoaDataProvider;
		this.generateQRCodeDataProvider = generateQRCodeDataProvider;
		this.condominioDataProvider = condominioDataProvider;
		this.compartimentoDataProvider = compartimentoDataProvider;
		this.entregaDataProvider = entregaDataProvider;
		this.entregadorDataProvider = entregadorDataProvider;
		this.empresaEntregaDataProvider = empresaEntregaDataProvider;
		this.boxDataProvider = boxDataProvider;
		this.unidadeDataProvider = unidadeDataProvider;
		this.blocoDataProvider = blocoDataProvider;
		
		this.enviarEmailAWSDataProviderImpl = enviarEmailAWSDataProviderImpl;
	}

	@Override
	public FinalizarEntrega execute(final String codigoEntrega, final String codigoAleatorio) throws EntregaUseCaseException {

		try {
			
			// Recupera código da porta atráves de código aleatório enviado para o front
			CompartimentoStatusTempResponse compartimentoStatusTempResponse = compartimentoDataProvider
					.consultarCompatimentoByIdAleatorio(codigoAleatorio);

			if (compartimentoStatusTempResponse == null
					|| !compartimentoStatusTempResponse.getEntregaId().equals(codigoEntrega)) {
				throw new EntregaUseCaseException("ERRO_CODIGO_ENTREGA_INVALIDO");
			}

			List<StatusEntregaDataWebClient> statusEntregaList = statusEntregaDataProvider
					.consultarStatusEntrega(codigoEntrega);

			StatusEntregaDataWebClient ultimoStatusEntregaDataWebClient = statusEntregaList.stream()
					.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst().get();

			final int SITUACAO_CRIACAO_ANDAMENTO = 1;
			if(ultimoStatusEntregaDataWebClient.getSituacaoId() != SITUACAO_CRIACAO_ANDAMENTO) {
				throw new EntregaUseCaseException("Não é permitido finalizar a entrega '" + codigoEntrega
						+ "', pois a mesma não se encontra em andamento!", HttpStatus.FORBIDDEN);
			}
			
			final int SITUACAO_DEPOSITADO = 2;
			final int ZERO = 0;
			ultimoStatusEntregaDataWebClient = new StatusEntregaDataWebClient(codigoEntrega, ZERO, SITUACAO_DEPOSITADO,
					ultimoStatusEntregaDataWebClient.getCodigoEntregador(), LocalDateTime.now(),
					ultimoStatusEntregaDataWebClient.getCodigoAla(), ultimoStatusEntregaDataWebClient.getCodigoBloco(),
					ultimoStatusEntregaDataWebClient.getCodigoUnidade(), compartimentoStatusTempResponse.getCompartimentoId(),
					ultimoStatusEntregaDataWebClient.getNomeMorador());

			// 1 - Recupera workflow_entrega
			// 2 - Valida dados
			// 3 - Atualiza tabela de entrega
			// 4 - Avisa inquilino
			// 5 - Avisa entregador
			// 6 - Avisa empresa responsável pela entrega

			StatusEntregaDataWebClient dataWebClient = statusEntregaDataProvider.atualizar(ultimoStatusEntregaDataWebClient); //Roolback
			// {box_id}/{new_estado_atividade}
			int AGUARDANDO_MORADOR = 2;
			compartimentoDataProvider.update(compartimentoStatusTempResponse.getCompartimentoId(), AGUARDANDO_MORADOR); //Roolback
			// dataWebClient = entregaDataProvider.criar(statusEntregaDataWebClient);
			Objects.requireNonNull(dataWebClient, "ERRO_ATUALIZAR_STATUS_ENTREGA");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String dataHoraDeposito = LocalDateTime.now().format(formatter);
			
			//this.notificar(compartimentoStatusTempResponse, statusEntregaDataWebClient, codigoEntrega, dataWebClient, dataHoraDeposito);
			// Necessário por conta de chamadas paralelizadas
			DataMDC dataMdc = MDC.MAPS.get(Thread.currentThread().getName());
			notificacaoUseCase.build(dataMdc).notificar(compartimentoStatusTempResponse,
					ultimoStatusEntregaDataWebClient, codigoEntrega, dataWebClient, dataHoraDeposito);
			
			return new FinalizarEntrega(codigoEntrega, dataHoraDeposito);

		} catch (DataProviderException | NullPointerException e) {
			throw new EntregaUseCaseException(e);
		}catch (EntregaUseCaseException e) {
			throw e;
		} catch (Exception e) {
			throw new EntregaUseCaseException(e);
		}
	}
	
	
	@Async
	public void notificar(CompartimentoStatusTempResponse compartimentoStatusTempResponse,
			StatusEntregaDataWebClient statusEntregaDataWebClient, String codigoEntrega,
			StatusEntregaDataWebClient dataWebClient, String dataHoraDeposito) {
		
		try {
			
			final int SITUACAO_DEPOSITADO = 2;
			
				// ###### Temporário
				String numeroSerial = "OY6URTF5E3SQ20G";
				// ###### Precisa implementar o método abaixo
				//numeroSerial = boxDataProvider.getBox(compartimentoStatusTempResponse.getBoxId()).getNumeroSerial();
				
				GenerateDataWebClientRequest generate = new GenerateDataWebClientRequest();
				generate.setEntregaId(compartimentoStatusTempResponse.getEntregaId());
				generate.setNumeroSerial(numeroSerial);// ###### Modificar
				//generate.setPorta(codigoAleatorio);//Código gerado aleatoriamente
				generate.setPorta(compartimentoStatusTempResponse.getLabelPorta());
				
				// Cria token para retirada de entrega (encomenda)
				GenerateDataWebClientResponse clientResponse = validateDataProvider.generate(generate); //Roolback
	
				String qrCodeRandomCode = clientResponse.getCodigoRandomico();
	
				// 7 - Gera QRCode
				//###### Solução temporária
				//QRCodeDataType qrCodeDataType = new QRCodeDataType(qrCodeRandomCode, generateQRCodeDataProvider.generate(qrCodeRandomCode), LocalDateTime.now()); //Roolback
				//MAP_QRCODE.put(qrCodeRandomCode, qrCodeDataType);
				
				// Cria QRCode na base de dados
				generateQRCodeDataProvider.generate(qrCodeRandomCode);
				
				// Será recuperado no método de retirada por QRCode
				List<CompartimentoStatusTempRequest> tempRequestsList = new ArrayList<CompartimentoStatusTempRequest>();
				tempRequestsList.add(new CompartimentoStatusTempRequest(
						qrCodeRandomCode,
						compartimentoStatusTempResponse.getCompartimentoId(), compartimentoStatusTempResponse.getBoxId(),
						SITUACAO_DEPOSITADO,
						compartimentoStatusTempResponse.getEntregaId(),
						compartimentoStatusTempResponse.getCompartimentoCom1Id(), compartimentoStatusTempResponse.getLabelPorta()));
				
				compartimentoDataProvider.criarComIdAleatorioLista(tempRequestsList); //Roolback
	
				// 8 - Recupera dados dos envolvidos
				// 9 - Enviar notificação para os envolvidos (inquilino e entregador)
	
				EntregaDataWebClientResponse entrega = entregaDataProvider.consultar(codigoEntrega);
	
				List<PessoaDataWebClient> pessoaDataWebClient = pessoaDataProvider.consultarPessoaMoradorPrincipalPorUnidadeList(
						entrega.getCodigoCondominio(), statusEntregaDataWebClient.getCodigoAla(),
						statusEntregaDataWebClient.getCodigoBloco(), statusEntregaDataWebClient.getCodigoUnidade());
	
				// Consulta dados do condomínio
				CondominioDataWebClientRequest condominioDataWebClientRequest = condominioDataProvider
						.consultarCondominio(entrega.getCodigoCondominio());
				EntregadorDataWebClientResponse entregador = entregadorDataProvider
						.consultar(dataWebClient.getCodigoEntregador());
				
				
				List<EmpresaEntregaDataWebClient> lista = empresaEntregaDataProvider.consultarTodos();
	
				EmpresaEntregaDataWebClient empresa = lista.stream()
						.filter(f -> f.getEmpresaId() == entrega.getCodigoEmpresaEntregdora()).findFirst().get();
				BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");
				
				String empresaEntregadora = empresa.getNome();
				String NomeEntregador = entregador.getNome();
				String localCondominioNome = condominioDataWebClientRequest.getNome();
				String nomeBox = box.getNome();
				//String linkMapaLocalizacaoBox = "https://www.google.com/maps/dir//-23.6314262,-46.7341012";
				//String linkQRCodeRetiradaEntrega = "http://bonabox.hadesgestor.com.br/" + qrCodeRandomCode;
				//String linkQRCodeRetiradaEntrega = "http://api.bonabox.com.br/box/api/v1/entrega/qrcode/" + qrCodeRandomCode;
				
				String urlShort = String.format(serverNotificacao, qrCodeRandomCode);
				urlShort = this.generateShorterUrl(urlShort, RandomStringUtils.randomAlphanumeric(8)).getUrl()
						.getShortLink();

				String urlShortEntregador = String.format(serverNotificacao, qrCodeRandomCode);
				urlShortEntregador = generateShorterUrl(urlShortEntregador, RandomStringUtils.randomAlphanumeric(8))
						.getUrl().getShortLink();
				
				String codigoRetiradaEntrega = clientResponse.getCodigo();
				
				Map<String, String> mapEnvioMorador = criarEnvioMorador(statusEntregaDataWebClient, pessoaDataWebClient, localCondominioNome,
						codigoRetiradaEntrega, urlShortEntregador);
				
	//enviarNotificacaoDataProvider.enviarLista(mapEnvioMorador);
				
				UnidadeDataWebClient unidade = unidadeDataProvider.consultarUnidade(dataWebClient.getCodigoUnidade());
				BlocoDataWebClient bloco = blocoDataProvider.consultarBloco(dataWebClient.getCodigoBloco());
				
				// Mensagem entregador
				String mensagemEntregador = String.format(
						"Comprovante de entrega em %s\r" + "Data: %s\r" + "Código da entrega: %s\r" + "Detalhes: %s",
						localCondominioNome, dataHoraDeposito, entrega.getEntregaId(), urlShortEntregador);
	
	//enviarNotificacaoDataProvider.enviar(mensagemEntregador, (entregador.getDdi() + entregador.getDdd() + entregador.getTelefone()));
				
				for(String telefone : telefonesAdmin) {
					String mensagemAdmin = String.format(
							"Registro de entrega\r" + "%s, box %s\r" + "Ala %s, bl %s, uni %s\r" + "%s\r"
									+ "Morador e Entregador avisados",
							localCondominioNome, nomeBox, "Única", bloco.getLabel(), unidade.getLabelUnidade(),
							entrega.getEntregaId());
	//enviarNotificacaoDataProvider.enviar(mensagemAdmin, telefone);
				}
				
				/*String htmlBody = enviarEmailAWSDataProviderImpl.getFile()
						.replace("{codigoRetirada}", codigoRetiradaEntrega).replace("{saudacao}", "Olá Leandro")
						.replace("{nomeBox}", nomeBox).replace("{nomeCondominio}", localCondominioNome)
						.replace("{dataDeposito}", dataHoraDeposito).replace("{nomeTransportadora}", empresaEntregadora);
				*/
				//enviarEmailAWSDataProviderImpl.enviar("informa@mail.bonabox.com.br",
				//		null, "Comunicado de entrega", htmlBody, "This email was sent through Amazon SES " + "using the AWS SDK for Java.");
				
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, String> criarEnvioMorador(StatusEntregaDataWebClient statusEntregaDataWebClient,
			List<PessoaDataWebClient> pessoaDataWebClient, String localCondominioNome, String codigoRetiradaEntrega,
			String urlShort) {
		// Mensagem morador
		List<PessoaDataWebClient> pessoaListEnviarMensagem = new ArrayList<PessoaDataWebClient>();
		
		for(PessoaDataWebClient pessoa : pessoaDataWebClient) {
			String [] array = pessoa.getNome().trim().split(" ");
			String nome = array.length == 0 ? "" : array[0];
			if (!nome.trim().isBlank()
					&& nome.trim().equalsIgnoreCase(statusEntregaDataWebClient.getNomeMorador())) {
				pessoaListEnviarMensagem.add(pessoa);
			}
		}
		
		if(pessoaListEnviarMensagem.isEmpty()) {
			pessoaListEnviarMensagem = pessoaDataWebClient;
		}
		
		String mensagemMorador;
		if(statusEntregaDataWebClient.getNomeMorador() == null || statusEntregaDataWebClient.getNomeMorador().trim().isEmpty()) {
			mensagemInquilinoRetirada = "Há uma nova encomenda aguardando para ser retirada em %s.\r"
					+ "Código de retirada: %s\r"
					+ "Detalhes: %s";
			
			mensagemMorador = String.format(mensagemInquilinoRetirada, localCondominioNome, codigoRetiradaEntrega, urlShort);
		} else {
			mensagemInquilinoRetirada = "Há uma nova encomenda aguardando para ser retirada em %s.\r"
					+ "Para: %s\r"
					+ "Código de retirada: %s\r"
					+ "Detalhes: %s";
			
			mensagemMorador = String.format(mensagemInquilinoRetirada, localCondominioNome,
					statusEntregaDataWebClient.getNomeMorador().toUpperCase(), codigoRetiradaEntrega, urlShort);
		}

		Map<String, String> map = new HashMap<String, String>();
		for(PessoaDataWebClient pessoa : pessoaListEnviarMensagem) {
			map.put(pessoa.getNumeroCelular(), mensagemMorador);
		}
		
		return map;
	}
	
	private OutPutShorter generateShorterUrl(String myUrl, String customNameAlias) {

		String output = "";

		try {

			URL url = new URL("https://cutt.ly/api/api.php?key=9d013fdfc1c839f3bae6fdfe42a7d53ce91f5&short="
					.concat(myUrl).concat("&name=").concat(customNameAlias));
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			//System.out.println("Output from Server .... \n");
			String result = "";
			while ((result = br.readLine()) != null) {
				output += result;
			}

			conn.disconnect();
			return new ObjectMapper().readValue(output, OutPutShorter.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new OutPutShorter(new MyUrl());
	}


	static class QRCodeDataType {

		private String qrCodeRandomCode;
		private BufferedImage imageQrCode;
		private LocalDateTime dateTime;

		public QRCodeDataType(String qrCodeRandomCode, BufferedImage imageQrCode, LocalDateTime dateTime) {
			super();
			this.qrCodeRandomCode = qrCodeRandomCode;
			this.imageQrCode = imageQrCode;
			this.dateTime = dateTime;
		}

		public String getQrCodeRandomCode() {
			return qrCodeRandomCode;
		}

		public void setQrCodeRandomCode(String qrCodeRandomCode) {
			this.qrCodeRandomCode = qrCodeRandomCode;
		}

		public BufferedImage getImageQrCode() {
			return imageQrCode;
		}

		public void setImageQrCode(BufferedImage imageQrCode) {
			this.imageQrCode = imageQrCode;
		}

		public LocalDateTime getDateTime() {
			return dateTime;
		}

		public void setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
		}
	}
	
}

class OutPutShorter{
	private MyUrl url;

	public OutPutShorter() {
	}
	
	public OutPutShorter(MyUrl url) {
		super();
		this.url = url;
	}

	public MyUrl getUrl() {
		return url;
	}

	public void setUrl(MyUrl url) {
		this.url = url;
	}
	
	
}

class MyUrl {
	
	private String status;
	private String fullLink;
	private String date;
	private String shortLink = "";
	private String title;
	
	public MyUrl() {
		super();
	}
	public MyUrl(String status, String fullLink, String date, String shortLink, String title) {
		super();
		this.status = status;
		this.fullLink = fullLink;
		this.date = date;
		this.shortLink = shortLink;
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFullLink() {
		return fullLink;
	}
	public void setFullLink(String fullLink) {
		this.fullLink = fullLink;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
