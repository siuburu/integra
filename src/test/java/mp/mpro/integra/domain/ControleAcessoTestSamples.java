package mp.mpro.integra.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ControleAcessoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ControleAcesso getControleAcessoSample1() {
        return new ControleAcesso().id(1L).ipAcesso("ipAcesso1").nomeDispositivo("nomeDispositivo1");
    }

    public static ControleAcesso getControleAcessoSample2() {
        return new ControleAcesso().id(2L).ipAcesso("ipAcesso2").nomeDispositivo("nomeDispositivo2");
    }

    public static ControleAcesso getControleAcessoRandomSampleGenerator() {
        return new ControleAcesso()
            .id(longCount.incrementAndGet())
            .ipAcesso(UUID.randomUUID().toString())
            .nomeDispositivo(UUID.randomUUID().toString());
    }
}
