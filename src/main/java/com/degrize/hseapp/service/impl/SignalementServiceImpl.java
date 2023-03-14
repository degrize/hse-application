package com.degrize.hseapp.service.impl;

import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.repository.SignalementRepository;
import com.degrize.hseapp.service.SignalementService;
import com.degrize.hseapp.service.dto.SignalementDTO;
import com.degrize.hseapp.service.mapper.SignalementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Signalement}.
 */
@Service
@Transactional
public class SignalementServiceImpl implements SignalementService {

    private final Logger log = LoggerFactory.getLogger(SignalementServiceImpl.class);

    private final SignalementRepository signalementRepository;

    private final SignalementMapper signalementMapper;

    public SignalementServiceImpl(SignalementRepository signalementRepository, SignalementMapper signalementMapper) {
        this.signalementRepository = signalementRepository;
        this.signalementMapper = signalementMapper;
    }

    @Override
    public SignalementDTO save(SignalementDTO signalementDTO) {
        log.debug("Request to save Signalement : {}", signalementDTO);
        Signalement signalement = signalementMapper.toEntity(signalementDTO);
        signalement = signalementRepository.save(signalement);
        return signalementMapper.toDto(signalement);
    }

    @Override
    public SignalementDTO update(SignalementDTO signalementDTO) {
        log.debug("Request to update Signalement : {}", signalementDTO);
        Signalement signalement = signalementMapper.toEntity(signalementDTO);
        signalement = signalementRepository.save(signalement);
        return signalementMapper.toDto(signalement);
    }

    @Override
    public Optional<SignalementDTO> partialUpdate(SignalementDTO signalementDTO) {
        log.debug("Request to partially update Signalement : {}", signalementDTO);

        return signalementRepository
            .findById(signalementDTO.getId())
            .map(existingSignalement -> {
                signalementMapper.partialUpdate(existingSignalement, signalementDTO);

                return existingSignalement;
            })
            .map(signalementRepository::save)
            .map(signalementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignalementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Signalements");
        return signalementRepository.findAll(pageable).map(signalementMapper::toDto);
    }

    public Page<SignalementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return signalementRepository.findAllWithEagerRelationships(pageable).map(signalementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SignalementDTO> findOne(Long id) {
        log.debug("Request to get Signalement : {}", id);
        return signalementRepository.findOneWithEagerRelationships(id).map(signalementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Signalement : {}", id);
        signalementRepository.deleteById(id);
    }
}
