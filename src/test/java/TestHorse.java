import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestHorse {
    @Test
    void firstArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1.0, 1.0);
        });
    }

    @Test
    void nameNull() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1.0, 1.0);
        });
        assertEquals("Name cannot be null.", exc.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyStringOrSpace(String argument) {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, 1.0, 1.0);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void emptyStringOrSpaceEqualsExceptionMessage(String argument) {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, 1.0, 1.0);
        });
        assertEquals("Name cannot be blank.", exc.getMessage());
    }

    @Test
    void secondArgumentIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -1.0, 1.0);
        });
    }

    @Test
    void secondArgumentIsNegativeEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -1.0, 1.0);
        });
        assertEquals("Speed cannot be negative.", exc.getMessage());
    }

    @Test
    void thirdArgumentIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 1.0, -1.0);
        });
    }

    @Test
    void thirdArgumentIsNegativeEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 1.0, -1.0);
        });
        assertEquals("Distance cannot be negative.", exc.getMessage());
    }

    @Test
    void returnFirstArgumentName() {
        String expected = "Test";
        Horse horse = new Horse(expected, 1.0, 1.0);
        String actual = horse.getName();
        assertEquals(expected, actual);
    }

    @Test
    void returnSecondArgumentSpeed() {
        double expected = 1.0;
        Horse horse = new Horse("Name", expected, 1.0);
        double actual = horse.getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    void returnThirdArgumentDistance() {
        double expected = 1.0;
        Horse horse = new Horse("Name", 1.0, expected);
        double actual = horse.getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void returnThirdDistanceNullIfObjectTwoParametrs() {
        double expected = 0.0;
        Horse horse = new Horse("Name", 1.0);
        double actual = horse.getDistance();
        assertEquals(expected, actual);
    }

    @Test
    void getRandomDoubleMock() {
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {

            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            Horse horse = new Horse("Name", 10.0, 0.0);
            horse.move();
            mocked.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void distanceUsingFormula(double randomValue) {
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {

            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            double initialSpeed = 10.0;
            double initialDistance = 5.0;

            Horse horse = new Horse("Name", initialSpeed, initialDistance);
            horse.move();
            double expected = initialDistance + initialSpeed * randomValue;
            assertEquals(expected, horse.getDistance());
        }

    }

}
