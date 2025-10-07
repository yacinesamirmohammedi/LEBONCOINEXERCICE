package com.example.demo.repository;

import com.example.demo.model.Annonce;
import com.example.demo.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long>, JpaSpecificationExecutor<Annonce> {
    boolean existsByTitreIgnoreCaseAndCategorie(String titre, Categorie categorie);
}
