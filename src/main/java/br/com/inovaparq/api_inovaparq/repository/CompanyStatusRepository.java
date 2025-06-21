package br.com.inovaparq.api_inovaparq.repository;

import br.com.inovaparq.api_inovaparq.model.CompanyStatusModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStatusRepository extends JpaRepository<CompanyStatusModel, Long> {
    Optional<CompanyStatusModel> findBySlug(String slug);
}
