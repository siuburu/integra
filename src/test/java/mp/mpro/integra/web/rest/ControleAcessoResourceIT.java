package mp.mpro.integra.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mp.mpro.integra.IntegrationTest;
import mp.mpro.integra.domain.ControleAcesso;
import mp.mpro.integra.repository.ControleAcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ControleAcessoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ControleAcessoResourceIT {

    private static final Instant DEFAULT_DATA_ACESSO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ACESSO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP_ACESSO = "AAAAAAAAAA";
    private static final String UPDATED_IP_ACESSO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DISPOSITIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DISPOSITIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/controle-acessos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControleAcessoRepository controleAcessoRepository;

    @Mock
    private ControleAcessoRepository controleAcessoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleAcessoMockMvc;

    private ControleAcesso controleAcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleAcesso createEntity(EntityManager em) {
        ControleAcesso controleAcesso = new ControleAcesso()
            .dataAcesso(DEFAULT_DATA_ACESSO)
            .ipAcesso(DEFAULT_IP_ACESSO)
            .nomeDispositivo(DEFAULT_NOME_DISPOSITIVO);
        return controleAcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleAcesso createUpdatedEntity(EntityManager em) {
        ControleAcesso controleAcesso = new ControleAcesso()
            .dataAcesso(UPDATED_DATA_ACESSO)
            .ipAcesso(UPDATED_IP_ACESSO)
            .nomeDispositivo(UPDATED_NOME_DISPOSITIVO);
        return controleAcesso;
    }

    @BeforeEach
    public void initTest() {
        controleAcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createControleAcesso() throws Exception {
        int databaseSizeBeforeCreate = controleAcessoRepository.findAll().size();
        // Create the ControleAcesso
        restControleAcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isCreated());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeCreate + 1);
        ControleAcesso testControleAcesso = controleAcessoList.get(controleAcessoList.size() - 1);
        assertThat(testControleAcesso.getDataAcesso()).isEqualTo(DEFAULT_DATA_ACESSO);
        assertThat(testControleAcesso.getIpAcesso()).isEqualTo(DEFAULT_IP_ACESSO);
        assertThat(testControleAcesso.getNomeDispositivo()).isEqualTo(DEFAULT_NOME_DISPOSITIVO);
    }

    @Test
    @Transactional
    void createControleAcessoWithExistingId() throws Exception {
        // Create the ControleAcesso with an existing ID
        controleAcesso.setId(1L);

        int databaseSizeBeforeCreate = controleAcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleAcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControleAcessos() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        // Get all the controleAcessoList
        restControleAcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleAcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAcesso").value(hasItem(DEFAULT_DATA_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].ipAcesso").value(hasItem(DEFAULT_IP_ACESSO)))
            .andExpect(jsonPath("$.[*].nomeDispositivo").value(hasItem(DEFAULT_NOME_DISPOSITIVO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleAcessosWithEagerRelationshipsIsEnabled() throws Exception {
        when(controleAcessoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleAcessoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(controleAcessoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllControleAcessosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(controleAcessoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restControleAcessoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(controleAcessoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getControleAcesso() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        // Get the controleAcesso
        restControleAcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, controleAcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleAcesso.getId().intValue()))
            .andExpect(jsonPath("$.dataAcesso").value(DEFAULT_DATA_ACESSO.toString()))
            .andExpect(jsonPath("$.ipAcesso").value(DEFAULT_IP_ACESSO))
            .andExpect(jsonPath("$.nomeDispositivo").value(DEFAULT_NOME_DISPOSITIVO));
    }

    @Test
    @Transactional
    void getNonExistingControleAcesso() throws Exception {
        // Get the controleAcesso
        restControleAcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControleAcesso() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();

        // Update the controleAcesso
        ControleAcesso updatedControleAcesso = controleAcessoRepository.findById(controleAcesso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControleAcesso are not directly saved in db
        em.detach(updatedControleAcesso);
        updatedControleAcesso.dataAcesso(UPDATED_DATA_ACESSO).ipAcesso(UPDATED_IP_ACESSO).nomeDispositivo(UPDATED_NOME_DISPOSITIVO);

        restControleAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedControleAcesso.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedControleAcesso))
            )
            .andExpect(status().isOk());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
        ControleAcesso testControleAcesso = controleAcessoList.get(controleAcessoList.size() - 1);
        assertThat(testControleAcesso.getDataAcesso()).isEqualTo(UPDATED_DATA_ACESSO);
        assertThat(testControleAcesso.getIpAcesso()).isEqualTo(UPDATED_IP_ACESSO);
        assertThat(testControleAcesso.getNomeDispositivo()).isEqualTo(UPDATED_NOME_DISPOSITIVO);
    }

    @Test
    @Transactional
    void putNonExistingControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controleAcesso.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControleAcessoWithPatch() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();

        // Update the controleAcesso using partial update
        ControleAcesso partialUpdatedControleAcesso = new ControleAcesso();
        partialUpdatedControleAcesso.setId(controleAcesso.getId());

        partialUpdatedControleAcesso.dataAcesso(UPDATED_DATA_ACESSO).ipAcesso(UPDATED_IP_ACESSO);

        restControleAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleAcesso.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleAcesso))
            )
            .andExpect(status().isOk());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
        ControleAcesso testControleAcesso = controleAcessoList.get(controleAcessoList.size() - 1);
        assertThat(testControleAcesso.getDataAcesso()).isEqualTo(UPDATED_DATA_ACESSO);
        assertThat(testControleAcesso.getIpAcesso()).isEqualTo(UPDATED_IP_ACESSO);
        assertThat(testControleAcesso.getNomeDispositivo()).isEqualTo(DEFAULT_NOME_DISPOSITIVO);
    }

    @Test
    @Transactional
    void fullUpdateControleAcessoWithPatch() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();

        // Update the controleAcesso using partial update
        ControleAcesso partialUpdatedControleAcesso = new ControleAcesso();
        partialUpdatedControleAcesso.setId(controleAcesso.getId());

        partialUpdatedControleAcesso.dataAcesso(UPDATED_DATA_ACESSO).ipAcesso(UPDATED_IP_ACESSO).nomeDispositivo(UPDATED_NOME_DISPOSITIVO);

        restControleAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControleAcesso.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControleAcesso))
            )
            .andExpect(status().isOk());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
        ControleAcesso testControleAcesso = controleAcessoList.get(controleAcessoList.size() - 1);
        assertThat(testControleAcesso.getDataAcesso()).isEqualTo(UPDATED_DATA_ACESSO);
        assertThat(testControleAcesso.getIpAcesso()).isEqualTo(UPDATED_IP_ACESSO);
        assertThat(testControleAcesso.getNomeDispositivo()).isEqualTo(UPDATED_NOME_DISPOSITIVO);
    }

    @Test
    @Transactional
    void patchNonExistingControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controleAcesso.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControleAcesso() throws Exception {
        int databaseSizeBeforeUpdate = controleAcessoRepository.findAll().size();
        controleAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControleAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controleAcesso))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ControleAcesso in the database
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControleAcesso() throws Exception {
        // Initialize the database
        controleAcessoRepository.saveAndFlush(controleAcesso);

        int databaseSizeBeforeDelete = controleAcessoRepository.findAll().size();

        // Delete the controleAcesso
        restControleAcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, controleAcesso.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleAcesso> controleAcessoList = controleAcessoRepository.findAll();
        assertThat(controleAcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
