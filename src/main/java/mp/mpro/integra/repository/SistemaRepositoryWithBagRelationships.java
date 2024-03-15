package mp.mpro.integra.repository;

import java.util.List;
import java.util.Optional;
import mp.mpro.integra.domain.Sistema;
import org.springframework.data.domain.Page;

public interface SistemaRepositoryWithBagRelationships {
    Optional<Sistema> fetchBagRelationships(Optional<Sistema> sistema);

    List<Sistema> fetchBagRelationships(List<Sistema> sistemas);

    Page<Sistema> fetchBagRelationships(Page<Sistema> sistemas);
}
