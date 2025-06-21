package br.com.inovaparq.api_inovaparq.controller;

import br.com.inovaparq.api_inovaparq.model.CompanyStatusModel;
import br.com.inovaparq.api_inovaparq.repository.CompanyStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company-status")
public class CompanyStatusController {

    @Autowired
    private CompanyStatusRepository companyStatusRepository;

    // GET
    @GetMapping
    public ResponseEntity<List<CompanyStatusModel>> getAllStatuses() {
        List<CompanyStatusModel> statuses = companyStatusRepository.findAll();
        return ResponseEntity.ok(statuses);
    }
}
