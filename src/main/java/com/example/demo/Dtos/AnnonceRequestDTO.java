package com.example.demo.web.dto;

import com.example.demo.model.Categorie;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AnnonceRequestDTO {

    @NotBlank
    @Size(min = 5, max = 100)
    private String titre;

    @Size(max = 2000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal prix;

    @NotNull
    private Categorie categorie;

    @NotBlank
    private String auteur;

    @Email
    private String email;

    private String telephone;

    // optionnel, peut être null -> true par défaut
    private Boolean active;

    // getters & setters (constructors si besoin)
}
