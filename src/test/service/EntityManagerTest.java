package test.service;

import enums.LocationType;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import model.base.Entity;
import model.base.Mob;
import model.entities.Enderman;
import model.entities.Player;
import model.entities.WitherSkeleton;
import model.entities.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.EntityManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {

    private EntityManager<Entity> entityManager;

    @BeforeEach
    void setUp() {
        entityManager = new EntityManager<>();
    }

    @Test
    void addEntity_withDistinctNamePlayer_shouldAddThisPlayer() {
        Player player = new Player("Racer_Pro", 20.0, 2.0, 0.5);
        entityManager.addEntity(player);

        Optional<Entity> found = entityManager.findByName(player.getName());

        assertTrue(found.isPresent());

        assertEquals(player, found.get());
    }

    @Test
    void addEntity_withDuplicateNamePlayer_shouldThrowDuplicateEntityException() {
        Player player1 = new Player("Racer_Pro", 20.0, 2.0, 0.5);
        Player player2 = new Player("Racer_Pro", 20.0, 3.0, 0.75);

        entityManager.addEntity(player1);

        assertThrows(DuplicateEntityException.class, () -> {
           entityManager.addEntity(player2);
        });
    }

    @Test
    void removeEntity_withExistingName_shouldRemoveThisPlayer() {
        Player player = new Player("Racer_Pro", 20.0, 2.0, 0.5);
        entityManager.addEntity(player);

        entityManager.removeEntity(player.getName());

        Optional<Entity> found = entityManager.findByName(player.getName());

        assertTrue(found.isEmpty());
    }

    @Test
    void removeEntity_withNonExistingName_shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            entityManager.removeEntity("Dream");
        });
    }

    @Test
    void findByName_withExistingName_shouldFindThisPlayer() {
        Player player = new Player("Racer_Pro", 20.0, 2.0, 0.5);
        entityManager.addEntity(player);

        Optional<Entity> found = entityManager.findByName(player.getName());

        assertTrue(found.isPresent());
    }

    @Test
    void findByName_withNonExistingName_shouldNotFindThisPlayer() {
        Optional<Entity> found = entityManager.findByName("Dream");

        assertTrue(found.isEmpty());
    }

    @Test
    void getEntitiesSorted_withUnsortedList_shouldReturnSortedListByName() {
        Player player1 = new Player("GeorgeNotFound", 20.0, 2.0, 1.0);
        Player player2 = new Player("Dream", 20.0, 2.0, 1.0);
        Player player3 = new Player("Sapnap", 20.0, 2.0, 1.0);
        Player player4 = new Player("AwesamDude", 20.0, 2.0, 1.0);

        entityManager.addEntity(player1);
        entityManager.addEntity(player2);
        entityManager.addEntity(player3);
        entityManager.addEntity(player4);

        List<Entity> sorted = entityManager.getEntitiesSorted(Comparator.comparing(Entity::getName));

        assertEquals(List.of(player4, player2, player1, player3), sorted);
    }

    @Test
    void getEntitiesFilteredBySpecialEffect_withUnfilteredListOfEntities_shouldReturnFilteredListBySpecialEffect() {
        WitherSkeleton e1 = new WitherSkeleton("Wither Skeleton #1", 20.0, 4.5, 1.0);
        Player e2 = new Player("Dream", 20.0, 2.0, 1.0);
        Zombie e3 = new Zombie("Zombie", 20.0, 3.0, 1.0);
        WitherSkeleton e4 = new WitherSkeleton("Wither Skeleton #2", 20.0, 4.5, 1.0);

        entityManager.addEntity(e1);
        entityManager.addEntity(e2);
        entityManager.addEntity(e3);
        entityManager.addEntity(e4);

        List<Entity> filtered = entityManager.getEntitiesFilteredBySpecialEffect();

        assertEquals(List.of(e1, e4), filtered);
    }

    @Test
    void getMobsByLocation_withMobsListOnly_shouldReturnMobsInCave() {
        Zombie mob1 = new Zombie("Zombie", 20.0, 3.0, 1.0);
        Enderman mob2 = new Enderman("Enderman", 40.0, 7.0, 1.0);
        Zombie mob3 = new Zombie("Buffed Zombie", 24.0, 4.0, 1.0);
        WitherSkeleton mob4 = new WitherSkeleton("Wither Skeleton", 20.0, 4.5, 1.0);

        entityManager.addEntity(mob1);
        entityManager.addEntity(mob2);
        entityManager.addEntity(mob3);
        entityManager.addEntity(mob4);

        List<Mob> mobsInCave = entityManager.getMobsByLocation(LocationType.CAVE);

        assertEquals(List.of(mob1, mob3), mobsInCave);
    }

    @Test
    void getMobsByLocation_withNotOnlyMobsList_shouldReturnMobsInCave() {
        Zombie e1 = new Zombie("Zombie", 20.0, 3.0, 1.0);
        Enderman e2 = new Enderman("Enderman", 40.0, 7.0, 1.0);
        Zombie e3 = new Zombie("Buffed Zombie", 24.0, 4.0, 1.0);
        WitherSkeleton e4 = new WitherSkeleton("Wither Skeleton", 20.0, 4.5, 1.0);
        Player e5 = new Player("Dream", 20.0, 2.0, 1.0);

        entityManager.addEntity(e1);
        entityManager.addEntity(e2);
        entityManager.addEntity(e3);
        entityManager.addEntity(e4);
        entityManager.addEntity(e5);

        List<Mob> mobsInCave = entityManager.getMobsByLocation(LocationType.CAVE);

        assertEquals(List.of(e1, e3), mobsInCave);
    }

}
