import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TestHippodrome {
    @Test
    void argumentConstructorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    void argumentConstructorIsNullEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exc.getMessage());
    }

    @Test
    void listIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Horse> horseList = new ArrayList<>();
            new Hippodrome(horseList);
        });
    }

    @Test
    void listIsEmptyEqualsExceptionMessage() {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
            List<Horse> horseList = new ArrayList<>();
            new Hippodrome(horseList);
        });
        assertEquals("Horses cannot be empty.", exc.getMessage());
    }

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

    @Test
    void getWinnerIsGreatestDistance() {
        Horse horse1 = new Horse("Test1", 1.0, 2.0);
        Horse horse2 = new Horse("Test2", 1.5, 5.0);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2));
        Horse winner = hippodrome.getWinner();
        assertEquals(horse2, winner);
    }

}
