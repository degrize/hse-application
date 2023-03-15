package com.degrize.hseapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String SPECIALISTE_EN_ENVIRONNEMENT_ET_SOCIAL = "ROLE_SPECIALISTE_EN_ENVIRONNEMENT_ET_SOCIAL";
    public static final String MISSION_DE_CONTROLE = "ROLE_MISSION_DE_CONTROLE";
    public static final String RESPONSABLE_HSE = "ROLE_RESPONSABLE_HSE";
    public static final String OUVRIER = "ROLE_OUVRIER";
    public static final String RIVERAIN = "ROLE_RIVERAIN";

    private AuthoritiesConstants() {}
}
