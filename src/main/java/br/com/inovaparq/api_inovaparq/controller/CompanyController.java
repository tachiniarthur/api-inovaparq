package br.com.inovaparq.api_inovaparq.controller;

import br.com.inovaparq.api_inovaparq.controller.dto.CompanyRequestDTO;
import br.com.inovaparq.api_inovaparq.model.CompanyModel;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import br.com.inovaparq.api_inovaparq.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

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

    // // Listar todas as empresas
    // @GetMapping
    // public List<CompanyModel> listarTodas(@PathVariable Long userId) {
    //     Optional<UserModel> user = userRepository.findById(userId);

    //     if (user.isEmpty()) {
    //         throw new RuntimeException("Usuário não encontrado com ID: " + userId);
    //     }else {
    //         UserModel userModel = user.get();
    //         if (!userModel.isAdmin()) {
    //             throw new RuntimeException("Usuário não tem permissão para acessar esta rota.");
    //         }
    //     }
        
    //     return companyService.findAllCompanies();
    // }

    // Buscar empresa por ID
    @GetMapping("/{id}")
    public CompanyModel buscarPorId(@PathVariable Long id) {
        Optional<CompanyModel> companyModel = companyService.findOnlyById(id);
        return companyModel.orElse(null);
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@ModelAttribute CompanyRequestDTO requestDTO) {
        try {
            CompanyModel savedCompany = companyService.createCompany(requestDTO);
            return ResponseEntity.ok(savedCompany);
        } catch (MultipartException e) {
            return ResponseEntity.badRequest().body("Erro ao processar os arquivos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
    }

    // Atualizar empresa existente
    @PutMapping("/{id}")
    public CompanyModel atualizarEmpresa(@PathVariable Long id, @RequestBody CompanyModel companyAtualizada) {
        return companyService.updateCompany(id, companyAtualizada);
    }

    // Atualizar status empresa existente
    @PutMapping("/status/{id}")
    public CompanyModel mudarStatusEmpresa(@PathVariable Long id, @RequestBody CompanyModel companyAtualizada) {
        return companyService.updateStatusCompany(id, companyAtualizada);
    }

    // Deletar empresa
    @DeleteMapping("/{id}")
    public void deletarEmpresa(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
