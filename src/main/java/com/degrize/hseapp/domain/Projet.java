package com.degrize.hseapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Projet entity.\n@author MEDA.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "duree")
    private String duree;

    @Column(name = "ville")
    private String ville;

    @Column(name = "code")
    private String code;

    /**
     * Joindre une image au projet\n@author MEDA.
     */
    @Lob
    @Column(name = "fichier_1")
    private byte[] fichier1;

    @Column(name = "fichier_1_content_type")
    private String fichier1ContentType;

    @Lob
    @Column(name = "fichier_2")
    private byte[] fichier2;

    @Column(name = "fichier_2_content_type")
    private String fichier2ContentType;

    @Lob
    @Column(name = "fichier_3")
    private byte[] fichier3;

    @Column(name = "fichier_3_content_type")
    private String fichier3ContentType;

    @Lob
    @Column(name = "fichier_4")
    private byte[] fichier4;

    @Column(name = "fichier_4_content_type")
    private String fichier4ContentType;

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projet" }, allowSetters = true)
    private Set<Regle> regles = new HashSet<>();

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projet" }, allowSetters = true)
    private Set<Signalement> signalements = new HashSet<>();

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projet" }, allowSetters = true)
    private Set<Avancement> avancements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public Projet titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public Projet description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return this.duree;
    }

    public Projet duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getVille() {
        return this.ville;
    }

    public Projet ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCode() {
        return this.code;
    }

    public Projet code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getFichier1() {
        return this.fichier1;
    }

    public Projet fichier1(byte[] fichier1) {
        this.setFichier1(fichier1);
        return this;
    }

    public void setFichier1(byte[] fichier1) {
        this.fichier1 = fichier1;
    }

    public String getFichier1ContentType() {
        return this.fichier1ContentType;
    }

    public Projet fichier1ContentType(String fichier1ContentType) {
        this.fichier1ContentType = fichier1ContentType;
        return this;
    }

    public void setFichier1ContentType(String fichier1ContentType) {
        this.fichier1ContentType = fichier1ContentType;
    }

    public byte[] getFichier2() {
        return this.fichier2;
    }

    public Projet fichier2(byte[] fichier2) {
        this.setFichier2(fichier2);
        return this;
    }

    public void setFichier2(byte[] fichier2) {
        this.fichier2 = fichier2;
    }

    public String getFichier2ContentType() {
        return this.fichier2ContentType;
    }

    public Projet fichier2ContentType(String fichier2ContentType) {
        this.fichier2ContentType = fichier2ContentType;
        return this;
    }

    public void setFichier2ContentType(String fichier2ContentType) {
        this.fichier2ContentType = fichier2ContentType;
    }

    public byte[] getFichier3() {
        return this.fichier3;
    }

    public Projet fichier3(byte[] fichier3) {
        this.setFichier3(fichier3);
        return this;
    }

    public void setFichier3(byte[] fichier3) {
        this.fichier3 = fichier3;
    }

    public String getFichier3ContentType() {
        return this.fichier3ContentType;
    }

    public Projet fichier3ContentType(String fichier3ContentType) {
        this.fichier3ContentType = fichier3ContentType;
        return this;
    }

    public void setFichier3ContentType(String fichier3ContentType) {
        this.fichier3ContentType = fichier3ContentType;
    }

    public byte[] getFichier4() {
        return this.fichier4;
    }

    public Projet fichier4(byte[] fichier4) {
        this.setFichier4(fichier4);
        return this;
    }

    public void setFichier4(byte[] fichier4) {
        this.fichier4 = fichier4;
    }

    public String getFichier4ContentType() {
        return this.fichier4ContentType;
    }

    public Projet fichier4ContentType(String fichier4ContentType) {
        this.fichier4ContentType = fichier4ContentType;
        return this;
    }

    public void setFichier4ContentType(String fichier4ContentType) {
        this.fichier4ContentType = fichier4ContentType;
    }

    public Set<Regle> getRegles() {
        return this.regles;
    }

    public void setRegles(Set<Regle> regles) {
        if (this.regles != null) {
            this.regles.forEach(i -> i.setProjet(null));
        }
        if (regles != null) {
            regles.forEach(i -> i.setProjet(this));
        }
        this.regles = regles;
    }

    public Projet regles(Set<Regle> regles) {
        this.setRegles(regles);
        return this;
    }

    public Projet addRegle(Regle regle) {
        this.regles.add(regle);
        regle.setProjet(this);
        return this;
    }

    public Projet removeRegle(Regle regle) {
        this.regles.remove(regle);
        regle.setProjet(null);
        return this;
    }

    public Set<Signalement> getSignalements() {
        return this.signalements;
    }

    public void setSignalements(Set<Signalement> signalements) {
        if (this.signalements != null) {
            this.signalements.forEach(i -> i.setProjet(null));
        }
        if (signalements != null) {
            signalements.forEach(i -> i.setProjet(this));
        }
        this.signalements = signalements;
    }

    public Projet signalements(Set<Signalement> signalements) {
        this.setSignalements(signalements);
        return this;
    }

    public Projet addSignalement(Signalement signalement) {
        this.signalements.add(signalement);
        signalement.setProjet(this);
        return this;
    }

    public Projet removeSignalement(Signalement signalement) {
        this.signalements.remove(signalement);
        signalement.setProjet(null);
        return this;
    }

    public Set<Avancement> getAvancements() {
        return this.avancements;
    }

    public void setAvancements(Set<Avancement> avancements) {
        if (this.avancements != null) {
            this.avancements.forEach(i -> i.setProjet(null));
        }
        if (avancements != null) {
            avancements.forEach(i -> i.setProjet(this));
        }
        this.avancements = avancements;
    }

    public Projet avancements(Set<Avancement> avancements) {
        this.setAvancements(avancements);
        return this;
    }

    public Projet addAvancement(Avancement avancement) {
        this.avancements.add(avancement);
        avancement.setProjet(this);
        return this;
    }

    public Projet removeAvancement(Avancement avancement) {
        this.avancements.remove(avancement);
        avancement.setProjet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree='" + getDuree() + "'" +
            ", ville='" + getVille() + "'" +
            ", code='" + getCode() + "'" +
            ", fichier1='" + getFichier1() + "'" +
            ", fichier1ContentType='" + getFichier1ContentType() + "'" +
            ", fichier2='" + getFichier2() + "'" +
            ", fichier2ContentType='" + getFichier2ContentType() + "'" +
            ", fichier3='" + getFichier3() + "'" +
            ", fichier3ContentType='" + getFichier3ContentType() + "'" +
            ", fichier4='" + getFichier4() + "'" +
            ", fichier4ContentType='" + getFichier4ContentType() + "'" +
            "}";
    }
}
