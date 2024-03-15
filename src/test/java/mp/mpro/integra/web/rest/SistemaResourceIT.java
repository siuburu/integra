package mp.mpro.integra.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mp.mpro.integra.IntegrationTest;
import mp.mpro.integra.domain.Sistema;
import mp.mpro.integra.repository.SistemaRepository;
import mp.mpro.integra.service.dto.SistemaDTO;
import mp.mpro.integra.service.mapper.SistemaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SistemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SistemaResourceIT {

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_URL = "AAAAAAAAAA";
    private static final String UPDATED_HOME_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sistemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    private SistemaMapper sistemaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSistemaMockMvc;

    private Sistema sistema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sistema createEntity(EntityManager em) {
        Sistema sistema = new Sistema()
            .group(DEFAULT_GROUP)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .homeUrl(DEFAULT_HOME_URL)
            .logoUrl(DEFAULT_LOGO_URL);
        return sistema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sistema createUpdatedEntity(EntityManager em) {
        Sistema sistema = new Sistema()
            .group(UPDATED_GROUP)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .homeUrl(UPDATED_HOME_URL)
            .logoUrl(UPDATED_LOGO_URL);
        return sistema;
    }

    @BeforeEach
    public void initTest() {
        sistema = createEntity(em);
    }

    @Test
    @Transactional
    void createSistema() throws Exception {
        int databaseSizeBeforeCreate = sistemaRepository.findAll().size();
        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);
        restSistemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeCreate + 1);
        Sistema testSistema = sistemaList.get(sistemaList.size() - 1);
        assertThat(testSistema.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testSistema.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSistema.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testSistema.getHomeUrl()).isEqualTo(DEFAULT_HOME_URL);
        assertThat(testSistema.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
    }

    @Test
    @Transactional
    void createSistemaWithExistingId() throws Exception {
        // Create the Sistema with an existing ID
        sistema.setId(1L);
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        int databaseSizeBeforeCreate = sistemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSistemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = sistemaRepository.findAll().size();
        // set the field null
        sistema.setGroup(null);

        // Create the Sistema, which fails.
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        restSistemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sistemaRepository.findAll().size();
        // set the field null
        sistema.setNome(null);

        // Create the Sistema, which fails.
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        restSistemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sistemaRepository.findAll().size();
        // set the field null
        sistema.setDescricao(null);

        // Create the Sistema, which fails.
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        restSistemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSistemas() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].homeUrl").value(hasItem(DEFAULT_HOME_URL)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(DEFAULT_LOGO_URL)));
    }

    @Test
    @Transactional
    void getSistema() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        // Get the sistema
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL_ID, sistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sistema.getId().intValue()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.homeUrl").value(DEFAULT_HOME_URL))
            .andExpect(jsonPath("$.logoUrl").value(DEFAULT_LOGO_URL));
    }

    @Test
    @Transactional
    void getNonExistingSistema() throws Exception {
        // Get the sistema
        restSistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSistema() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();

        // Update the sistema
        Sistema updatedSistema = sistemaRepository.findById(sistema.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSistema are not directly saved in db
        em.detach(updatedSistema);
        updatedSistema
            .group(UPDATED_GROUP)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .homeUrl(UPDATED_HOME_URL)
            .logoUrl(UPDATED_LOGO_URL);
        SistemaDTO sistemaDTO = sistemaMapper.toDto(updatedSistema);

        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sistemaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
        Sistema testSistema = sistemaList.get(sistemaList.size() - 1);
        assertThat(testSistema.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testSistema.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSistema.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSistema.getHomeUrl()).isEqualTo(UPDATED_HOME_URL);
        assertThat(testSistema.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void putNonExistingSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sistemaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSistemaWithPatch() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();

        // Update the sistema using partial update
        Sistema partialUpdatedSistema = new Sistema();
        partialUpdatedSistema.setId(sistema.getId());

        partialUpdatedSistema.group(UPDATED_GROUP).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).homeUrl(UPDATED_HOME_URL);

        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSistema.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSistema))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
        Sistema testSistema = sistemaList.get(sistemaList.size() - 1);
        assertThat(testSistema.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testSistema.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSistema.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSistema.getHomeUrl()).isEqualTo(UPDATED_HOME_URL);
        assertThat(testSistema.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
    }

    @Test
    @Transactional
    void fullUpdateSistemaWithPatch() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();

        // Update the sistema using partial update
        Sistema partialUpdatedSistema = new Sistema();
        partialUpdatedSistema.setId(sistema.getId());

        partialUpdatedSistema
            .group(UPDATED_GROUP)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .homeUrl(UPDATED_HOME_URL)
            .logoUrl(UPDATED_LOGO_URL);

        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSistema.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSistema))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
        Sistema testSistema = sistemaList.get(sistemaList.size() - 1);
        assertThat(testSistema.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testSistema.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSistema.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSistema.getHomeUrl()).isEqualTo(UPDATED_HOME_URL);
        assertThat(testSistema.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void patchNonExistingSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sistemaDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSistema() throws Exception {
        int databaseSizeBeforeUpdate = sistemaRepository.findAll().size();
        sistema.setId(longCount.incrementAndGet());

        // Create the Sistema
        SistemaDTO sistemaDTO = sistemaMapper.toDto(sistema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sistemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sistema in the database
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSistema() throws Exception {
        // Initialize the database
        sistemaRepository.saveAndFlush(sistema);

        int databaseSizeBeforeDelete = sistemaRepository.findAll().size();

        // Delete the sistema
        restSistemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, sistema.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sistema> sistemaList = sistemaRepository.findAll();
        assertThat(sistemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
