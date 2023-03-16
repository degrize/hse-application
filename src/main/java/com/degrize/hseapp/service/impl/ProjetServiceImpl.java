package com.degrize.hseapp.service.impl;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.repository.ProjetRepository;
import com.degrize.hseapp.service.ProjetService;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.service.mapper.ProjetMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Projet}.
 */
@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {

    private final Logger log = LoggerFactory.getLogger(ProjetServiceImpl.class);

    private final ProjetRepository projetRepository;

    private final ProjetMapper projetMapper;

    public ProjetServiceImpl(ProjetRepository projetRepository, ProjetMapper projetMapper) {
        this.projetRepository = projetRepository;
        this.projetMapper = projetMapper;
    }

    @Override
    public ProjetDTO save(ProjetDTO projetDTO) {
        log.debug("Request to save Projet : {}", projetDTO);
        projetDTO.setIsDone(false);
        Projet projet = projetMapper.toEntity(projetDTO);
        projet = projetRepository.save(projet);
        return projetMapper.toDto(projet);
    }

    @Override
    public ProjetDTO update(ProjetDTO projetDTO) {
        log.debug("Request to update Projet : {}", projetDTO);
        Projet projet = projetMapper.toEntity(projetDTO);
        projet = projetRepository.save(projet);
        return projetMapper.toDto(projet);
    }

    @Override
    public Optional<ProjetDTO> partialUpdate(ProjetDTO projetDTO) {
        log.debug("Request to partially update Projet : {}", projetDTO);

        return projetRepository
            .findById(projetDTO.getId())
            .map(existingProjet -> {
                projetMapper.partialUpdate(existingProjet, projetDTO);

                return existingProjet;
            })
            .map(projetRepository::save)
            .map(projetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projets");
        return projetRepository.findAll(pageable).map(projetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjetDTO> findOne(Long id) {
        log.debug("Request to get Projet : {}", id);
        return projetRepository.findById(id).map(projetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projet : {}", id);
        projetRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projet> findAllNoPageble() {
        log.debug("Request to get list of Projets");
        return projetRepository.findAll();
    }

    @Override
    public List<Projet> findAllByProjetTitre(String projetTitre) {
        return projetRepository.findProjetByLikeTitre(projetTitre);
    }
}
