package mp.mpro.integra.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mp.mpro.integra.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SistemaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SistemaDTO.class);
        SistemaDTO sistemaDTO1 = new SistemaDTO();
        sistemaDTO1.setId(1L);
        SistemaDTO sistemaDTO2 = new SistemaDTO();
        assertThat(sistemaDTO1).isNotEqualTo(sistemaDTO2);
        sistemaDTO2.setId(sistemaDTO1.getId());
        assertThat(sistemaDTO1).isEqualTo(sistemaDTO2);
        sistemaDTO2.setId(2L);
        assertThat(sistemaDTO1).isNotEqualTo(sistemaDTO2);
        sistemaDTO1.setId(null);
        assertThat(sistemaDTO1).isNotEqualTo(sistemaDTO2);
    }
}
