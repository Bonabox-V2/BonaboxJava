package br.com.bonabox.auth.api.filter;

import java.time.LocalDateTime;

public class LoggerInterno {
    private Integer boxId;

    private LocalDateTime dataHora;

    private String content;

    private String correlationId;

    private long timeTaken;

    public LoggerInterno() {}

    public LoggerInterno(Integer boxId, LocalDateTime dataHora, String content, String correlationId, long timeTaken) {
        this.boxId = boxId;
        this.dataHora = dataHora;
        this.content = content;
        this.correlationId = correlationId;
        this.timeTaken = timeTaken;
    }

    public Integer getBoxId() {
        return this.boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public long getTimeTaken() {
        return this.timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
