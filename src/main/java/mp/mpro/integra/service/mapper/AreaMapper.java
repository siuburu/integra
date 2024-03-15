package mp.mpro.integra.service.mapper;

import mp.mpro.integra.domain.Area;
import mp.mpro.integra.service.dto.AreaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Area} and its DTO {@link AreaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaMapper extends EntityMapper<AreaDTO, Area> {}
