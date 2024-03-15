package mp.mpro.integra.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import mp.mpro.integra.domain.ControleAcesso;
import mp.mpro.integra.domain.Sistema;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SistemaRepository extends JpaRepository<Sistema, Long> {
    @Query("select sistema from Sistema sistema where sistema.group in :groups")
    List<Sistema> findByGroups(@Param("groups") List<String> groups);

    List<Sistema> findSistemasByGroupIn(List<String> groups);
}
