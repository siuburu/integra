package mp.mpro.integra.domain;

import static mp.mpro.integra.domain.AreaTestSamples.*;
import static mp.mpro.integra.domain.SistemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mp.mpro.integra.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SistemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sistema.class);
        Sistema sistema1 = getSistemaSample1();
        Sistema sistema2 = new Sistema();
        assertThat(sistema1).isNotEqualTo(sistema2);

        sistema2.setId(sistema1.getId());
        assertThat(sistema1).isEqualTo(sistema2);

        sistema2 = getSistemaSample2();
        assertThat(sistema1).isNotEqualTo(sistema2);
    }

    @Test
    void areaTest() throws Exception {
        Sistema sistema = getSistemaRandomSampleGenerator();
        Area areaBack = getAreaRandomSampleGenerator();

        sistema.setArea(areaBack);
        assertThat(sistema.getArea()).isEqualTo(areaBack);

        sistema.area(null);
        assertThat(sistema.getArea()).isNull();
    }
}
