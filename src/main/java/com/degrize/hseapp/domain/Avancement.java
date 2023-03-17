package com.degrize.hseapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Avancement entity.\n@author MEDA.
 */
@Entity
@Table(name = "avancement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Avancement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "regles", "signalements", "avancements" }, allowSetters = true)
    private Projet projet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "regles", "signalements", "avancements", "projets" }, allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avancement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Avancement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Avancement date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getFichier1() {
        return this.fichier1;
    }

    public Avancement fichier1(byte[] fichier1) {
        this.setFichier1(fichier1);
        return this;
    }

    public void setFichier1(byte[] fichier1) {
        this.fichier1 = fichier1;
    }

    public String getFichier1ContentType() {
        return this.fichier1ContentType;
    }

    public Avancement fichier1ContentType(String fichier1ContentType) {
        this.fichier1ContentType = fichier1ContentType;
        return this;
    }

    public void setFichier1ContentType(String fichier1ContentType) {
        this.fichier1ContentType = fichier1ContentType;
    }

    public byte[] getFichier2() {
        return this.fichier2;
    }

    public Avancement fichier2(byte[] fichier2) {
        this.setFichier2(fichier2);
        return this;
    }

    public void setFichier2(byte[] fichier2) {
        this.fichier2 = fichier2;
    }

    public String getFichier2ContentType() {
        return this.fichier2ContentType;
    }

    public Avancement fichier2ContentType(String fichier2ContentType) {
        this.fichier2ContentType = fichier2ContentType;
        return this;
    }

    public void setFichier2ContentType(String fichier2ContentType) {
        this.fichier2ContentType = fichier2ContentType;
    }

    public byte[] getFichier3() {
        return this.fichier3;
    }

    public Avancement fichier3(byte[] fichier3) {
        this.setFichier3(fichier3);
        return this;
    }

    public void setFichier3(byte[] fichier3) {
        this.fichier3 = fichier3;
    }

    public String getFichier3ContentType() {
        return this.fichier3ContentType;
    }

    public Avancement fichier3ContentType(String fichier3ContentType) {
        this.fichier3ContentType = fichier3ContentType;
        return this;
    }

    public void setFichier3ContentType(String fichier3ContentType) {
        this.fichier3ContentType = fichier3ContentType;
    }

    public byte[] getFichier4() {
        return this.fichier4;
    }

    public Avancement fichier4(byte[] fichier4) {
        this.setFichier4(fichier4);
        return this;
    }

    public void setFichier4(byte[] fichier4) {
        this.fichier4 = fichier4;
    }

    public String getFichier4ContentType() {
        return this.fichier4ContentType;
    }

    public Avancement fichier4ContentType(String fichier4ContentType) {
        this.fichier4ContentType = fichier4ContentType;
        return this;
    }

    public void setFichier4ContentType(String fichier4ContentType) {
        this.fichier4ContentType = fichier4ContentType;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Avancement projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avancement user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avancement)) {
            return false;
        }
        return id != null && id.equals(((Avancement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avancement{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
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
