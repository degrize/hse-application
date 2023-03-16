package com.degrize.hseapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.degrize.hseapp.domain.Projet} entity.
 */
@Schema(description = "Projet entity.\n@author MEDA.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjetDTO implements Serializable {

    private Long id;

    @NotNull
    private String titre;

    private String description;

    private String duree;
    private String uniteDuree;

    private String ville;

    private String code;

    /**
     * Joindre une image au projet\n@author MEDA.
     */
    @Schema(description = "Joindre une image au projet\n@author MEDA.")
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
    private Boolean isDone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getUniteDuree() {
        return uniteDuree;
    }

    public void setUniteDuree(String uniteDuree) {
        this.uniteDuree = uniteDuree;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjetDTO)) {
            return false;
        }

        ProjetDTO projetDTO = (ProjetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjetDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree='" + getDuree() + "'" +
            ", ville='" + getVille() + "'" +
            ", code='" + getCode() + "'" +
            ", fichier1='" + getFichier1() + "'" +
            ", fichier2='" + getFichier2() + "'" +
            ", fichier3='" + getFichier3() + "'" +
            ", fichier4='" + getFichier4() + "'" +
            ", isDone='" + getIsDone() + "'" +
            ", uniteDuree='" + getUniteDuree() + "'" +
            "}";
    }
}
