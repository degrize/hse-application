package com.degrize.hseapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.degrize.hseapp.domain.Avancement} entity.
 */
@Schema(description = "Avancement entity.\n@author MEDA.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvancementDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private LocalDate date;

    @Lob
    private byte[] fichier1;

    private String fichier1ContentType;

    @Lob
    private byte[] fichier2;

    private String fichier2ContentType;

    @Lob
    private byte[] fichier3;

    private String fichier3ContentType;

    @Lob
    private byte[] fichier4;

    private String fichier4ContentType;
    private ProjetDTO projet;
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getFichier1() {
        return fichier1;
    }

    public void setFichier1(byte[] fichier1) {
        this.fichier1 = fichier1;
    }

    public String getFichier1ContentType() {
        return fichier1ContentType;
    }

    public void setFichier1ContentType(String fichier1ContentType) {
        this.fichier1ContentType = fichier1ContentType;
    }

    public byte[] getFichier2() {
        return fichier2;
    }

    public void setFichier2(byte[] fichier2) {
        this.fichier2 = fichier2;
    }

    public String getFichier2ContentType() {
        return fichier2ContentType;
    }

    public void setFichier2ContentType(String fichier2ContentType) {
        this.fichier2ContentType = fichier2ContentType;
    }

    public byte[] getFichier3() {
        return fichier3;
    }

    public void setFichier3(byte[] fichier3) {
        this.fichier3 = fichier3;
    }

    public String getFichier3ContentType() {
        return fichier3ContentType;
    }

    public void setFichier3ContentType(String fichier3ContentType) {
        this.fichier3ContentType = fichier3ContentType;
    }

    public byte[] getFichier4() {
        return fichier4;
    }

    public void setFichier4(byte[] fichier4) {
        this.fichier4 = fichier4;
    }

    public String getFichier4ContentType() {
        return fichier4ContentType;
    }

    public void setFichier4ContentType(String fichier4ContentType) {
        this.fichier4ContentType = fichier4ContentType;
    }

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvancementDTO)) {
            return false;
        }

        AvancementDTO avancementDTO = (AvancementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avancementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvancementDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", fichier1='" + getFichier1() + "'" +
            ", fichier2='" + getFichier2() + "'" +
            ", fichier3='" + getFichier3() + "'" +
            ", fichier4='" + getFichier4() + "'" +
            ", projet=" + getProjet() +
            "}";
    }
}
