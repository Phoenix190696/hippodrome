import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class TestMain {
    @Test
    @Disabled
    public void mainFinishIn22Sec() {
        assertTimeout(
                ofSeconds(22),
                () -> {
                    // пауза в одну секунду
                    Main.main(new String[]{});
                }
        );
    }
}
