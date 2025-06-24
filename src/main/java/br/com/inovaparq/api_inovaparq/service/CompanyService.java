package br.com.inovaparq.api_inovaparq.service;

// import br.com.inovaparq.api_inovaparq.controller.dto.CompanyRequestDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.CompanyFullRequestDTO;
import br.com.inovaparq.api_inovaparq.model.CompanyModel;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.model.CompanyStatusModel;
import br.com.inovaparq.api_inovaparq.repository.CompanyRepository;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import br.com.inovaparq.api_inovaparq.repository.CompanyStatusRepository;

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

    @Autowired
    private CompanyStatusRepository companyStatusRepository;

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

    public CompanyModel createCompany(CompanyFullRequestDTO dto) {
        CompanyFullRequestDTO.CompanyData companyData = dto.getCompanyData();
        CompanyFullRequestDTO.AddressData addressData = dto.getAddressData();
        CompanyFullRequestDTO.ResponsibleData responsibleData = dto.getResponsibleData();

        CompanyModel company = new CompanyModel();
        company.setName(companyData.getCompanyName());
        company.setCnpj(companyData.getCnpj());
        company.setInscricaoEstadual(companyData.getStateRegistration());
        company.setInscricaoMunicipal(companyData.getMunicipalRegistration());
        company.setTelefone(companyData.getPhone());
        company.setEmail(companyData.getEmail());
        company.setSite(companyData.getWebsite());
        company.setObservacao(companyData.getBusinessActivity());
        company.setAlvaraFuncionamento(companyData.getOperatingLicense());
        company.setInscricaoEstadualArquivo(companyData.getRegistrationDocument());
        company.setComprovanteEndereco(companyData.getAddressProof());

        // Endereço
        company.setCep(addressData.getCep());
        company.setLogradouro(addressData.getAddress());
        company.setNumero(addressData.getNumber());
        company.setComplemento(addressData.getComplement());
        company.setBairro(addressData.getNeighborhood());
        company.setCidade(addressData.getCity());
        company.setUf(addressData.getState());

        // Responsável
        if (responsibleData != null && responsibleData.getUserId() != null && !responsibleData.getUserId().isEmpty()) {
            try {
                Long userId = Long.parseLong(responsibleData.getUserId());
                Optional<UserModel> responsavel = userRepository.findById(userId);
                responsavel.ifPresent(company::setResponsavel);
            } catch (NumberFormatException ignored) {}
        }

        // Se for novo responsável, criar lógica para criar novo usuário e associar
        // (implemente conforme sua regra de negócio)

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

    public CompanyModel updateKanbanStatus(Long id, String slug) {
        CompanyModel company = companyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));
        CompanyStatusModel status = companyStatusRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Status não encontrado para o slug: " + slug));
        company.getStatuses().clear();
        company.getStatuses().add(status);
        status.setCompany(company);
        return companyRepository.save(company);
    }
}
