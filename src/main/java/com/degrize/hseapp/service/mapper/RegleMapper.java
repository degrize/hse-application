package com.degrize.hseapp.service.mapper;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.domain.Regle;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.service.dto.RegleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Regle} and its DTO {@link RegleDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegleMapper extends EntityMapper<RegleDTO, Regle> {
    @Mapping(target = "projet", source = "projet", qualifiedByName = "projetTitre")
    RegleDTO toDto(Regle s);

    @Named("projetTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    ProjetDTO toDtoProjetTitre(Projet projet);
}
