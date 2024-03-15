package mp.mpro.integra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Area.
 */
@Entity
@Table(name = "area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "area" }, allowSetters = true)
    private Set<Sistema> sistemas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Area id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Area nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Sistema> getSistemas() {
        return this.sistemas;
    }

    public void setSistemas(Set<Sistema> sistemas) {
        if (this.sistemas != null) {
            this.sistemas.forEach(i -> i.setArea(null));
        }
        if (sistemas != null) {
            sistemas.forEach(i -> i.setArea(this));
        }
        this.sistemas = sistemas;
    }

    public Area sistemas(Set<Sistema> sistemas) {
        this.setSistemas(sistemas);
        return this;
    }

    public Area addSistema(Sistema sistema) {
        this.sistemas.add(sistema);
        sistema.setArea(this);
        return this;
    }

    public Area removeSistema(Sistema sistema) {
        this.sistemas.remove(sistema);
        sistema.setArea(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Area)) {
            return false;
        }
        return getId() != null && getId().equals(((Area) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Area{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
