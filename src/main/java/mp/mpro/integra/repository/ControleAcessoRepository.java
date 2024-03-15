package mp.mpro.integra.repository;

import java.util.List;
import java.util.Optional;
import mp.mpro.integra.domain.ControleAcesso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ControleAcesso entity.
 */
@Repository
public interface ControleAcessoRepository extends JpaRepository<ControleAcesso, Long> {
    @Query("select controleAcesso from ControleAcesso controleAcesso where controleAcesso.user.login = ?#{authentication.name}")
    List<ControleAcesso> findByUserIsCurrentUser();

    default Optional<ControleAcesso> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ControleAcesso> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ControleAcesso> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select controleAcesso from ControleAcesso controleAcesso left join fetch controleAcesso.user",
        countQuery = "select count(controleAcesso) from ControleAcesso controleAcesso"
    )
    Page<ControleAcesso> findAllWithToOneRelationships(Pageable pageable);

    @Query("select controleAcesso from ControleAcesso controleAcesso left join fetch controleAcesso.user")
    List<ControleAcesso> findAllWithToOneRelationships();

    @Query("select controleAcesso from ControleAcesso controleAcesso left join fetch controleAcesso.user where controleAcesso.id =:id")
    Optional<ControleAcesso> findOneWithToOneRelationships(@Param("id") Long id);
}
