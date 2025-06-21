package br.com.inovaparq.api_inovaparq.model;

import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private CompanyModel company;

    public CompanyStatusModel() {}

    public CompanyStatusModel(String slug, String title, CompanyModel company) {
        this.slug = slug;
        this.title = title;
        this.company = company;
    }

    public CompanyStatusModel(String slug, String title) {
        this.slug = slug;
        this.title = title;
        this.company = null;
    }

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

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
