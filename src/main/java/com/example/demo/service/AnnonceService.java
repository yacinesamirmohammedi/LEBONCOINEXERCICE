package com.example.demo.service;

import com.example.demo.dtos.AnnonceRequestDTO;
import com.example.demo.dtos.AnnonceResponseDTO;
import com.example.demo.model.Annonce;
import com.example.demo.model.Categorie;
import com.example.demo.repository.AnnonceRepository;
import com.example.demo.repository.AnnonceSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class AnnonceService {

    private final AnnonceRepository annonceRepository;

    public AnnonceService(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    public Page<AnnonceResponseDTO> search(BigDecimal prixMin,
                                           BigDecimal prixMax,
                                           String titre,
                                           List<Categorie> categories,
                                           Pageable pageable) {
        Specification<Annonce> spec = AnnonceSpecifications.withFilters(prixMin, prixMax, titre, categories);
        Page<Annonce> page = annonceRepository.findAll(spec, pageable);
        return page.map(this::toDto);
    }

    public Page<AnnonceResponseDTO> getAll(Pageable pageable) {

        Page<Annonce> page = annonceRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    public AnnonceResponseDTO getById(Long id) {
        Annonce a = annonceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable pour id=" + id));
        return toDto(a);
    }

    public AnnonceResponseDTO create(AnnonceRequestDTO dto) {

        // controle unicité titre + categorie
        if (annonceRepository.existsByTitreIgnoreCaseAndCategorie(dto.getTitre().trim(), dto.getCategorie())) {
            throw new IllegalArgumentException("Un titre identique existe déjà pour cette catégorie.");
        }

        Annonce ann = fromDto(dto);
        Annonce saved = annonceRepository.save(ann);
        return toDto(saved);
    }

    public AnnonceResponseDTO update(Long id, AnnonceRequestDTO dto) {
        Annonce existing = annonceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Annonce introuvable pour id=" + id));

        // si titre ou catégorie changent, vérifier unicité
        if (!existing.getTitre().equalsIgnoreCase(dto.getTitre())
                || existing.getCategorie() != dto.getCategorie()) {
            if (annonceRepository.existsByTitreIgnoreCaseAndCategorie(dto.getTitre().trim(), dto.getCategorie())) {
                throw new IllegalArgumentException("Un titre identique existe déjà pour cette catégorie.");
            }
        }

        // mettre à jour champs
        existing.setTitre(dto.getTitre());
        existing.setDescription(dto.getDescription());
        existing.setPrix(dto.getPrix());
        existing.setCategorie(dto.getCategorie());
        existing.setAuteur(dto.getAuteur());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setActive(dto.getActive() == null ? existing.getActive() : dto.getActive());

        Annonce saved = annonceRepository.save(existing);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!annonceRepository.existsById(id)) {
            throw new IllegalArgumentException("Annonce introuvable pour id=" + id);
        }
        annonceRepository.deleteById(id);
    }


}
