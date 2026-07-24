package test.model.base;

import exceptions.InvalidDataException;
import model.entities.WitherSkeleton;
import model.entities.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    private Zombie zombie;

    @BeforeEach
    void setUp() {
        zombie = new Zombie("Zombie", 20.0, 3.0, 1.0);
    }

    @Test
    void constructor_withNullName_shouldThrowNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
           new Zombie(null, 20.0, 3.0, 1.0);
        });
        assertEquals("Entity name cannot be null!", exception.getMessage());
    }

    @Test
    void constructor_withNegativeHealthPoints_shouldThrowInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> {
            new Zombie("Zombie", -5.0, 4.0, 1.0);
        });
    }

    @Test
    void constructor_withNegativeDamage_shouldThrowInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> {
            new Zombie("Zombie", 20.0, -3.0, 1.0);
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0})
    void constructor_withZeroOrNegativeAttackSpeed_shouldThrowInvalidDataException(double invalidAttackSpeed) {
        assertThrows(InvalidDataException.class, () -> {
            new Zombie("Zombie", 20.0, 3.0, invalidAttackSpeed);
        });
    }

    @Test
    void isAlive_withPositiveHealthPoints_shouldReturnTrue() {
        assertTrue(zombie.isAlive());
    }

    @Test
    void hasSpecialEffect_withSpecialEffectMob_shouldReturnTrue() {
        WitherSkeleton witherSkeleton = new WitherSkeleton("Wither Skeleton", 20.0, 4.5, 1.0);
        assertTrue(witherSkeleton.hasSpecialEffect());
    }
}
