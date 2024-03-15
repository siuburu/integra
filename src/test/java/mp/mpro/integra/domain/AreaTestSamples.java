package mp.mpro.integra.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AreaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Area getAreaSample1() {
        return new Area().id(1L).nome("nome1");
    }

    public static Area getAreaSample2() {
        return new Area().id(2L).nome("nome2");
    }

    public static Area getAreaRandomSampleGenerator() {
        return new Area().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
