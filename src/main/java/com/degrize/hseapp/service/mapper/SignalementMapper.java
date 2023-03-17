package com.degrize.hseapp.service.mapper;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.service.dto.SignalementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Signalement} and its DTO {@link SignalementDTO}.
 */
@Mapper(componentModel = "spring")
public interface SignalementMapper extends EntityMapper<SignalementDTO, Signalement> {
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTitre")
    SignalementDTO toDto(Signalement s);

    @Named("projetTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    @Mapping(target = "code", source = "code")
    ProjetDTO toDtoProjetTitre(Projet projet);
}
