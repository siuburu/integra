package mp.mpro.integra.domain;

import static mp.mpro.integra.domain.AreaTestSamples.*;
import static mp.mpro.integra.domain.SistemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import mp.mpro.integra.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Area.class);
        Area area1 = getAreaSample1();
        Area area2 = new Area();
        assertThat(area1).isNotEqualTo(area2);

        area2.setId(area1.getId());
        assertThat(area1).isEqualTo(area2);

        area2 = getAreaSample2();
        assertThat(area1).isNotEqualTo(area2);
    }

    @Test
    void sistemaTest() throws Exception {
        Area area = getAreaRandomSampleGenerator();
        Sistema sistemaBack = getSistemaRandomSampleGenerator();

        area.addSistema(sistemaBack);
        assertThat(area.getSistemas()).containsOnly(sistemaBack);
        assertThat(sistemaBack.getArea()).isEqualTo(area);

        area.removeSistema(sistemaBack);
        assertThat(area.getSistemas()).doesNotContain(sistemaBack);
        assertThat(sistemaBack.getArea()).isNull();

        area.sistemas(new HashSet<>(Set.of(sistemaBack)));
        assertThat(area.getSistemas()).containsOnly(sistemaBack);
        assertThat(sistemaBack.getArea()).isEqualTo(area);

        area.setSistemas(new HashSet<>());
        assertThat(area.getSistemas()).doesNotContain(sistemaBack);
        assertThat(sistemaBack.getArea()).isNull();
    }
}
