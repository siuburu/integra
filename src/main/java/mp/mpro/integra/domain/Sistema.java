package mp.mpro.integra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sistema.
 */
@Entity
@Table(name = "sistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "jhi_group", nullable = false)
    private String group;

    @NotNull
    @Size(min = 3)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Size(min = 10)
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "home_url")
    private String homeUrl;

    @Column(name = "logo_url")
    private String logoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sistemas" }, allowSetters = true)
    private Area area;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sistema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return this.group;
    }

    public Sistema group(String group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNome() {
        return this.nome;
    }

    public Sistema nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Sistema descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHomeUrl() {
        return this.homeUrl;
    }

    public Sistema homeUrl(String homeUrl) {
        this.setHomeUrl(homeUrl);
        return this;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public Sistema logoUrl(String logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Sistema area(Area area) {
        this.setArea(area);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sistema)) {
            return false;
        }
        return getId() != null && getId().equals(((Sistema) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sistema{" +
            "id=" + getId() +
            ", group='" + getGroup() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", homeUrl='" + getHomeUrl() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            "}";
    }
}
