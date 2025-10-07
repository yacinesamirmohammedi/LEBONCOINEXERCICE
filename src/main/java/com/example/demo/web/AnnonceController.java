package com.example.demo.web;

import com.example.demo.model.Categorie;
import com.example.demo.service.AnnonceService;
import com.example.demo.web.dto.AnnonceRequestDTO;
import com.example.demo.web.dto.AnnonceResponseDTO;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    private final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "API OK";
    }

    // GET all (paged)
    @GetMapping
    public Page<AnnonceResponseDTO> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreation") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        return annonceService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AnnonceResponseDTO> create(@Valid @RequestBody AnnonceRequestDTO dto) {
        AnnonceResponseDTO saved = annonceService.create(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AnnonceRequestDTO dto) {
        return ResponseEntity.ok(annonceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        annonceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH endpoint
    @GetMapping("/search")
    public Page<AnnonceResponseDTO> search(
            @RequestParam(required = false) BigDecimal prixMin,
            @RequestParam(required = false) BigDecimal prixMax,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String categories, // comma separated names
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreation") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));

        List<Categorie> cats = null;
        if (categories != null && !categories.isBlank()) {
            cats = Arrays.stream(categories.split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(Categorie::valueOf) // si valeur invalide: exception, peut être capturée si tu veux
                    .collect(Collectors.toList());
        }

        return annonceService.search(prixMin, prixMax, titre, cats, pageable);
    }
}
