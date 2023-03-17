package com.degrize.hseapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Regle entity.\n@author MEDA.
 */
@Entity
@Table(name = "regle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "texte", nullable = false)
    private String texte;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

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

    public Regle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return this.texte;
    }

    public Regle texte(String texte) {
        this.setTexte(texte);
        return this;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Instant getDate() {
        return this.date;
    }

    public Regle date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Regle projet(Projet projet) {
        this.setProjet(projet);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Regle user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regle)) {
            return false;
        }
        return id != null && id.equals(((Regle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regle{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
