package com.degrize.hseapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.degrize.hseapp.domain.Regle} entity.
 */
@Schema(description = "Regle entity.\n@author MEDA.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegleDTO implements Serializable {

    private Long id;

    @NotNull
    private String texte;

    @NotNull
    private Instant date;

    private ProjetDTO projet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegleDTO)) {
            return false;
        }

        RegleDTO regleDTO = (RegleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, regleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegleDTO{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            ", date='" + getDate() + "'" +
            ", projet=" + getProjet() +
            "}";
    }
}
