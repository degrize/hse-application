package com.degrize.hseapp.service.impl;

import com.degrize.hseapp.domain.Regle;
import com.degrize.hseapp.repository.RegleRepository;
import com.degrize.hseapp.service.RegleService;
import com.degrize.hseapp.service.dto.RegleDTO;
import com.degrize.hseapp.service.mapper.RegleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Regle}.
 */
@Service
@Transactional
public class RegleServiceImpl implements RegleService {

    private final Logger log = LoggerFactory.getLogger(RegleServiceImpl.class);

    private final RegleRepository regleRepository;

    private final RegleMapper regleMapper;

    public RegleServiceImpl(RegleRepository regleRepository, RegleMapper regleMapper) {
        this.regleRepository = regleRepository;
        this.regleMapper = regleMapper;
    }

    @Override
    public RegleDTO save(RegleDTO regleDTO) {
        log.debug("Request to save Regle : {}", regleDTO);
        Regle regle = regleMapper.toEntity(regleDTO);
        regle = regleRepository.save(regle);
        return regleMapper.toDto(regle);
    }

    @Override
    public RegleDTO update(RegleDTO regleDTO) {
        log.debug("Request to update Regle : {}", regleDTO);
        Regle regle = regleMapper.toEntity(regleDTO);
        regle = regleRepository.save(regle);
        return regleMapper.toDto(regle);
    }

    @Override
    public Optional<RegleDTO> partialUpdate(RegleDTO regleDTO) {
        log.debug("Request to partially update Regle : {}", regleDTO);

        return regleRepository
            .findById(regleDTO.getId())
            .map(existingRegle -> {
                regleMapper.partialUpdate(existingRegle, regleDTO);

                return existingRegle;
            })
            .map(regleRepository::save)
            .map(regleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Regles");
        return regleRepository.findAll(pageable).map(regleMapper::toDto);
    }

    public Page<RegleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return regleRepository.findAllWithEagerRelationships(pageable).map(regleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegleDTO> findOne(Long id) {
        log.debug("Request to get Regle : {}", id);
        return regleRepository.findOneWithEagerRelationships(id).map(regleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regle : {}", id);
        regleRepository.deleteById(id);
    }
}
