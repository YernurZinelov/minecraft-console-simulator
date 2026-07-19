import exceptions.*;
import model.base.Entity;
import model.base.Mob;
import model.entities.*;
import model.items.*;
import service.AdventureEngine;
import service.CombatSystem;
import service.EntityManager;
import service.Log;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager<Entity> entityManager = null;
        try {
            // === 1. Creating Mobs ===
            Mob mob1 = new Zombie("Zombie", 20.0, 3.0, 1.0);
            Mob mob2 = new Enderman("Enderman", 40.0, 7.0, 1.0);
            Mob mob3 = new WitherSkeleton("Wither Skeleton", 20.0, 4.5, 1.0);
            Mob mob4 = new Cow("Cow", 10.0, 0.0, 0.5);
            Mob mob5 = new WitherSkeleton("Injured Wither Skeleton", 15.5, 4.5, 1.0);
            Mob mob6 = new Zombie("Buffed Zombie", 25.0, 3.5, 1.0);
            Mob mob7 = new Vindicator("Vindicator", 24.0, 13.0, 2.0);

            // === 2. Creating players ===
            Player player1 = new Player("Steve", 20.0, 2.0, 1.0);
            Player player2 = new Player("Alex", 20.0, 2.0, 1.0);
            Player player3 = new Player("Technoblade", 20.0, 2.0, 0.5);
            Player player4 = new Player("Dream", 20.0, 2.0, 1.0);
            Player player5 = new Player("MrBeast", 20.0, 2.0, 1.0);

            // === 3. Creating weapons and armors ===
            Weapon weapon1 = new WoodenSword();
            Weapon weapon2 = new StoneSword();
            Weapon weapon3 = new IronSword();

            Armor armor1 = new LeatherArmor();
            Armor armor2 = new IronArmor();

            // === 4. Giving players weapon and armor ===
            player1.setWeapon(weapon1);
            player1.setArmor(armor2);

            player2.setWeapon(weapon2);
            player2.setArmor(armor1);

            player3.setWeapon(weapon3);
            player3.setArmor(armor2);

            player4.setWeapon(weapon3);
            player5.setArmor(armor2);

            // === 5. Initializing a combat system ===
            entityManager = new EntityManager<>();

            CombatSystem combatSystem = new CombatSystem();

            entityManager.addEntity(mob1);
            entityManager.addEntity(mob2);
            entityManager.addEntity(mob3);
            entityManager.addEntity(mob4);
            entityManager.addEntity(mob5);
            entityManager.addEntity(mob6);
            entityManager.addEntity(mob7);

            entityManager.addEntity(player1);
            entityManager.addEntity(player2);
            entityManager.addEntity(player3);
            entityManager.addEntity(player4);
            entityManager.addEntity(player5);

            entityManager.printList();
            Log.println("");

            // === 6. Organizing duels ===
            combatSystem.oneVSOne(mob1, mob6);
            Log.println("");
            combatSystem.oneVSOne(mob2, mob3);
            Log.println("");

            entityManager.printList();
            Log.println("");

            entityManager.removeEntity("Zombie");
            entityManager.removeEntity("Wither Skeleton");
            Log.println("");

            entityManager.printList();
            Log.println("");

            Log.println("=== Entities ranked by [HP]: ===");
            Log.println("");
            printNumberedList(entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getHealthPoints)).reversed());
            Log.println("");

            Log.println("=== Entities sorted by [Damage Points]: ===");
            Log.println("");
            printNumberedList(entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getDamagePoints)));
            Log.println("");

            Log.println("=== Entities sorted by [Attack Speed]: ===");
            Log.println("");
            printNumberedList(entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getAttackSpeed)));
            Log.println("");

            Log.println("=== Entities filtered by [Special Effects]: ===");
            Log.println("");
            printNumberedList(entityManager.getEntitiesFilteredBySpecialEffect());
            Log.println("");

            AdventureEngine adventureEngine = new AdventureEngine(entityManager, combatSystem);
            adventureEngine.beatingMinecraft(player3);

            Log.println("\n--- Waiting for each effect ticks' ending ... ---");
            Thread.sleep(10000);

        } catch (InvalidDataException e) {
            System.err.println("Error of initializing in a game! Wrong parameters: " + e.getMessage());
        } catch (EntityDeadException e) {
            Log.println("❌ Battle error: " + e.getMessage());
        } catch (ZeroDamageException e) {
            Log.println("⚠️ Impossible to start the battle: " + e.getMessage());
        } catch (DuplicateEntityException e) {
            Log.println("⚠️ Duplication error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            Log.println("⚠️ Entity not found: " + e.getMessage());
        } catch (Exception e) {
            Log.println("An unforeseen system error appeared: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                Log.println("\n[System] Shutting down all remaining entity schedulers...");
                entityManager.getAllEntities().forEach(Entity::shutDownScheduler);
            }
        }
    }

    private static void printNumberedList(List<? extends Entity> list) {
        int index = 1;
        for (Entity entity : list) {
            Log.println(index + ". " + entity);
            index++;
        }
    }
}