package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.catalina.startup.Catalina;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "annonce",
       uniqueConstraints = @UniqueConstraint(name = "uk_titre_categorie", columnNames = {"titre", "categorie"}))
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 5, max = 100, message = "Le titre doit contenir entre 5 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String titre;

    @Column(length = 2000)
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    @NotNull(message = "La catégorie est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Catalina categorie;

    @NotBlank(message = "L'auteur est obligatoire")
    @Column(nullable = false)
    private String auteur;

    @Email(message = "Email invalide")
    private String email;

    private String telephone;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private LocalDateTime dateModification;

    public Annonce() {}

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.dateCreation = now;
        this.dateModification = now;
        if (this.active == null) this.active = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    // Getters & setters (omis ici pour la brièveté; place les tiens)
    // ... (getId, setId, getTitre, setTitre, etc.)
    // N'oublie pas les getters/setters pour tous les champs.
}
