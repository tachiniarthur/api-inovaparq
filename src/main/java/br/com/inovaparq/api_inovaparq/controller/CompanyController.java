package br.com.inovaparq.api_inovaparq.controller;

import br.com.inovaparq.api_inovaparq.controller.dto.CompanyFullRequestDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.CompanyResumoDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.DefaultResponseDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.KanbanStatusDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.company_view.DocumentosDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.company_view.EnderecoDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.company_view.InformacoesBasicasDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.company_view.RepresentanteLegalDTO;
// import br.com.inovaparq.api_inovaparq.controller.dto.CompanyRequestDTO;
import br.com.inovaparq.api_inovaparq.model.CompanyModel;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import br.com.inovaparq.api_inovaparq.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list/{userId}")
    public ResponseEntity<DefaultResponseDTO<?>> listarTodas(@PathVariable Long userId) {
        try {
            UserModel user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "Usuário não encontrado com ID: " + userId));

            if (Boolean.TRUE.equals(user.getAdmin())) {
                List<CompanyModel> empresas = companyService.findAllCompanies();

                if (empresas.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(new DefaultResponseDTO<>("Nenhuma empresa encontrada", null));
                }

                List<CompanyResumoDTO> resultado = empresas.stream()
                        .map(emp -> {
                            String slugStatus = null;
                            if (emp.getStatuses() != null && !emp.getStatuses().isEmpty()) {
                                slugStatus = emp.getStatuses().get(0).getSlug();
                            }
                            return new CompanyResumoDTO(
                                    emp.getId(),
                                    emp.getName(),
                                    emp.getResponsavel() != null ? emp.getResponsavel().getName() : null,
                                    slugStatus
                            );
                        })
                        .toList();

                return ResponseEntity.ok(new DefaultResponseDTO<>("Empresas listadas com sucesso", resultado));

            } else {
                CompanyModel empresa = user.getCompany();

                if (empresa == null) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(new DefaultResponseDTO<>("Nenhuma empresa encontrada", null));
                }

                String slugStatus = null;
                if (empresa.getStatuses() != null && !empresa.getStatuses().isEmpty()) {
                    slugStatus = empresa.getStatuses().get(0).getSlug();
                }

                CompanyResumoDTO resumo = new CompanyResumoDTO(
                        empresa.getId(),
                        empresa.getName(),
                        empresa.getResponsavel() != null ? empresa.getResponsavel().getName() : null,
                        slugStatus
                );

                return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa listada com sucesso", List.of(resumo)));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> buscarPorId(
            @PathVariable Long id,
            @RequestParam String section) {
        try {
            Optional<CompanyModel> optionalCompany = companyService.findOnlyById(id);
            if (optionalCompany.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new DefaultResponseDTO<>("Empresa não encontrada", null));
            }

            CompanyModel company = optionalCompany.get();

            return switch (section.toLowerCase()) {
                case "informacoes-basicas" -> {
                    InformacoesBasicasDTO dto = new InformacoesBasicasDTO(
                            company.getId(), company.getName(), company.getCnpj(), company.getAtivo(),
                            company.getTelefone(), company.getEmail(), company.getSite()
                    );
                    yield ResponseEntity.ok(new DefaultResponseDTO<>("Informações básicas encontradas", dto));
                }

                case "endereco" -> {
                    EnderecoDTO dto = new EnderecoDTO(
                            company.getCep(), company.getLogradouro(), company.getNumero(), company.getComplemento(),
                            company.getBairro(), company.getCidade(), company.getUf()
                    );
                    yield ResponseEntity.ok(new DefaultResponseDTO<>("Endereço encontrado", dto));
                }

                case "representante-legal" -> {
                    UserModel resp = company.getResponsavel();
                    RepresentanteLegalDTO dto = new RepresentanteLegalDTO(
                            resp != null ? resp.getName() : null,
                            resp != null ? resp.getEmail() : null,
                            resp != null ? resp.getCpf() : null,
                            resp != null ? resp.getPhone() : null
                    );
                    yield ResponseEntity.ok(new DefaultResponseDTO<>("Representante legal encontrado", dto));
                }

                case "documentos" -> {
                    DocumentosDTO dto = new DocumentosDTO(
                            company.getAlvaraFuncionamento(), company.getInscricaoEstadualArquivo(),
                            company.getComprovanteEndereco(), company.getObservacao()
                    );
                    yield ResponseEntity.ok(new DefaultResponseDTO<>("Documentos encontrados", dto));
                }

                default -> ResponseEntity.badRequest().body(new DefaultResponseDTO<>("Seção inválida", null));
            };

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<DefaultResponseDTO<?>> createCompany(@RequestBody CompanyFullRequestDTO requestDTO) {
        try {
            companyService.createCompany(requestDTO);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa criada com sucesso", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>(e.getMessage(), null));
        }
    }

    // Atualizar empresa existente
    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> atualizarEmpresa(@PathVariable Long id, @RequestBody CompanyModel companyAtualizada) {
        try {
            CompanyModel updated = companyService.updateCompany(id, companyAtualizada);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa atualizada com sucesso", updated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>("Erro ao atualizar empresa: " + e.getMessage(), null));
        }
    }

    // Atualizar status empresa existente
    @PutMapping("/status/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> mudarStatusEmpresa(@PathVariable Long id, @RequestBody CompanyModel companyAtualizada) {
        try {
            CompanyModel updated = companyService.updateStatusCompany(id, companyAtualizada);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Status da empresa atualizado com sucesso", updated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>("Erro ao atualizar status da empresa: " + e.getMessage(), null));
        }
    }

    // Deletar empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> deletarEmpresa(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa deletada com sucesso", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>("Erro ao deletar empresa: " + e.getMessage(), null));
        }
    }

    // Mudar status da empresa no kanban, recebendo o id e o novo slug da empresa
    @PutMapping("/kanban-status/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> mudarStatusKanban(
            @PathVariable Long id,
            @RequestBody KanbanStatusDTO dto) {
        try {
            CompanyModel updated = companyService.updateKanbanStatus(id, dto.getStatus());
            // Pegue apenas o status principal (ajuste conforme sua lógica)
            String status = (updated.getStatuses() != null && !updated.getStatuses().isEmpty())
                    ? updated.getStatuses().get(0).getSlug()
                    : null;
            KanbanStatusDTO responseDTO = new KanbanStatusDTO(
                    updated.getId(),
                    updated.getName(),
                    status
            );
            return ResponseEntity.ok(new DefaultResponseDTO<>("Status do kanban atualizado com sucesso", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>("Erro ao atualizar status do kanban: " + e.getMessage(), null));
        }
    }
}
