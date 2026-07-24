package test.model.entities;

import model.entities.Player;
import model.items.IronArmor;
import model.items.IronSword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Racer_Pro", 20.0, 2.0, 0.5);
    }

    @Test
    void takeDamage_withArmorEquipped_shouldReduceIncomingDamage() {
        player.setArmor(new IronArmor()); // protection points = 1.5

        player.takeDamage(5.0, false);

        // 20.0 - (5.0 - 1.5) = 16.5
        assertEquals(16.5, player.getHealthPoints(), 0.001);
    }

    @Test
    void takeDamage_withArmorEquippedButIgnored_shouldNotReduceIncomingDamage() {
        player.setArmor(new IronArmor());

        player.takeDamage(5.0, true);

        // 20.0 - (5.0 - 0.0) = 15.0
        assertEquals(15.0, player.getHealthPoints(), 0.001);
    }

    @Test
    void takeDamage_withNoArmorEquippedAndNotIgnored_shouldNotReduceIncomingDamage() {
        player.takeDamage(5.0, false);

        // 20.0 - 5.0 = 15.0
        assertEquals(15.0, player.getHealthPoints(), 0.001);
    }

    @Test
    void takeDamage_withNoArmorAndIgnored_shouldNotReduceIncomingDamage() {
        player.takeDamage(5.0, true);

        // 20.0 - 5.0 = 15.0
        assertEquals(15.0, player.getHealthPoints(), 0.001);
    }

    @Test
    void getDamagePoints_withWeaponEquipped_shouldIncludeWeaponDamage() {
        player.setWeapon(new IronSword());

        // 2.0 + 3.0 = 5.0
        assertEquals(5.0, player.getDamagePoints(), 0.001);
    }

    @Test
    void getDamagePoints_withoutWeapon_shouldReturnBaseDamageOnly() {
        // 2.0 + 0.0 = 2.0
        assertEquals(2.0, player.getDamagePoints(), 0.001);
    }

    @Test
    void getAttackSpeed_withWeaponEquipped_shouldIncludeWeaponSpeed() {
        player.setWeapon(new IronSword());

        // 0.5 + (-0.1) = 0.4
        assertEquals(0.4, player.getAttackSpeed(), 0.001);
    }

    @Test
    void getAttackSpeed_withoutWeapon_shouldReturnBaseAttackSpeedOnly() {
        // 0.5 + (0.0) = 0.5
        assertEquals(0.5, player.getAttackSpeed(), 0.001);
    }
}
