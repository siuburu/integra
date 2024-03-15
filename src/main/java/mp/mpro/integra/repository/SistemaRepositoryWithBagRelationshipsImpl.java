package mp.mpro.integra.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mp.mpro.integra.domain.Sistema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SistemaRepositoryWithBagRelationshipsImpl implements SistemaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Sistema> fetchBagRelationships(Optional<Sistema> sistema) {
        return sistema.map(this::fetchUsers);
    }

    @Override
    public Page<Sistema> fetchBagRelationships(Page<Sistema> sistemas) {
        return new PageImpl<>(fetchBagRelationships(sistemas.getContent()), sistemas.getPageable(), sistemas.getTotalElements());
    }

    @Override
    public List<Sistema> fetchBagRelationships(List<Sistema> sistemas) {
        return Optional.of(sistemas).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Sistema fetchUsers(Sistema result) {
        return entityManager
            .createQuery("select sistema from Sistema sistema left join fetch sistema.users where sistema.id = :id", Sistema.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Sistema> fetchUsers(List<Sistema> sistemas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sistemas.size()).forEach(index -> order.put(sistemas.get(index).getId(), index));
        List<Sistema> result = entityManager
            .createQuery("select sistema from Sistema sistema left join fetch sistema.users where sistema in :sistemas", Sistema.class)
            .setParameter("sistemas", sistemas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
