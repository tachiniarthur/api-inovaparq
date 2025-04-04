package br.com.inovaparq.api_inovaparq.repository;

import br.com.inovaparq.api_inovaparq.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    
}