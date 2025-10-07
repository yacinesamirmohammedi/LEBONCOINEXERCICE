package com.example.demo.dtos;

import com.example.demo.model.Categorie;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnnonceResponseDTO {

    private Long id;
    private String titre;
    private String description;
    private BigDecimal prix;
    private Categorie categorie;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String auteur;
    private String email;
    private String telephone;
    private Boolean active;

    public AnnonceResponseDTO(Long id, String titre, String description, BigDecimal prix, Categorie categorie,
                              LocalDateTime dateCreation, LocalDateTime dateModification, String auteur,
                              String email, String telephone, Boolean active) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.auteur = auteur;
        this.email = email;
        this.telephone = telephone;
        this.active = active;
    }

    // getters & setters...
}
