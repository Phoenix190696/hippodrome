import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;
/**
 * Класс {@code TestMain} содержит модульный тест для проверки времени выполнения метода {@link Main#main(String[])}.
 * Использует JUnit 5 для тестирования.
 *
 * <p>Основные проверки:
 * <ul>
 *     <li>Метод {@code main()} должен завершаться за 22 секунды или меньше.</li>
 *     <li>Тест временно отключён с помощью аннотации {@link Disabled}, чтобы не запускаться автоматически.</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * // Запуск основного метода приложения
 * Main.main(new String[]{});
 * }</pre>
 *
 * Аннотации:
 * <ul>
 *     <li>{@link Test} — указывает, что метод является тестом.</li>
 *     <li>{@link Disabled} — отключает выполнение теста.</li>
 * </ul>
 */
class TestMain {
    /**
     * Проверка: метод {@code main()} должен завершаться за 22 секунды.
     * <p>Тест отключён с помощью {@link Disabled}, чтобы не выполняться автоматически.</p>
     */
    @Test
    @Disabled
    public void mainFinishIn22Sec() {
        assertTimeout(
                ofSeconds(22), // Ограничение по времени: 22 секунды
                () -> {
                    // Вызов основного метода приложения
                    Main.main(new String[]{});
                }
        );
    }
}
