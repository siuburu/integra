package mp.mpro.integra.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SistemaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sistema getSistemaSample1() {
        return new Sistema().id(1L).group("group1").nome("nome1").descricao("descricao1").homeUrl("homeUrl1").logoUrl("logoUrl1");
    }

    public static Sistema getSistemaSample2() {
        return new Sistema().id(2L).group("group2").nome("nome2").descricao("descricao2").homeUrl("homeUrl2").logoUrl("logoUrl2");
    }

    public static Sistema getSistemaRandomSampleGenerator() {
        return new Sistema()
            .id(longCount.incrementAndGet())
            .group(UUID.randomUUID().toString())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .homeUrl(UUID.randomUUID().toString())
            .logoUrl(UUID.randomUUID().toString());
    }
}
