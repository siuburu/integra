package mp.mpro.integra.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mp.mpro.integra.domain.ControleAcesso;
import mp.mpro.integra.repository.ControleAcessoRepository;
import mp.mpro.integra.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mp.mpro.integra.domain.ControleAcesso}.
 */
@RestController
@RequestMapping("/api/controle-acessos")
@Transactional
public class ControleAcessoResource {

    private final Logger log = LoggerFactory.getLogger(ControleAcessoResource.class);

    private static final String ENTITY_NAME = "controleAcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleAcessoRepository controleAcessoRepository;

    public ControleAcessoResource(ControleAcessoRepository controleAcessoRepository) {
        this.controleAcessoRepository = controleAcessoRepository;
    }

    /**
     * {@code POST  /controle-acessos} : Create a new controleAcesso.
     *
     * @param controleAcesso the controleAcesso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleAcesso, or with status {@code 400 (Bad Request)} if the controleAcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControleAcesso> createControleAcesso(@RequestBody ControleAcesso controleAcesso) throws URISyntaxException {
        log.debug("REST request to save ControleAcesso : {}", controleAcesso);
        if (controleAcesso.getId() != null) {
            throw new BadRequestAlertException("A new controleAcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleAcesso result = controleAcessoRepository.save(controleAcesso);
        return ResponseEntity
            .created(new URI("/api/controle-acessos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-acessos/:id} : Updates an existing controleAcesso.
     *
     * @param id the id of the controleAcesso to save.
     * @param controleAcesso the controleAcesso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleAcesso,
     * or with status {@code 400 (Bad Request)} if the controleAcesso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleAcesso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControleAcesso> updateControleAcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleAcesso controleAcesso
    ) throws URISyntaxException {
        log.debug("REST request to update ControleAcesso : {}, {}", id, controleAcesso);
        if (controleAcesso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleAcesso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleAcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControleAcesso result = controleAcessoRepository.save(controleAcesso);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleAcesso.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controle-acessos/:id} : Partial updates given fields of an existing controleAcesso, field will ignore if it is null
     *
     * @param id the id of the controleAcesso to save.
     * @param controleAcesso the controleAcesso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleAcesso,
     * or with status {@code 400 (Bad Request)} if the controleAcesso is not valid,
     * or with status {@code 404 (Not Found)} if the controleAcesso is not found,
     * or with status {@code 500 (Internal Server Error)} if the controleAcesso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControleAcesso> partialUpdateControleAcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleAcesso controleAcesso
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControleAcesso partially : {}, {}", id, controleAcesso);
        if (controleAcesso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleAcesso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleAcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControleAcesso> result = controleAcessoRepository
            .findById(controleAcesso.getId())
            .map(existingControleAcesso -> {
                if (controleAcesso.getDataAcesso() != null) {
                    existingControleAcesso.setDataAcesso(controleAcesso.getDataAcesso());
                }
                if (controleAcesso.getIpAcesso() != null) {
                    existingControleAcesso.setIpAcesso(controleAcesso.getIpAcesso());
                }
                if (controleAcesso.getNomeDispositivo() != null) {
                    existingControleAcesso.setNomeDispositivo(controleAcesso.getNomeDispositivo());
                }

                return existingControleAcesso;
            })
            .map(controleAcessoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleAcesso.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-acessos} : get all the controleAcessos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleAcessos in body.
     */
    @GetMapping("")
    public List<ControleAcesso> getAllControleAcessos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ControleAcessos");
        if (eagerload) {
            return controleAcessoRepository.findAllWithEagerRelationships();
        } else {
            return controleAcessoRepository.findAll();
        }
    }

    /**
     * {@code GET  /controle-acessos/:id} : get the "id" controleAcesso.
     *
     * @param id the id of the controleAcesso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleAcesso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControleAcesso> getControleAcesso(@PathVariable("id") Long id) {
        log.debug("REST request to get ControleAcesso : {}", id);
        Optional<ControleAcesso> controleAcesso = controleAcessoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(controleAcesso);
    }

    /**
     * {@code DELETE  /controle-acessos/:id} : delete the "id" controleAcesso.
     *
     * @param id the id of the controleAcesso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleAcesso(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControleAcesso : {}", id);
        controleAcessoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
