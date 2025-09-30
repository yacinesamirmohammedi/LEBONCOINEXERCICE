package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity // cette classe correspond à une table en base
@Table(name = "annonce") // le nom de la table sera "annonce"
public class Annonce {

    @Id // clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrémentée
    private Long id;

    @NotBlank(message = "Le titre est obligatoire") // validation
    @Column(nullable = false, length = 120) // en base : non null + taille max
    private String titre;

    @Column(length = 2000) // taille max de la description
    private String description;

    private Double prix; // prix libre

    private String categorie; // exemple : IMMOBILIER, AUTO...

    private String auteur; // nom du créateur de l’annonce

    private LocalDateTime dateCreation; // sera rempli automatiquement

    @PrePersist // exécute ce code avant l’insertion en base
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
    }

    // Constructeur vide (obligatoire pour JPA)
    public Annonce() {}

    // ===== GETTERS & SETTERS =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
