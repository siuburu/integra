package mp.mpro.integra.service.mapper;

import mp.mpro.integra.domain.Area;
import mp.mpro.integra.domain.Sistema;
import mp.mpro.integra.service.dto.AreaDTO;
import mp.mpro.integra.service.dto.SistemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sistema} and its DTO {@link SistemaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SistemaMapper extends EntityMapper<SistemaDTO, Sistema> {
    @Mapping(target = "area", source = "area", qualifiedByName = "areaId")
    SistemaDTO toDto(Sistema s);

    @Named("areaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AreaDTO toDtoAreaId(Area area);
}
