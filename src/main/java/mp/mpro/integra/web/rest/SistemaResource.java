package mp.mpro.integra.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;
import mp.mpro.integra.domain.Sistema;
import mp.mpro.integra.repository.SistemaRepository;
import mp.mpro.integra.service.SistemaService;
import mp.mpro.integra.service.dto.SistemaDTO;
import mp.mpro.integra.service.mapper.SistemaMapper;
import mp.mpro.integra.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mp.mpro.integra.domain.Sistema}.
 */
@RestController
@RequestMapping("/api/sistemas")
@Transactional
public class SistemaResource {

    private final Logger log = LoggerFactory.getLogger(SistemaResource.class);

    private static final String ENTITY_NAME = "sistema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SistemaRepository sistemaRepository;

    private final SistemaMapper sistemaMapper;

    public SistemaResource(SistemaRepository sistemaRepository, SistemaMapper sistemaMapper) {
        this.sistemaRepository = sistemaRepository;
        this.sistemaMapper = sistemaMapper;
    }

    /**
     * {@code POST  /sistemas} : Create a new sistema.
     *
     * @param sistemaDTO the sistemaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sistemaDTO, or with status {@code 400 (Bad Request)} if the sistema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SistemaDTO> createSistema(@Valid @RequestBody SistemaDTO sistemaDTO) throws URISyntaxException {
        log.debug("REST request to save Sistema : {}", sistemaDTO);
        if (sistemaDTO.getId() != null) {
            throw new BadRequestAlertException("A new sistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sistema sistema = sistemaMapper.toEntity(sistemaDTO);
        sistema = sistemaRepository.save(sistema);
        SistemaDTO result = sistemaMapper.toDto(sistema);
        return ResponseEntity
            .created(new URI("/api/sistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sistemas/:id} : Updates an existing sistema.
     *
     * @param id the id of the sistemaDTO to save.
     * @param sistemaDTO the sistemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sistemaDTO,
     * or with status {@code 400 (Bad Request)} if the sistemaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sistemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SistemaDTO> updateSistema(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SistemaDTO sistemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sistema : {}, {}", id, sistemaDTO);
        if (sistemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sistemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sistema sistema = sistemaMapper.toEntity(sistemaDTO);
        sistema = sistemaRepository.save(sistema);
        SistemaDTO result = sistemaMapper.toDto(sistema);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sistemaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sistemas/:id} : Partial updates given fields of an existing sistema, field will ignore if it is null
     *
     * @param id the id of the sistemaDTO to save.
     * @param sistemaDTO the sistemaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sistemaDTO,
     * or with status {@code 400 (Bad Request)} if the sistemaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sistemaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sistemaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SistemaDTO> partialUpdateSistema(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SistemaDTO sistemaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sistema partially : {}, {}", id, sistemaDTO);
        if (sistemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sistemaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sistemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SistemaDTO> result = sistemaRepository
            .findById(sistemaDTO.getId())
            .map(existingSistema -> {
                sistemaMapper.partialUpdate(existingSistema, sistemaDTO);

                return existingSistema;
            })
            .map(sistemaRepository::save)
            .map(sistemaMapper::toDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sistemaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sistemas} : get all the sistemas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sistemas in body.
     */
    @GetMapping("")
    public List<SistemaDTO> getAllSistemas() {
        log.debug("REST request to get all Sistemas");
        List<Sistema> sistemas = sistemaRepository.findAll();
        return sistemaMapper.toDto(sistemas);
    }

    /**
     * {@code GET  /sistemas} : get all the sistemas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sistemas in body.
     */
    @GetMapping("/all")
    public List<SistemaDTO> getUserSistemas(Principal principal) {
        //List<String> groups =
        Map<String, Object> attributes;
        if (principal instanceof AbstractAuthenticationToken) {
            if (principal instanceof OAuth2AuthenticationToken) {
                attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            } else if (principal instanceof JwtAuthenticationToken) {
                attributes = ((JwtAuthenticationToken) principal).getTokenAttributes();
            } else {
                throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
            }
        } else {
            throw new RuntimeException("User could not be found");
        }
        List<String> result = (List<String>) attributes.getOrDefault("groups", new ArrayList<>());
        log.info("RESULT: " + result.toString());
        log.info("RESULT 1: " + result.get(0).toString());
        List<Sistema> sistemas = sistemaRepository.findSistemasByGroupIn(result);

        return sistemaMapper.toDto(sistemas);
    }

    /**
     * {@code GET  /sistemas/:id} : get the "id" sistema.
     *
     * @param id the id of the sistemaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sistemaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SistemaDTO> getSistema(@PathVariable("id") Long id) {
        log.debug("REST request to get Sistema : {}", id);
        Optional<SistemaDTO> sistemaDTO = sistemaRepository.findById(id).map(sistemaMapper::toDto);
        return ResponseUtil.wrapOrNotFound(sistemaDTO);
    }

    /**
     * {@code DELETE  /sistemas/:id} : delete the "id" sistema.
     *
     * @param id the id of the sistemaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSistema(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sistema : {}", id);
        sistemaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
