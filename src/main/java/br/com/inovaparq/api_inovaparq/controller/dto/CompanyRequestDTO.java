package br.com.inovaparq.api_inovaparq.controller.dto;

import org.springframework.web.multipart.MultipartFile;

public class CompanyRequestDTO {
    private String name;
    private String cnpj;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;
    private String site;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String observacao;
    private Long responsavelId;
    private MultipartFile alvaraFuncionamento;
    private MultipartFile inscricaoEstadualArquivo;
    private MultipartFile comprovanteEndereco;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }

    public MultipartFile getAlvaraFuncionamento() {
        return alvaraFuncionamento;
    }

    public void setAlvaraFuncionamento(MultipartFile alvaraFuncionamento) {
        this.alvaraFuncionamento = alvaraFuncionamento;
    }

    public MultipartFile getInscricaoEstadualArquivo() {
        return inscricaoEstadualArquivo;
    }

    public void setInscricaoEstadualArquivo(MultipartFile inscricaoEstadualArquivo) {
        this.inscricaoEstadualArquivo = inscricaoEstadualArquivo;
    }

    public MultipartFile getComprovanteEndereco() {
        return comprovanteEndereco;
    }

    public void setComprovanteEndereco(MultipartFile comprovanteEndereco) {
        this.comprovanteEndereco = comprovanteEndereco;
    }
}
