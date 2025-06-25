package br.com.inovaparq.api_inovaparq.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CompanyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cnpj;

    // Endereço
    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String uf;

    private Boolean ativo = true;

    // Dados de contato
    private String telefone;
    private String email;
    private String site;

    // Responsável legal (relacionamento com usuário)
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private UserModel responsavel;

    @OneToMany(mappedBy = "company")
    private List<UserModel> usuarios;

    // Dados fiscais
    private String inscricaoEstadual;
    private String inscricaoMunicipal;

    // Observações
    @Lob
    @Column(nullable = true)
    private String alvaraFuncionamento;

    @Lob
    @Column(nullable = true)
    private String inscricaoEstadualArquivo;

    @Lob
    @Column(nullable = true)
    private String comprovanteEndereco;

    @Lob
    @Column(nullable = true)
    private String observacao;

    // Novo relacionamento com status da empresa
    @ManyToMany
    @JoinTable(
        name = "company_status_mapping",
        joinColumns = @JoinColumn(name = "company_id"),
        inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private List<CompanyStatusModel> statuses;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public UserModel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UserModel responsavel) {
        this.responsavel = responsavel;
    }

    public List<UserModel> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserModel> usuarios) {
        this.usuarios = usuarios;
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

    public String getAlvaraFuncionamento() {
        return alvaraFuncionamento;
    }

    public void setAlvaraFuncionamento(String alvaraFuncionamento) {
        this.alvaraFuncionamento = alvaraFuncionamento;
    }

    public String getInscricaoEstadualArquivo() {
        return inscricaoEstadualArquivo;
    }

    public void setInscricaoEstadualArquivo(String inscricaoEstadualArquivo) {
        this.inscricaoEstadualArquivo = inscricaoEstadualArquivo;
    }

    public String getComprovanteEndereco() {
        return comprovanteEndereco;
    }

    public void setComprovanteEndereco(String comprovanteEndereco) {
        this.comprovanteEndereco = comprovanteEndereco;
    }

    public List<CompanyStatusModel> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CompanyStatusModel> statuses) {
        this.statuses = statuses;
    }
}
