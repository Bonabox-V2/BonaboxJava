package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.CompartimentoDataProvider;
import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import br.com.bonabox.business.dataproviders.StatusEntregaDataProvider;
import br.com.bonabox.business.dataproviders.ValidateDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.CompartimentoStatusTempRequest;
import br.com.bonabox.business.domain.CompartimentoStatusTempResponse;
import br.com.bonabox.business.domain.Retirada;
import br.com.bonabox.business.domain.StatusEntrega;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;
import br.com.bonabox.business.domain.webclient.ValidateDataWebClientResponse;
import br.com.bonabox.business.usecases.CreateRetiradaUseCase;
import br.com.bonabox.business.usecases.UpdateEntregaUseCase;
import br.com.bonabox.business.usecases.ex.RetiradaUseCaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CreateRetiradaUseCaseImpl implements CreateRetiradaUseCase {

	private final ValidateDataProvider validateDataProvider;
	private final StatusEntregaDataProvider statusEntregaDataProvider;
	private final CompartimentoDataProvider compartimentoDataProvider;
	private final UpdateEntregaUseCase updateEntregaUseCase;
	
	@Value("${entrega.mensagem.admin}")
	private String[] telefonesAdmin;
	
	@Autowired
	@Qualifier("sms")
	private EnviarNotificacaoDataProvider enviarNotificacaoDataProvider;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public CreateRetiradaUseCaseImpl(ValidateDataProvider validateDataProvider,
			StatusEntregaDataProvider statusEntregaDataProvider, CompartimentoDataProvider compartimentoDataProvider,
			UpdateEntregaUseCase updateEntregaUseCase) {
		this.validateDataProvider = validateDataProvider;
		this.statusEntregaDataProvider = statusEntregaDataProvider;
		this.compartimentoDataProvider = compartimentoDataProvider;
		this.updateEntregaUseCase = updateEntregaUseCase;
	}

	@Override
	public Retirada execute(final String numeroSerial, final String codigoAcesso) throws RetiradaUseCaseException {
		try {

			int ESTADO_COMPARTIMENTO_DEPOSITADO = 2;
			
			CompartimentoStatusTempResponse compartimento = compartimentoDataProvider.consultarCompatimentoByIdAleatorio(codigoAcesso);
			
			if(compartimento.getCodigoEstadoBoxAtividade() != ESTADO_COMPARTIMENTO_DEPOSITADO) {
				logger.error("Código de retirada {} não encontrada na base de dados", codigoAcesso);
				throw new RetiradaUseCaseException("Compartimento indisponível para retirada!");
			}
			
			// Recupera porta que deve ser aberta para retirada de mercadoria
			ValidateDataWebClientResponse validateDataWebClientResponse = validateDataProvider.validate(numeroSerial,
					codigoAcesso);

			// Busca ultimo estado da tabela status_entrega para o codigo de entrega
			// informado
			List<StatusEntregaDataWebClient> listStatusEntrega = statusEntregaDataProvider
					.consultarStatusEntrega(validateDataWebClientResponse.getEntregaId());

			// Filtra por data hora a lista de StatusEntrega para pegar o registro mais novo
			// da lista
			StatusEntregaDataWebClient statusEntregaMaisNovo = listStatusEntrega.stream()
					.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst().get();

			// Altera estado do objeto com o código que informa que a entrega foi concluída
			// pelo inquilino
			int FINALIZADO_MORADOR = 3;
			statusEntregaMaisNovo.setSituacaoId(FINALIZADO_MORADOR);

			// Inclui novo registro na tabela status_entrega
			StatusEntregaDataWebClient statusEntregaDataWebClient = statusEntregaDataProvider
					.atualizar(statusEntregaMaisNovo);
			Objects.requireNonNull(statusEntregaDataWebClient);

			// String compartimentoId = RandomStringUtils.randomAlphabetic(32);
			String compartimentoId = codigoAcesso;
			// GetCompartimentosUseCaseImpl.MAP_PORTA.put(key, value);

			/*
			 * CompartimentoStatusTempResponse consulta = compartimentoDataProvider
			 * .consultarCompatimentoByIdAleatorio(codigoAcesso);
			 * consulta.setCompartimentoIdAleatorio(compartimentoId);
			 * 
			 * CompartimentoStatusTempRequest criar = new CompartimentoStatusTempRequest(
			 * consulta.getCompartimentoIdAleatorio(), consulta.getCompartimentoId(),
			 * consulta.getBoxId(), consulta.getCodigoEstadoBoxAtividade(),
			 * consulta.getEntregaId(), consulta.getCompartimentoCom1Id(),
			 * consulta.getLabelPorta());
			 * 
			 * compartimentoDataProvider.criarComIdAleatorio(criar);
			 */

			return new Retirada(validateDataWebClientResponse.getPorta(), compartimentoId);

		} catch (DataProviderException e) {
			throw new RetiradaUseCaseException(e);
		} catch (Exception e) {
			throw new RetiradaUseCaseException(e);
		}
	}

	@Override
	public String execute(String token) throws RetiradaUseCaseException {
		try {
			
			String validate = validateDataProvider.getValidateByToken(token);
			
			Retorno retorno = new ObjectMapper().readValue(validate, Retorno.class);
			
			CompartimentoStatusTempResponse compartimento = compartimentoDataProvider.consultarCompatimentoByIdAleatorio(retorno.getCodigoRandomico());
			
			int ESTADO_COMPARTIMENTO_DEPOSITADO = 2;
			if(compartimento.getCodigoEstadoBoxAtividade() != ESTADO_COMPARTIMENTO_DEPOSITADO) {
				logger.error("Token de retirada {} não encontrada na base de dados", token);
				throw new RetiradaUseCaseException("Compartimento indisponível para retirada!");
			}
			
			return validate;
		} catch (RetiradaUseCaseException | DataProviderException e) {
			logger.error(e.getLocalizedMessage());
			throw new RetiradaUseCaseException(e);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new RetiradaUseCaseException(e);
		}
	}

	@Override
	public String executeByToken(String numeroSerial, String token) throws RetiradaUseCaseException {
		try {

			String tokenPin = validateDataProvider.getValidateByToken(token);

			ValidateDataWebClientResponse validateDataWebClientResponse = validateDataProvider.validate(numeroSerial,
					tokenPin);

			return validateDataWebClientResponse.getPorta();

		} catch (DataProviderException e) {
			throw new RetiradaUseCaseException(e);
		} catch (Exception e) {
			throw new RetiradaUseCaseException(e);
		}
	}

	@Override
	public String executeByTokenPin(String numeroSerial, String tokenPin) throws RetiradaUseCaseException {
		try {

			ValidateDataWebClientResponse validateDataWebClientResponse = validateDataProvider.validate(numeroSerial,
					tokenPin);

			return validateDataWebClientResponse.getPorta();

		} catch (DataProviderException e) {
			throw new RetiradaUseCaseException(e);
		} catch (Exception e) {
			throw new RetiradaUseCaseException(e);
		}
	}

	@Override
	public boolean liberarPorta(String numeroSerial, String codigoCompartimentoRetirada, int boxId, String entregaId,
			String origem) throws RetiradaUseCaseException {

		try {
			// Fazer Dupla checagem
			CompartimentoStatusTempResponse compartimento = compartimentoDataProvider
					.consultarCompatimentoByIdAleatorio(codigoCompartimentoRetirada);

			// if (compartimento.getEntregaId() != null &&
			// "".equals(compartimento.getEntregaId())) {
			// }

			// compartimentoDataProvider.consultarCompartimentos(compartimento.getBoxId()).stream().filter(f
			// -> f.get);
			String codigoEntrega;
			if ("ADMINISTRADOR".equals(origem)) {
				codigoEntrega = compartimentoDataProvider
						.consultarCompatimentoByIdAleatorio(codigoCompartimentoRetirada).getEntregaId();
			} else {
				codigoEntrega = validateDataProvider.validate(numeroSerial, codigoCompartimentoRetirada).getEntregaId();
			}

			//String codigoEntrega = validateDataProvider.validate(numeroSerial, codigoCompartimentoRetirada).getEntregaId();
			
			StatusEntregaDataWebClient statusEntregaDataWebClient = statusEntregaDataProvider
					.consultarStatusEntrega(codigoEntrega).stream()
					.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst().get();

			// compartimentoDataProvider.atualizarComIdAleatorio(compartimento);
			int FINALIZADO_INQUILINO = 3;
			int FINALIZADO_ZELADOR = 4;
			int OBJETO_DEPOSITADO = 2;
			int ESTADO_BOX_AGUARDANDO_RETIRADA = 2;

			// Se tentar liberar/abrir um compartimento do box cujo o estado da entrega seja
			// diferente de objeto depositado
			if (statusEntregaDataWebClient.getSituacaoId() != OBJETO_DEPOSITADO
					|| compartimento.getCodigoEstadoBoxAtividade() != ESTADO_BOX_AGUARDANDO_RETIRADA) {
				String msg = String.format("Estado %s e/ou estado %s do compartimento é/são incorreto(s) para a entrega %s",
						statusEntregaDataWebClient.getSituacaoId(), compartimento.getCodigoEstadoBoxAtividade(),
						codigoEntrega);
				logger.error(msg);
				throw new RetiradaUseCaseException(
						msg,
						HttpStatus.FORBIDDEN);
			}

			int ESTADO_BOX_LIVRE = 1;
			compartimentoDataProvider.update(compartimento.getCompartimentoId(), ESTADO_BOX_LIVRE);

			int codigo;

			if ("INQUILINO".equals(origem)) {
				codigo = FINALIZADO_INQUILINO;
			} else {
				codigo = FINALIZADO_ZELADOR;
			}

			StatusEntrega statusEntrega = new StatusEntrega(codigoEntrega, 0, codigo, 0, null, 0, 0, 0, 0, "");
			updateEntregaUseCase.execute(statusEntrega);

			return true;

		} catch (RetiradaUseCaseException e) {
			throw e;
		} catch (Exception e) {
			throw new RetiradaUseCaseException(e);
		}
	}

	@Override
	public boolean processarAberturaDePorta(String numeroSerial, String codigoCompartimentoRetirada, String type,
			String origem) throws RetiradaUseCaseException {

		try {

			String ORIGEM_INQUILINO = "INQUILINO";
			String ORIGEM_ENTREGADOR = "ENTREGADOR";
			String ORIGEM_ADMINISTRADOR = "ADMINISTRADOR";

			String TYPE_ENTREGA = "ENTREGA";
			String TYPE_RETIRADA = "RETIRADA";

			int FINALIZADO_INQUILINO = 3;
			int FINALIZADO_ZELADOR = 4;
			int OBJETO_DEPOSITADO = 2;
			int ESTADO_BOX_AGUARDANDO_RETIRADA = 2;
			int ESTADO_BOX_LIVRE = 1;

			if (ORIGEM_ENTREGADOR.equals(origem) && TYPE_ENTREGA.equals(type)) {
				//Não é necessário processar abertura de porta para entrega de entregadores
				return true;
			} else if (ORIGEM_INQUILINO.equals(origem) && TYPE_RETIRADA.equals(type)) {

				CompartimentoStatusTempResponse compartimento = compartimentoDataProvider
						.consultarCompatimentoByIdAleatorio(codigoCompartimentoRetirada);

				String codigoEntrega = validateDataProvider.validate(numeroSerial, codigoCompartimentoRetirada)
						.getEntregaId();

				StatusEntregaDataWebClient statusEntregaDataWebClient = statusEntregaDataProvider
						.consultarStatusEntrega(codigoEntrega).stream()
						.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst()
						.get();

				if (statusEntregaDataWebClient.getSituacaoId() != OBJETO_DEPOSITADO
						|| compartimento.getCodigoEstadoBoxAtividade() != ESTADO_BOX_AGUARDANDO_RETIRADA) {
					String msg = String.format(
							"Situação %s e/ou estado %s do compartimento é/são incorreto(s) para a entrega %s",
							statusEntregaDataWebClient.getSituacaoId(), compartimento.getCodigoEstadoBoxAtividade(),
							codigoEntrega);
					logger.error(msg);
					throw new RetiradaUseCaseException(msg, HttpStatus.FORBIDDEN);
				}

				// Atualizar status da entrega
				StatusEntrega statusEntrega = new StatusEntrega(codigoEntrega, 0, FINALIZADO_INQUILINO, 0, null, 0, 0,
						0, 0, "");
				updateEntregaUseCase.execute(statusEntrega);

				// Atualiza status do compartimento
				compartimentoDataProvider.update(compartimento.getCompartimentoId(), ESTADO_BOX_LIVRE);

				CompartimentoStatusTempRequest compartimentoStatusTempRequest = new CompartimentoStatusTempRequest(
						compartimento.getCompartimentoIdAleatorio(),
						compartimento.getCompartimentoId(),
						compartimento.getBoxId(),
						FINALIZADO_INQUILINO,
						compartimento.getEntregaId(),
						compartimento.getCompartimentoCom1Id(),
						compartimento.getLabelPorta()
						);
				
				compartimentoDataProvider.atualizarComIdAleatorio(compartimentoStatusTempRequest);
				
				
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					String dataHoraRetirada = LocalDateTime.now().format(formatter);
					for (String telefone : telefonesAdmin) {
						String mensagemAdmin = String.format("Entrega %s foi retirada às %s pelo %s  ",
								compartimento.getEntregaId(), dataHoraRetirada, "MORADOR");
						enviarNotificacaoDataProvider.enviar(mensagemAdmin, telefone);
					}
				} catch (Exception e) {
					logger.error("Erro ao tentar notificar administradores em 'processarAberturaDePorta' >> {}",
							e.getLocalizedMessage(), e);
				}
			
			} else if (ORIGEM_ADMINISTRADOR.equals(origem) && TYPE_RETIRADA.equals(type)) {
				
				CompartimentoStatusTempResponse compartimento = compartimentoDataProvider
						.consultarCompatimentoByIdAleatorio(codigoCompartimentoRetirada);

				//String codigoEntrega = validateDataProvider.validate(numeroSerial, codigoCompartimentoRetirada).getEntregaId();
				String codigoEntrega = compartimentoDataProvider.consultarCompatimentoByIdAleatorio(codigoCompartimentoRetirada).getEntregaId();
				
				StatusEntregaDataWebClient statusEntregaDataWebClient = statusEntregaDataProvider
						.consultarStatusEntrega(codigoEntrega).stream()
						.sorted(Comparator.comparing(StatusEntregaDataWebClient::getDataHora).reversed()).findFirst()
						.get();

				if (statusEntregaDataWebClient.getSituacaoId() != OBJETO_DEPOSITADO
						|| compartimento.getCodigoEstadoBoxAtividade() != ESTADO_BOX_AGUARDANDO_RETIRADA) {
					String msg = String.format(
							"Estado %s e/ou estado %s do compartimento é/são incorreto(s) para a entrega %s",
							statusEntregaDataWebClient.getSituacaoId(), compartimento.getCodigoEstadoBoxAtividade(),
							codigoEntrega);
					throw new RetiradaUseCaseException(msg, HttpStatus.FORBIDDEN);
				}

				// Atualizar status da entrega
				StatusEntrega statusEntrega = new StatusEntrega(codigoEntrega, 0, FINALIZADO_ZELADOR, 0, null, 0, 0,
						0, 0, "");
				updateEntregaUseCase.execute(statusEntrega);

				// Atualiza status do compartimento
				compartimentoDataProvider.update(compartimento.getCompartimentoId(), ESTADO_BOX_LIVRE);
				
				CompartimentoStatusTempRequest compartimentoStatusTempRequest = new CompartimentoStatusTempRequest(
						compartimento.getCompartimentoIdAleatorio(),
						compartimento.getCompartimentoId(),
						compartimento.getBoxId(),
						FINALIZADO_ZELADOR,
						compartimento.getEntregaId(),
						compartimento.getCompartimentoCom1Id(),
						compartimento.getLabelPorta()
						);
				
				compartimentoDataProvider.atualizarComIdAleatorio(compartimentoStatusTempRequest);
				
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					String dataHoraRetirada = LocalDateTime.now().format(formatter);
					for (String telefone : telefonesAdmin) {
						String mensagemAdmin = String.format(
								"Entrega %s foi retirada às %s pelo %s  ", compartimento.getEntregaId(), dataHoraRetirada, ORIGEM_ADMINISTRADOR);
						enviarNotificacaoDataProvider.enviar(mensagemAdmin, telefone);
					}
				}catch (Exception e) {
					logger.error("Erro ao tentar notificar administradores em 'processarAberturaDePorta' >> {}", e.getLocalizedMessage(), e);
				}
			}

			return true;
			
		} catch (Exception e) {
			logger.error("Erro ao tentar 'processarAberturaDePorta' >> {}", e.getLocalizedMessage(), e);
		}
		return false;
	}
}

class Retorno {
	private String labelPorta;
	private String codigoRandomico;

	public String getLabelPorta() {
		return labelPorta;
	}

	public String getCodigoRandomico() {
		return codigoRandomico;
	}

}
