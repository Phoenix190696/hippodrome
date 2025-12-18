import org.junit.jupiter.api.Test;
// Импорт аннотации @Test для написания тестов.

import org.junit.jupiter.params.ParameterizedTest;
// Импорт аннотации для параметризованных тестов.

import org.junit.jupiter.params.provider.ValueSource;
// Импорт источника значений для параметризованных тестов.

import org.mockito.MockedStatic;
// Импорт класса для мокирования статических методов.

import org.mockito.Mockito;
// Импорт библиотеки Mockito.

import java.util.*;
// Импорт стандартных коллекций (не используется явно, но подключено).

import static org.junit.jupiter.api.Assertions.assertEquals;
// Импорт метода assertEquals для проверки равенства.

import static org.junit.jupiter.api.Assertions.assertThrows;
// Импорт метода assertThrows для проверки исключений.
/**
 * Класс {@code TestHorse} содержит модульные тесты для проверки корректности работы класса {@link Horse}.
 * Использует JUnit 5 и Mockito для тестирования поведения конструктора, методов доступа и логики движения.
 *
 * <p>Основные проверки:
 * <ul>
 *     <li>Валидация аргументов конструктора (имя, скорость, дистанция).</li>
 *     <li>Корректность сообщений исключений при ошибках.</li>
 *     <li>Возврат значений через геттеры.</li>
 *     <li>Поведение метода {@code move()} с использованием мокирования случайных значений.</li>
 *     <li>Проверка формулы изменения дистанции.</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * Horse horse = new Horse("Test", 10.0, 5.0);
 * horse.move();
 * }</pre>
 */
class TestHorse {
    /** Проверка: если имя лошади {@code null}, должно выбрасываться исключение. */
    @Test
    void firstArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1.0, 1.0);
        });
    }
    /** Проверка: сообщение исключения при {@code null} имени должно быть "Name cannot be null." */
    @Test
    void nameNull() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1.0, 1.0);
        });
        assertEquals("Name cannot be null.", exc.getMessage());
    }
    /** Проверка: пустая строка или пробел в имени должны вызывать исключение. */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyStringOrSpace(String argument) {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, 1.0, 1.0);
        });
    }
    /** Проверка: сообщение исключения при пустом имени должно быть "Name cannot be blank." */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyStringOrSpaceEqualsExceptionMessage(String argument) {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, 1.0, 1.0);
        });
        assertEquals("Name cannot be blank.", exc.getMessage());
    }
    /** Проверка: отрицательная скорость должна вызывать исключение. */
    @Test
    void secondArgumentIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -1.0, 1.0);
        });
    }
    /** Проверка: сообщение исключения при отрицательной скорости должно быть "Speed cannot be negative." */
    @Test
    void secondArgumentIsNegativeEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -1.0, 1.0);
        });
        assertEquals("Speed cannot be negative.", exc.getMessage());
    }
    /** Проверка: отрицательная дистанция должна вызывать исключение. */
    @Test
    void thirdArgumentIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 1.0, -1.0);
        });
    }
    /** Проверка: сообщение исключения при отрицательной дистанции должно быть "Distance cannot be negative." */
    @Test
    void thirdArgumentIsNegativeEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 1.0, -1.0);
        });
        assertEquals("Distance cannot be negative.", exc.getMessage());
    }
    /** Проверка: метод {@code getName()} возвращает имя, переданное в конструктор. */
    @Test
    void returnFirstArgumentName() {
        String expected = "Test";
        Horse horse = new Horse(expected, 1.0, 1.0);
        String actual = horse.getName();
        assertEquals(expected, actual);
    }
    /** Проверка: метод {@code getSpeed()} возвращает скорость, переданную в конструктор. */
    @Test
    void returnSecondArgumentSpeed() {
        double expected = 1.0;
        Horse horse = new Horse("Name", expected, 1.0);
        double actual = horse.getSpeed();
        assertEquals(expected, actual);
    }
    /** Проверка: метод {@code getDistance()} возвращает дистанцию, переданную в конструктор. */
    @Test
    void returnThirdArgumentDistance() {
        double expected = 1.0;
        Horse horse = new Horse("Name", 1.0, expected);
        double actual = horse.getDistance();
        assertEquals(expected, actual);
    }
    /** Проверка: если конструктор вызван без дистанции, {@code getDistance()} возвращает 0.0. */
    @Test
    void returnThirdDistanceNullIfObjectTwoParametrs() {
        double expected = 0.0;
        Horse horse = new Horse("Name", 1.0);
        double actual = horse.getDistance();
        assertEquals(expected, actual);
    }
    /** Проверка: метод {@code move()} вызывает статический метод {@code getRandomDouble()}. */
    @Test
    void getRandomDoubleMock() {
        // Создаём мок для статического метода Horse.getRandomDouble()
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {
            // Настраиваем мок: при вызове getRandomDouble(0.2, 0.9) возвращать 0.5
            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            // Создаём объект Horse с именем "Name", скоростью 10.0 и дистанцией 0.0
            Horse horse = new Horse("Name", 10.0, 0.0);

            // Вызываем метод move(), который внутри должен обратиться к getRandomDouble()
            horse.move();

            // Проверяем, что метод getRandomDouble(0.2, 0.9) действительно был вызван
            mocked.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    /** Проверка: дистанция изменяется по формуле {@code distance = initialDistance + speed * randomValue}. */
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void distanceUsingFormula(double randomValue) {
        // Создаём мок для статического метода Horse.getRandomDouble()
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {
            // Настраиваем мок: при вызове getRandomDouble(0.2, 0.9) возвращать значение randomValue
            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            // Задаём начальные значения скорости и дистанции
            double initialSpeed = 10.0;
            double initialDistance = 5.0;

            // Создаём объект Horse с указанной скоростью и дистанцией
            Horse horse = new Horse("Name", initialSpeed, initialDistance);

            // Вызываем метод move(), который должен изменить дистанцию по формуле
            horse.move();

            // Вычисляем ожидаемое значение дистанции: начальная + скорость * случайное число
            double expected = initialDistance + initialSpeed * randomValue;

            // Проверяем, что фактическая дистанция совпадает с ожидаемой
            assertEquals(expected, horse.getDistance());
        }
    }
}

