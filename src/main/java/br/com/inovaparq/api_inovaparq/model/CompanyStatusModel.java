package br.com.inovaparq.api_inovaparq.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "company_status")
public class CompanyStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String slug;

    @Column(nullable = false)
    private String title;

    // Remova o relacionamento antigo @ManyToOne e substitua pelo ManyToMany correto
    @ManyToMany(mappedBy = "statuses")
    private List<CompanyModel> companies;

    public CompanyStatusModel() {}

    public CompanyStatusModel(String slug, String title) {
        this.slug = slug;
        this.title = title;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CompanyModel> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyModel> companies) {
        this.companies = companies;
    }
}
