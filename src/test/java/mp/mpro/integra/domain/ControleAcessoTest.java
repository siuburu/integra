package mp.mpro.integra.domain;

import static mp.mpro.integra.domain.ControleAcessoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mp.mpro.integra.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControleAcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleAcesso.class);
        ControleAcesso controleAcesso1 = getControleAcessoSample1();
        ControleAcesso controleAcesso2 = new ControleAcesso();
        assertThat(controleAcesso1).isNotEqualTo(controleAcesso2);

        controleAcesso2.setId(controleAcesso1.getId());
        assertThat(controleAcesso1).isEqualTo(controleAcesso2);

        controleAcesso2 = getControleAcessoSample2();
        assertThat(controleAcesso1).isNotEqualTo(controleAcesso2);
    }
}
