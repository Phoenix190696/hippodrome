import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Класс {@code TestHippodrome} содержит модульные тесты для проверки корректности работы класса {@link Hippodrome}.
 * Использует JUnit 5 и Mockito для тестирования поведения конструктора, методов доступа и бизнес‑логики.
 *
 * <p>Основные проверки:
 * <ul>
 *     <li>Валидация аргументов конструктора (список лошадей не должен быть {@code null} или пустым).</li>
 *     <li>Корректность сообщений исключений при ошибках.</li>
 *     <li>Возврат списка лошадей через метод {@code getHorses()}.</li>
 *     <li>Вызов метода {@code move()} у всех лошадей.</li>
 *     <li>Определение победителя через метод {@code getWinner()} (лошадь с наибольшей дистанцией).</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * List<Horse> horses = List.of(new Horse("Test1", 1.0), new Horse("Test2", 2.0));
 * Hippodrome hippodrome = new Hippodrome(horses);
 * Horse winner = hippodrome.getWinner();
 * }</pre>
 *
 * Аннотации:
 * <ul>
 *     <li>{@link Test} — указывает, что метод является тестом.</li>
 * </ul>
 */
class TestHippodrome {

    /** Проверка: если список лошадей {@code null}, должно выбрасываться исключение. */
    @Test
    void argumentConstructorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    /** Проверка: сообщение исключения при {@code null} списке должно быть "Horses cannot be null." */
    @Test
    void argumentConstructorIsNullEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exc.getMessage());
    }

    /** Проверка: если список лошадей пустой, должно выбрасываться исключение. */
    @Test
    void listIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Horse> horseList = new ArrayList<>();
            new Hippodrome(horseList);
        });
    }

    /** Проверка: сообщение исключения при пустом списке должно быть "Horses cannot be empty." */
    @Test
    void listIsEmptyEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            List<Horse> horseList = new ArrayList<>();
            new Hippodrome(horseList);
        });
        assertEquals("Horses cannot be empty.", exc.getMessage());
    }

    /** Проверка: метод {@code getHorses()} возвращает список лошадей, переданный в конструктор. */
    @Test
    void getHorsesReturnList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            horses.add(new Horse("Name" + i, 1.0));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> actual = hippodrome.getHorses();
        assertEquals(horses.size(), actual.size());
        assertEquals(horses, actual);
    }

    /** Проверка: метод {@code move()} вызывает метод {@code move()} у каждой лошади. */
    @Test
    void moveInAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }
    }

    /** Проверка: метод {@code getWinner()} возвращает лошадь с наибольшей дистанцией. */
    @Test
    void getWinnerIsGreatestDistance() {
        Horse horse1 = new Horse("Test1", 1.0, 2.0);
        Horse horse2 = new Horse("Test2", 1.5, 5.0);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2));
        Horse winner = hippodrome.getWinner();
        assertEquals(horse2, winner);
    }
}

