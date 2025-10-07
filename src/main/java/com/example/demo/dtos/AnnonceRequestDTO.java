package com.example.demo.dtos;

import com.example.demo.model.Categorie;
import jakarta.validation.constraints.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;



@Data
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


}
