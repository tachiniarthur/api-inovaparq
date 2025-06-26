package br.com.inovaparq.api_inovaparq.controller.dto.company_view;

public class InformacoesBasicasDTO {
    private Long id;
    private String name;
    private String cnpj;
    private Boolean ativo;
    private String telefone;
    private String email;
    private String site;

    public InformacoesBasicasDTO(Long id, String name, String cnpj, Boolean ativo, String telefone, String email, String site) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getSite() {
        return site;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
