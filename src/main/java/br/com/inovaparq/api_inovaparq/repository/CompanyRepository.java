package br.com.inovaparq.api_inovaparq.repository;

import br.com.inovaparq.api_inovaparq.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
}
