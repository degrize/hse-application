package com.degrize.hseapp.domain;

import com.degrize.hseapp.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Size(min = 10, max = 10)
    @Column(name = "contact", length = 10, unique = true)
    private String contact;

    @Column(name = "ville")
    private String ville;

    @Column(name = "quartier_ou_commune")
    private String quartierOuCommune;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jhi_user" }, allowSetters = true)
    private Set<Avancement> avancements = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jhi_user" }, allowSetters = true)
    private Set<Regle> regles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jhi_user" }, allowSetters = true)
    private Set<Signalement> signalements = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jhi_user" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getQuartierOuCommune() {
        return quartierOuCommune;
    }

    public void setQuartierOuCommune(String quartierOuCommune) {
        this.quartierOuCommune = quartierOuCommune;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Regle> getRegles() {
        return this.regles;
    }

    public void setRegles(Set<Regle> regles) {
        if (this.regles != null) {
            this.regles.forEach(i -> i.setUser(null));
        }
        if (regles != null) {
            regles.forEach(i -> i.setUser(this));
        }
        this.regles = regles;
    }

    public User regles(Set<Regle> regles) {
        this.setRegles(regles);
        return this;
    }

    public User addRegle(Regle regle) {
        this.regles.add(regle);
        regle.setUser(this);
        return this;
    }

    public User removeRegle(Regle regle) {
        this.regles.remove(regle);
        regle.setUser(null);
        return this;
    }

    public Set<Signalement> getSignalements() {
        return this.signalements;
    }

    public void setSignalements(Set<Signalement> signalements) {
        if (this.signalements != null) {
            this.signalements.forEach(i -> i.setUser(null));
        }
        if (signalements != null) {
            signalements.forEach(i -> i.setUser(this));
        }
        this.signalements = signalements;
    }

    public User signalements(Set<Signalement> signalements) {
        this.setSignalements(signalements);
        return this;
    }

    public User addSignalement(Signalement signalement) {
        this.signalements.add(signalement);
        signalement.setUser(this);
        return this;
    }

    public User removeSignalement(Signalement signalement) {
        this.signalements.remove(signalement);
        signalement.setUser(null);
        return this;
    }

    public Set<Avancement> getAvancements() {
        return this.avancements;
    }

    public void setAvancements(Set<Avancement> avancements) {
        if (this.avancements != null) {
            this.avancements.forEach(i -> i.setUser(null));
        }
        if (avancements != null) {
            avancements.forEach(i -> i.setUser(this));
        }
        this.avancements = avancements;
    }

    public User avancements(Set<Avancement> avancements) {
        this.setAvancements(avancements);
        return this;
    }

    public User addAvancement(Avancement avancement) {
        this.avancements.add(avancement);
        avancement.setUser(this);
        return this;
    }

    public User removeAvancement(Avancement avancement) {
        this.avancements.remove(avancement);
        avancement.setUser(null);
        return this;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        if (this.projets != null) {
            this.projets.forEach(i -> i.setUser(null));
        }
        if (projets != null) {
            projets.forEach(i -> i.setUser(this));
        }
        this.projets = projets;
    }

    public User projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public User addProjet(Projet projet) {
        this.projets.add(projet);
        projet.setUser(this);
        return this;
    }

    public User removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.setUser(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", contact='" + contact + '\'' +
            ", ville='" + ville + '\'' +
            ", quartierOuCommune='" + quartierOuCommune + '\'' +
            "}";
    }
}
