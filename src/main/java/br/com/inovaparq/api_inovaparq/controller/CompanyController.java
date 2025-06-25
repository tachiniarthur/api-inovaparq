package br.com.inovaparq.api_inovaparq.controller;

import br.com.inovaparq.api_inovaparq.controller.dto.CompanyFullRequestDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.DefaultResponseDTO;
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
                return ResponseEntity.ok(new DefaultResponseDTO<>("Empresas listadas com sucesso", empresas));
            } else {
                CompanyModel empresa = user.getCompany();
                if (empresa == null) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(new DefaultResponseDTO<>("Nenhuma empresa encontrada", null));
                }
                return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa listada com sucesso", List.of(empresa)));
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new DefaultResponseDTO<>(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>(e.getMessage(), null));
        }
    }

    // Buscar empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> buscarPorId(@PathVariable Long id) {
        try {
            Optional<CompanyModel> companyModel = companyService.findOnlyById(id);
            if (companyModel.isPresent()) {
                return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa encontrada com sucesso", companyModel.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new DefaultResponseDTO<>("Empresa não encontrada", null));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<DefaultResponseDTO<?>> createCompany(@RequestBody CompanyFullRequestDTO requestDTO) {
        try {
            CompanyModel savedCompany = companyService.createCompany(requestDTO);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Empresa criada com sucesso", savedCompany));
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
    public ResponseEntity<DefaultResponseDTO<?>> mudarStatusKanban(@PathVariable Long id, @RequestParam String novoSlug) {
        try {
            CompanyModel updated = companyService.updateKanbanStatus(id, novoSlug);
            return ResponseEntity.ok(new DefaultResponseDTO<>("Status do kanban atualizado com sucesso", updated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new DefaultResponseDTO<>("Erro ao atualizar status do kanban: " + e.getMessage(), null));
        }
    }
}
