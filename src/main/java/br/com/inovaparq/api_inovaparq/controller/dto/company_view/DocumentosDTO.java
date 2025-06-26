package br.com.inovaparq.api_inovaparq.controller.dto.company_view;

public class DocumentosDTO {
    private String alvaraFuncionamento;
    private String inscricaoEstadualArquivo;
    private String comprovanteEndereco;
    private String observacao;

    public DocumentosDTO(String alvaraFuncionamento, String inscricaoEstadualArquivo, String comprovanteEndereco, String observacao) {
        this.alvaraFuncionamento = alvaraFuncionamento;
        this.inscricaoEstadualArquivo = inscricaoEstadualArquivo;
        this.comprovanteEndereco = comprovanteEndereco;
        this.observacao = observacao;
    }

    public String getAlvaraFuncionamento() {
        return alvaraFuncionamento;
    }

    public String getInscricaoEstadualArquivo() {
        return inscricaoEstadualArquivo;
    }

    public String getComprovanteEndereco() {
        return comprovanteEndereco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setAlvaraFuncionamento(String alvaraFuncionamento) {
        this.alvaraFuncionamento = alvaraFuncionamento;
    }

    public void setInscricaoEstadualArquivo(String inscricaoEstadualArquivo) {
        this.inscricaoEstadualArquivo = inscricaoEstadualArquivo;
    }

    public void setComprovanteEndereco(String comprovanteEndereco) {
        this.comprovanteEndereco = comprovanteEndereco;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

