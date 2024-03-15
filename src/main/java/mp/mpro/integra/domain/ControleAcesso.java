package mp.mpro.integra.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ControleAcesso.
 */
@Entity
@Table(name = "controle_acesso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControleAcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_acesso")
    private Instant dataAcesso;

    @Column(name = "ip_acesso")
    private String ipAcesso;

    @Column(name = "nome_dispositivo")
    private String nomeDispositivo;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ControleAcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAcesso() {
        return this.dataAcesso;
    }

    public ControleAcesso dataAcesso(Instant dataAcesso) {
        this.setDataAcesso(dataAcesso);
        return this;
    }

    public void setDataAcesso(Instant dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    public String getIpAcesso() {
        return this.ipAcesso;
    }

    public ControleAcesso ipAcesso(String ipAcesso) {
        this.setIpAcesso(ipAcesso);
        return this;
    }

    public void setIpAcesso(String ipAcesso) {
        this.ipAcesso = ipAcesso;
    }

    public String getNomeDispositivo() {
        return this.nomeDispositivo;
    }

    public ControleAcesso nomeDispositivo(String nomeDispositivo) {
        this.setNomeDispositivo(nomeDispositivo);
        return this;
    }

    public void setNomeDispositivo(String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ControleAcesso user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleAcesso)) {
            return false;
        }
        return getId() != null && getId().equals(((ControleAcesso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleAcesso{" +
            "id=" + getId() +
            ", dataAcesso='" + getDataAcesso() + "'" +
            ", ipAcesso='" + getIpAcesso() + "'" +
            ", nomeDispositivo='" + getNomeDispositivo() + "'" +
            "}";
    }
}
