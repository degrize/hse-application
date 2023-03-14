package com.degrize.hseapp.service.mapper;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.service.dto.ProjetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projet} and its DTO {@link ProjetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjetMapper extends EntityMapper<ProjetDTO, Projet> {}
