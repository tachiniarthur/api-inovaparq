package br.com.inovaparq.api_inovaparq.config;

import br.com.inovaparq.api_inovaparq.model.CompanyStatusModel;
import br.com.inovaparq.api_inovaparq.repository.CompanyStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CompanyStatusDataLoader implements CommandLineRunner {

    private final CompanyStatusRepository repository;

    public CompanyStatusDataLoader(CompanyStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<CompanyStatusModel> defaultStatuses = Arrays.asList(
            new CompanyStatusModel("todo", "A Fazer"),
            new CompanyStatusModel("in_progress", "Em Progresso"),
            new CompanyStatusModel("review", "Em Revisão"),
            new CompanyStatusModel("documents", "Documentos Pendentes"),
            new CompanyStatusModel("financeiro", "Pendências Financeiras")
        );

        for (CompanyStatusModel status : defaultStatuses) {
            repository.findBySlug(status.getSlug())
                .orElseGet(() -> repository.save(status));
        }
    }

}
