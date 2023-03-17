package com.degrize.hseapp.service.mapper;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.domain.User;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.service.dto.SignalementDTO;
import com.degrize.hseapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projet} and its DTO {@link ProjetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjetMapper extends EntityMapper<ProjetDTO, Projet> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ProjetDTO toDto(Projet s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
