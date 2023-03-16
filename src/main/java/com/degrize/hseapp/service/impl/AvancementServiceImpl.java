package com.degrize.hseapp.service.impl;

import com.degrize.hseapp.domain.Avancement;
import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.repository.AvancementRepository;
import com.degrize.hseapp.service.AvancementService;
import com.degrize.hseapp.service.dto.AvancementDTO;
import com.degrize.hseapp.service.mapper.AvancementMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Avancement}.
 */
@Service
@Transactional
public class AvancementServiceImpl implements AvancementService {

    private final Logger log = LoggerFactory.getLogger(AvancementServiceImpl.class);

    private final AvancementRepository avancementRepository;

    private final AvancementMapper avancementMapper;

    public AvancementServiceImpl(AvancementRepository avancementRepository, AvancementMapper avancementMapper) {
        this.avancementRepository = avancementRepository;
        this.avancementMapper = avancementMapper;
    }

    @Override
    public AvancementDTO save(AvancementDTO avancementDTO) {
        log.debug("Request to save Avancement : {}", avancementDTO);
        Avancement avancement = avancementMapper.toEntity(avancementDTO);
        avancement = avancementRepository.save(avancement);
        return avancementMapper.toDto(avancement);
    }

    @Override
    public AvancementDTO update(AvancementDTO avancementDTO) {
        log.debug("Request to update Avancement : {}", avancementDTO);
        Avancement avancement = avancementMapper.toEntity(avancementDTO);
        avancement = avancementRepository.save(avancement);
        return avancementMapper.toDto(avancement);
    }

    @Override
    public Optional<AvancementDTO> partialUpdate(AvancementDTO avancementDTO) {
        log.debug("Request to partially update Avancement : {}", avancementDTO);

        return avancementRepository
            .findById(avancementDTO.getId())
            .map(existingAvancement -> {
                avancementMapper.partialUpdate(existingAvancement, avancementDTO);

                return existingAvancement;
            })
            .map(avancementRepository::save)
            .map(avancementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvancementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avancements");
        return avancementRepository.findAll(pageable).map(avancementMapper::toDto);
    }

    public Page<AvancementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return avancementRepository.findAllWithEagerRelationships(pageable).map(avancementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvancementDTO> findOne(Long id) {
        log.debug("Request to get Avancement : {}", id);
        return avancementRepository.findOneWithEagerRelationships(id).map(avancementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avancement : {}", id);
        avancementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Avancement> findAllByProjetId(Long id) {
        log.debug("Request to get list Avancement of Projets");
        return avancementRepository.findAllByProjetId(id);
    }
}
