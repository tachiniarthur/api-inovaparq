package br.com.inovaparq.api_inovaparq.service;

import br.com.inovaparq.api_inovaparq.controller.dto.CompanyRequestDTO;
import br.com.inovaparq.api_inovaparq.model.CompanyModel;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.CompanyRepository;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CompanyModel> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<CompanyModel> findOnlyById(Long id) {
        return companyRepository.findById(id);
    }

    public CompanyModel saveCompany(CompanyModel company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyModel createCompany(CompanyRequestDTO dto) throws IOException {
        CompanyModel company = new CompanyModel();
        company.setName(dto.getName());
        company.setCnpj(dto.getCnpj());
        company.setCep(dto.getCep());
        company.setLogradouro(dto.getLogradouro());
        company.setNumero(dto.getNumero());
        company.setComplemento(dto.getComplemento());
        company.setBairro(dto.getBairro());
        company.setCidade(dto.getCidade());
        company.setUf(dto.getUf());
        company.setTelefone(dto.getTelefone());
        company.setEmail(dto.getEmail());
        company.setSite(dto.getSite());
        company.setInscricaoEstadual(dto.getInscricaoEstadual());
        company.setInscricaoMunicipal(dto.getInscricaoMunicipal());
        company.setObservacao(dto.getObservacao());

        if (dto.getResponsavelId() != null) {
            Optional<UserModel> responsavel = userRepository.findById(dto.getResponsavelId());
            responsavel.ifPresent(company::setResponsavel);
        }

        // Aqui você implementa a lógica para salvar os arquivos (em disco, S3, etc.)
        if (dto.getAlvaraFuncionamento() != null && !dto.getAlvaraFuncionamento().isEmpty()) {
            String filePath = salvarArquivo(dto.getAlvaraFuncionamento());
            company.setAlvaraFuncionamento(filePath);
        }
        if (dto.getInscricaoEstadualArquivo() != null && !dto.getInscricaoEstadualArquivo().isEmpty()) {
            String filePath = salvarArquivo(dto.getInscricaoEstadualArquivo());
            company.setInscricaoEstadualArquivo(filePath);
        }
        if (dto.getComprovanteEndereco() != null && !dto.getComprovanteEndereco().isEmpty()) {
            String filePath = salvarArquivo(dto.getComprovanteEndereco());
            company.setComprovanteEndereco(filePath);
        }

        return companyRepository.save(company);
    }

    private String salvarArquivo(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        file.transferTo(new java.io.File("/caminho/salvo/" + filename));
        return "/caminho/salvo/" + filename;
    }

    public CompanyModel updateCompany(Long id, CompanyModel companyAtualizada) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(companyAtualizada.getName());
                    existingCompany.setCnpj(companyAtualizada.getCnpj());
                    existingCompany.setCep(companyAtualizada.getCep());
                    existingCompany.setLogradouro(companyAtualizada.getLogradouro());
                    existingCompany.setNumero(companyAtualizada.getNumero());
                    existingCompany.setComplemento(companyAtualizada.getComplemento());
                    existingCompany.setBairro(companyAtualizada.getBairro());
                    existingCompany.setCidade(companyAtualizada.getCidade());
                    existingCompany.setUf(companyAtualizada.getUf());
                    existingCompany.setTelefone(companyAtualizada.getTelefone());
                    existingCompany.setEmail(companyAtualizada.getEmail());
                    existingCompany.setSite(companyAtualizada.getSite());
                    existingCompany.setInscricaoEstadual(companyAtualizada.getInscricaoEstadual());
                    existingCompany.setInscricaoMunicipal(companyAtualizada.getInscricaoMunicipal());
                    existingCompany.setObservacao(companyAtualizada.getObservacao());
                    existingCompany.setAlvaraFuncionamento(companyAtualizada.getAlvaraFuncionamento());
                    existingCompany.setInscricaoEstadualArquivo(companyAtualizada.getInscricaoEstadualArquivo());
                    existingCompany.setComprovanteEndereco(companyAtualizada.getComprovanteEndereco());
                    existingCompany.setResponsavel(companyAtualizada.getResponsavel());
                    return companyRepository.save(existingCompany);
                })
                .orElseGet(() -> {
                    companyAtualizada.setId(id);
                    return companyRepository.save(companyAtualizada);
                });
    }

    public CompanyModel updateStatusCompany(Long id, CompanyModel companyAtualizada) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setAtivo(companyAtualizada.getAtivo());
                    return companyRepository.save(existingCompany);
                })
                .orElseGet(() -> {
                    companyAtualizada.setId(id);
                    return companyRepository.save(companyAtualizada);
                });
    }
}
