package mp.mpro.integra.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mp.mpro.integra.domain.Sistema} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SistemaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String group;

    @NotNull
    @Size(min = 3)
    private String nome;

    @NotNull
    @Size(min = 10)
    private String descricao;

    private String homeUrl;

    private String logoUrl;

    private AreaDTO area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public AreaDTO getArea() {
        return area;
    }

    public void setArea(AreaDTO area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SistemaDTO)) {
            return false;
        }

        SistemaDTO sistemaDTO = (SistemaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sistemaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SistemaDTO{" +
            "id=" + getId() +
            ", group='" + getGroup() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", homeUrl='" + getHomeUrl() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", area=" + getArea() +
            "}";
    }
}
