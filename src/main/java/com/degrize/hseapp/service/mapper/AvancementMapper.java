package com.degrize.hseapp.service.mapper;

import com.degrize.hseapp.domain.Avancement;
import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.service.dto.AvancementDTO;
import com.degrize.hseapp.service.dto.ProjetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avancement} and its DTO {@link AvancementDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvancementMapper extends EntityMapper<AvancementDTO, Avancement> {
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTitre")
    AvancementDTO toDto(Avancement s);

    @Named("projetTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    @Mapping(target = "code", source = "code")
    ProjetDTO toDtoProjetTitre(Projet projet);
}
