package br.com.inovaparq.api_inovaparq.controller.dto;

import java.time.LocalDate;

public class UserResponseDTO {
    private Long id;
    private String nome;
    private String username;
    private String email;
    private String cpf;
    private String foto;
    private String telefone;
    private String token;
    private Boolean ativo;
    private Boolean admin;
    private LocalDate birthdate;

    public UserResponseDTO(Long id, String nome, String username, String email, String cpf,
                           String foto, String telefone, String token, Boolean ativo, Boolean admin, LocalDate birthdate) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.cpf = cpf;
        this.foto = foto;
        this.telefone = telefone;
        this.token = token;
        this.ativo = ativo;
        this.admin = admin;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getCpf() {
        return cpf;
    }
    public String getFoto() {
        return foto;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getToken() {
        return token;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public Boolean getAdmin() {
        return admin;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
}
