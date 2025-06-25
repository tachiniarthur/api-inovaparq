package br.com.inovaparq.api_inovaparq.controller.dto;

public class CompanyResumoDTO {
    private Long id;
    private String nomeEmpresa;
    private String nomeResponsavel;
    private String slugStatus;

    public CompanyResumoDTO(Long id, String nomeEmpresa, String nomeResponsavel, String slugStatus) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.nomeResponsavel = nomeResponsavel;
        this.slugStatus = slugStatus;
    }

    public Long getId() {
        return id;
    } 

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public String getSlugStatus() {
        return slugStatus;
    }
}
