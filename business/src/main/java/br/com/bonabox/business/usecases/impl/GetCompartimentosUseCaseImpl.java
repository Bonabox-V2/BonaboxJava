package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.api.models.GetCompartimentoLabelResponse;
import br.com.bonabox.business.api.models.GetCompartimentosResponse;
import br.com.bonabox.business.api.models.GetTipoCompartimentoDisponivelResponse;
import br.com.bonabox.business.dataproviders.*;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.CompartimentoStatusTempRequest;
import br.com.bonabox.business.domain.CompartimentoStatusTempResponse;
import br.com.bonabox.business.domain.EstadoBox;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;
import br.com.bonabox.business.usecases.GetCompartimentosUseCase;
import br.com.bonabox.business.usecases.ex.CompartimentoUseCaseException;
import br.com.bonabox.business.util.GenerateCodigoTipoCompartimento;
import br.com.bonabox.business.util.GenerateUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetCompartimentosUseCaseImpl implements GetCompartimentosUseCase {

	private final CompartimentoDataProvider compartimentoDataProvider;
	private final StatusEntregaDataProvider statusEntregaDataProvider;
	private final BoxDataProvider boxDataProvider;
	private final EntregaDataProvider entregaDataProvider;
	private final GenerateUID generateUID;
	
	@Autowired
	private UnidadeDataProvider unidadeDataProvider;
	
	@Autowired
	private BlocoDataProvider blocoDataProvider;

	public static Map<String, CompartimentoDisponiveisSuporte> MAP;

	@Value("${entrega.sessao.ttl:0}")
	private int entregaSessaoTtl;

	@Value("${entrega.sessao.ttl.erro.mensagem}")
	private String entregaSessaoTtlErroMensagem;

	@Value("${entrega.sessao.ttl.erro.codigo}")
	private String entregaSessaoTtlErroCodigo;

	static {
		MAP = new HashMap<String, CompartimentoDisponiveisSuporte>();
	}

	public GetCompartimentosUseCaseImpl(CompartimentoDataProvider compartimentoDataProvider,
			StatusEntregaDataProvider statusEntregaDataProvider, BoxDataProvider boxDataProvider,
			EntregaDataProvider entregaDataProvider, @Qualifier("generate-uid-compartimento") GenerateUID generateUID) {
		this.compartimentoDataProvider = compartimentoDataProvider;
		this.statusEntregaDataProvider = statusEntregaDataProvider;
		this.boxDataProvider = boxDataProvider;
		this.entregaDataProvider = entregaDataProvider;
		this.generateUID = generateUID;

		this.generateUID.build(new GenerateCodigoTipoCompartimento());
	}

	@Override
	public List<GetCompartimentosResponse> execute(String numeroSerial) throws CompartimentoUseCaseException {
		try {
			// Consulta dados box principal
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");

			// Lista estados disponíveis do codigo do box recuperado através do número
			// serial neste mpetodo
			List<EstadoBox> todosEstadosBox = compartimentoDataProvider.getAllEstadoBoxByOwner(box.getBoxId());
			
			int ESTADO_BOX_LIVRE = 1;
			int SITUACAO_DEPOSITADO = 2;
			//todosEstadosBox = todosEstadosBox.stream().filter(f -> f.getEstadoAtividade().getEstadoBoxId() == SITUACAO_DEPOSITADO).collect(Collectors.toList());
			
			/*if(todosEstadosBox.isEmpty()) {
				throw new CompartimentoUseCaseException("Compartimentos indisponíveis para uso!", HttpStatus.NOT_FOUND);
			}*/
			
			//if(true) {
				List<EstadoBox> todosEstadosBoxOcupados = todosEstadosBox.stream().filter(f -> f.getEstadoAtividade().getEstadoBoxId() > 1).collect(Collectors.toList());
				List<Integer> compartimentoIdList = todosEstadosBoxOcupados.stream().map(m -> m.getCompartimentoId()).collect(Collectors.toList());
				
				List<StatusEntregaDataWebClient> statusTempList = statusEntregaDataProvider
						.consultarStatusEntregaByCompartimentoLista(compartimentoIdList, SITUACAO_DEPOSITADO);
				
				List<StatusEntregaDataWebClient> novo = statusEntregaDataProvider
						.consultarStatusEntregaByCompartimentoLista(compartimentoIdList, 3);
				
				statusTempList = this.add(statusTempList, novo);
				
				novo = statusEntregaDataProvider
						.consultarStatusEntregaByCompartimentoLista(compartimentoIdList, 4);
				statusTempList = this.add(statusTempList, novo);
				
				novo = statusEntregaDataProvider
						.consultarStatusEntregaByCompartimentoLista(compartimentoIdList, 5);
				statusTempList = this.add(statusTempList, novo);
				
			//}
			
			final List<StatusEntregaDataWebClient> statusTempListSave = statusTempList;
			
			/*List<Integer> compartimentoIdList = todosEstadosBox.stream().map(m -> m.getCompartimentoId())
					.collect(Collectors.toList());
			
			List<StatusEntregaDataWebClient> statusTempList = statusEntregaDataProvider
					.consultarStatusEntregaByCompartimentoLista(compartimentoIdList, SITUACAO_DEPOSITADO);*/
			
			List<CompartimentoStatusTempRequest> listaSave = new ArrayList<CompartimentoStatusTempRequest>();
			
			List<GetCompartimentosResponse> retorno = todosEstadosBox.stream().map(m -> {
				String compartimentoId = "admin_" + RandomStringUtils.randomAlphabetic(28);
				
				String bloco = "";
				String unidade = "";
				LocalDateTime dataHora = null;
				//Somente salva os compartimentos com estado depositado
				if (m.getEstadoAtividade().getEstadoBoxId() == SITUACAO_DEPOSITADO) {
					//System.out.println(m.getCompartimentoId());
					
					Optional<StatusEntregaDataWebClient> op = statusTempListSave.stream()
							.filter(f -> f.getCodigoNumeroPortaBox() == m.getCompartimentoId()).findFirst();

					String entregaIdTemp = op.isPresent() ? op.get().getEntregaId() : "";
					
					try {
						if (op.isPresent()) {
							//System.out.println(op.get().getCodigoBloco());
							bloco = blocoDataProvider.consultarBloco(op.get().getCodigoBloco()).getLabel();
							//System.out.println(bloco);
							unidade = unidadeDataProvider.consultarUnidade(op.get().getCodigoUnidade())
									.getLabelUnidade();
							
							dataHora = op.get().getDataHora();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					listaSave.add(new CompartimentoStatusTempRequest(compartimentoId, m.getCompartimentoId(),
							m.getBoxId(), m.getEstadoAtividade().getEstadoBoxId(), entregaIdTemp,
							m.getCompartimentoCom1Id(), m.getLabelPorta()));
				}
				
				return new GetCompartimentosResponse(m.getTipoBox(), compartimentoId, m.getLabelPorta(),
						m.getCompartimentoTamanho(), m.getEstadoAtividade(), unidade, bloco, dataHora);
				
			}).collect(Collectors.toList());

			List<CompartimentoStatusTempResponse> resultado = compartimentoDataProvider
					.criarComIdAleatorioLista(listaSave);

			if(resultado == null) {
				throw new CompartimentoUseCaseException("Erro ao criar lista de id aleatórios na base de dados.");
			}
			
			return retorno;

		}catch (CompartimentoUseCaseException e) {
			throw e;
		} catch (Exception e) {
			throw new CompartimentoUseCaseException(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<StatusEntregaDataWebClient> add (List<StatusEntregaDataWebClient> listaAtual, List<StatusEntregaDataWebClient> listaNovo){
			
			List<StatusEntregaDataWebClient> resultado = new ArrayList<>();
			
			if(listaNovo.isEmpty()) {
				return listaAtual;
			}
			
			StatusEntregaDataWebClient [] arrayAtual = new StatusEntregaDataWebClient[listaAtual.size()];
			StatusEntregaDataWebClient [] arrayNovo = new StatusEntregaDataWebClient[listaNovo.size()];
			
			arrayAtual = listaAtual.toArray(arrayAtual);
			arrayNovo = listaNovo.toArray(arrayNovo);
			
			for(int i=0; i<arrayAtual.length; i++) {
				for(int a=0; a<arrayNovo.length; a++) {
					if(arrayAtual != null && arrayAtual[i].getEntregaId().equals(arrayNovo[a].getEntregaId())) {
						arrayAtual[i] = null;
						break;
					}
				}
			}
			
			for(StatusEntregaDataWebClient s : arrayAtual) {
				if(s != null) {
					resultado.add(s);
				}
			}
		    
		    return resultado;
		}

	@Override
	public List<GetTipoCompartimentoDisponivelResponse> execute(String numeroSerial, String entregaId)
			throws CompartimentoUseCaseException {
		try {

			// Consulta dados da entrega
			EntregaDataWebClientResponse entregaDataWebClient = entregaDataProvider.consultar(entregaId);

			// Verifica se a data da criação do código de entrega ainda é válida
			if (LocalDateTime.now().isAfter(entregaDataWebClient.getDataHoraCriacao().plusMinutes(entregaSessaoTtl))) {
				throw new CompartimentoUseCaseException(entregaSessaoTtlErroMensagem, HttpStatus.BAD_REQUEST);
			}

			// Consulta dados do box
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");

			// Valida se o código do box informado na criação da entrega é o mesmo que o
			// informado neste método
			if (entregaDataWebClient.getCodigoBox() != box.getBoxId()) {
				throw new CompartimentoUseCaseException("7009");
			}

			// Lista os estados disponíveis do codigo do box recuperado através do número
			// serial neste método
			List<EstadoBox> disponiveis = compartimentoDataProvider.getEstadoBoxByOwner(box.getBoxId());

			// ### Temporário
			if (disponiveis.isEmpty()) {

				List<GetTipoCompartimentoDisponivelResponse> retorno = new ArrayList<>();

				Arrays.asList("P", "M", "G").stream().forEach(tipo -> {
						GetTipoCompartimentoDisponivelResponse disponivelResponse = new GetTipoCompartimentoDisponivelResponse(
								tipo, false, this.getDescricaoTipo(tipo), "");
						retorno.add(disponivelResponse);
				});
				
				return retorno;
			}

			List<String> lista = disponiveis.stream().map(m -> m.getCompartimentoTamanho()).distinct()
					.collect(Collectors.toList());
			
			List<GetTipoCompartimentoDisponivelResponse> retorno = new ArrayList<>();

			long parent = Long.parseLong(RandomStringUtils.randomNumeric(16));

			Integer condominioId = disponiveis.stream().map(m -> m.getCondominioId()).distinct().findFirst().get();

			Arrays.asList("P", "M", "G").stream().forEach(tipo -> {
				boolean contem = lista.contains(tipo);
				if (contem) {
					String codigo = "disp_"+generateUID.generatingCodigo(26);
					GetTipoCompartimentoDisponivelResponse disponivelResponse = new GetTipoCompartimentoDisponivelResponse(
							tipo, contem, this.getDescricaoTipo(tipo), codigo);

					retorno.add(disponivelResponse);

					MAP.put(codigo, new CompartimentoDisponiveisSuporte(disponivelResponse, numeroSerial, entregaId,
							tipo, parent, box.getBoxId(), LocalDateTime.now(), condominioId));
				} else {
					GetTipoCompartimentoDisponivelResponse disponivelResponse = new GetTipoCompartimentoDisponivelResponse(
							tipo, contem, this.getDescricaoTipo(tipo), "");

					retorno.add(disponivelResponse);
				}
			});

			return retorno;

		} catch (CompartimentoUseCaseException e) {
			throw e;
		} catch (Exception e) {
			throw new CompartimentoUseCaseException(e, "", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {

		}
	}
	
	private String getDescricaoTipo(String tipo) {
		
		switch (tipo) {
		case "P":
			return String.format("Altura: %s \nLargura: %s \nProfundidade: %s \n", "9 cm", "39 cm", "47 cm");
		case "M":
			return String.format("Altura: %s \nLargura: %s \nProfundidade: %s \n", "15 cm", "39 cm", "47 cm");
		case "G":
			return String.format("Altura: %s \nLargura: %s \nProfundidade: %s \n", "45 cm", "39 cm", "47 cm");
		}
		
		return "";
	}

	@Override
	public GetCompartimentoLabelResponse execute(String numeroSerial, String entregaId, final String codigo)
			throws CompartimentoUseCaseException {

		try {

			// Recupera objeto compartimento guardado em memória
			CompartimentoDisponiveisSuporte compartimentoSuporte = MAP.get(codigo);

			if (compartimentoSuporte == null)
				throw new CompartimentoUseCaseException("Dados incorretos.", HttpStatus.NOT_FOUND);

			if (compartimentoSuporte.getEntregaId().equals(entregaId)
					&& compartimentoSuporte.getNumeroSerial().equals(numeroSerial)) {
				// Verificar uma forma de cálculo de hash para o app fazer a validação de dados

				List<EstadoBox> disponiveis = this.getBoxesDisponiveis(compartimentoSuporte.getBoxId(),
						compartimentoSuporte.getTipoCompartimento());

				String newCodigo = "comp_"+generateUID.generatingCodigo(26);

				String label = getLabel(disponiveis);
		
				if(!disponiveis.isEmpty()) {
					EstadoBox box = disponiveis.get(0);
					CompartimentoStatusTempRequest compartimento = new CompartimentoStatusTempRequest(
							newCodigo, box.getCompartimentoId(), box.getBoxId(), box.getEstadoAtividade().getEstadoBoxId(), entregaId,
							box.getCompartimentoCom1Id(), label);
					
					compartimentoDataProvider.criarComIdAleatorio(compartimento);
				}
				
				//MAP_PORTA.put(newCodigo, entregaPorta);

				MAP.values().removeIf(value -> value.getParent() == compartimentoSuporte.getParent());

				return new GetCompartimentoLabelResponse(label, newCodigo);
			} else {
				throw new CompartimentoUseCaseException("Dados incorretos.", HttpStatus.BAD_REQUEST);
			}
		} catch (CompartimentoUseCaseException e) {
			throw new CompartimentoUseCaseException(e);
		} catch (Exception e) {
			throw new CompartimentoUseCaseException(e, "Dados incorretos.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public boolean temCompartimentosLivres(String numeroSerial) throws CompartimentoUseCaseException {
		try {

			// Consulta dados box principal
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");

			List<EstadoBox> todosEstadosBox = compartimentoDataProvider.getAllEstadoBoxByOwner(box.getBoxId());

			int ESTADO_BOX_LIVRE = 1;

			todosEstadosBox = todosEstadosBox.stream()
					.filter(f -> f.getEstadoAtividade().getEstadoBoxId() == ESTADO_BOX_LIVRE)
					.collect(Collectors.toList());

			return !todosEstadosBox.isEmpty();

		} catch (Exception e) {
			throw new CompartimentoUseCaseException(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public List<GetCompartimentosResponse> obterAllEstadoBoxByOwner(String numeroSerial)
			throws CompartimentoUseCaseException {
		try {
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");

			List<EstadoBox> todosEstadosBox = compartimentoDataProvider.getAllEstadoBoxByOwner(box.getBoxId());

			List<GetCompartimentosResponse> retorno = todosEstadosBox.stream().map(m -> {
				
				GetCompartimentosResponse resp = new GetCompartimentosResponse(m.getTipoBox(), "", m.getLabelPorta(),
						m.getCompartimentoTamanho(), m.getEstadoAtividade(), "", "", null);
				resp.setCompartimentoCom1Id(m.getCompartimentoCom1Id());
				return resp;
				
			}).collect(Collectors.toList());

			return retorno;
		} catch (Exception e) {
			throw new CompartimentoUseCaseException(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private StatusEntregaDataWebClient getStatusEntrega(String codigoEntrega) throws DataProviderException {
		List<StatusEntregaDataWebClient> statusEntregaList = statusEntregaDataProvider
				.consultarStatusEntrega(codigoEntrega);

		return statusEntregaList.stream()
				.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst().get();
	}

	private List<EstadoBox> getBoxesDisponiveis(Integer boxId, String tipoCompartimento) throws DataProviderException {

		List<EstadoBox> disponiveis = compartimentoDataProvider.getEstadoBoxByOwner(boxId);

		return disponiveis.stream().filter(f -> f.getCompartimentoTamanho().equals(tipoCompartimento))
				.collect(Collectors.toList());
	}

	private int getCompartimentoId(List<EstadoBox> disponiveis) {
		if (disponiveis.isEmpty())
			return -1;
		return disponiveis.get(0).getCompartimentoId();
	}

	private String getLabel(List<EstadoBox> disponiveis) {
		if (disponiveis.isEmpty())
			return "";
		return disponiveis.get(0).getLabelPorta();
	}

	static class EntregaPorta {

		private String numeroSerial;
		private String entregaId;
		private String tipoCompartimento;
		private Integer compartimentoId;
		private Integer boxId;
		private LocalDateTime dateTime;
		private String label;

		private Integer condominioId;
		private Integer alaId;
		private Integer blocoId;
		private Integer unidadeId;

		public EntregaPorta(String numeroSerial, String entregaId, String tipoCompartimento, Integer compartimentoId,
				Integer boxId, LocalDateTime dateTime, String label) {
			super();
			this.numeroSerial = numeroSerial;
			this.entregaId = entregaId;
			this.tipoCompartimento = tipoCompartimento;
			this.compartimentoId = compartimentoId;
			this.boxId = boxId;
			this.dateTime = dateTime;
			this.label = label;
		}

		public String getNumeroSerial() {
			return numeroSerial;
		}

		public void setNumeroSerial(String numeroSerial) {
			this.numeroSerial = numeroSerial;
		}

		public String getEntregaId() {
			return entregaId;
		}

		public void setEntregaId(String entregaId) {
			this.entregaId = entregaId;
		}

		public String getTipoCompartimento() {
			return tipoCompartimento;
		}

		public void setTipoCompartimento(String tipoCompartimento) {
			this.tipoCompartimento = tipoCompartimento;
		}

		public Integer getCompartimentoId() {
			return compartimentoId;
		}

		public void setCompartimentoId(Integer compartimentoId) {
			this.compartimentoId = compartimentoId;
		}

		public Integer getBoxId() {
			return boxId;
		}

		public void setBoxId(Integer boxId) {
			this.boxId = boxId;
		}

		public LocalDateTime getDateTime() {
			return dateTime;
		}

		public void setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Integer getCondominioId() {
			return condominioId;
		}

		public void setCondominioId(Integer condominioId) {
			this.condominioId = condominioId;
		}

		public Integer getAlaId() {
			return alaId;
		}

		public void setAlaId(Integer alaId) {
			this.alaId = alaId;
		}

		public Integer getBlocoId() {
			return blocoId;
		}

		public void setBlocoId(Integer blocoId) {
			this.blocoId = blocoId;
		}

		public Integer getUnidadeId() {
			return unidadeId;
		}

		public void setUnidadeId(Integer unidadeId) {
			this.unidadeId = unidadeId;
		}
	}

}

class CompartimentoSuporte {

	private final EstadoBox estadoBox;
	private final BoxDataResponse boxDataResponse;

	public CompartimentoSuporte(EstadoBox estadoBox, BoxDataResponse boxDataResponse) {
		super();
		this.estadoBox = estadoBox;
		this.boxDataResponse = boxDataResponse;
	}

	public EstadoBox getEstadoBox() {
		return estadoBox;
	}

	public BoxDataResponse getBoxDataResponse() {
		return boxDataResponse;
	}

}

class CompartimentoDisponiveisSuporte {

	private final GetTipoCompartimentoDisponivelResponse compartimentoDisponivelResponse;
	private final String numeroSerial;
	private final String entregaId;
	private final String tipoCompartimento;
	private final Integer boxId;
	private final long parent;
	private final LocalDateTime dateTime;
	private Integer condominioId;

	public CompartimentoDisponiveisSuporte(GetTipoCompartimentoDisponivelResponse compartimentoDisponivelResponse,
			String numeroSerial, String entregaId, String tipoCompartimento, long parent, Integer boxId,
			LocalDateTime dateTime, Integer condominioId) {
		super();
		this.compartimentoDisponivelResponse = compartimentoDisponivelResponse;
		this.numeroSerial = numeroSerial;
		this.entregaId = entregaId;
		this.tipoCompartimento = tipoCompartimento;
		this.parent = parent;
		this.boxId = boxId;
		this.dateTime = dateTime;
		this.condominioId = condominioId;
	}

	public Integer getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(Integer condominioId) {
		this.condominioId = condominioId;
	}

	public GetTipoCompartimentoDisponivelResponse getCompartimentoDisponivelResponse() {
		return compartimentoDisponivelResponse;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public String getEntregaId() {
		return entregaId;
	}

	public String getTipoCompartimento() {
		return tipoCompartimento;
	}

	public Integer getBoxId() {
		return boxId;
	}

	public long getParent() {
		return parent;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

}
