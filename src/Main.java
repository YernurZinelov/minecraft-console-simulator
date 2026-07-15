import exceptions.EntityDeadException;
import exceptions.ZeroDamageException;
import model.base.Entity;
import model.base.Mob;
import model.entities.*;
import model.items.*;
import service.AdventureEngine;
import service.CombatSystem;
import exceptions.InvalidDataException;
import service.EntityManager;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
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
            Player player3 = new Player("Technoblade", 20.0, 2.0, 1.0);
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
            EntityManager entityManager = new EntityManager();

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
            System.out.println();

            // === 6. Organizing duels ===
            combatSystem.oneVSOne(mob1, mob6);
            System.out.println();
            combatSystem.oneVSOne(mob2, mob3);
            System.out.println();

            entityManager.printList();
            System.out.println();

            entityManager.removeEntity("Zombie");
            entityManager.removeEntity("Wither Skeleton");
            System.out.println();

            entityManager.printList();
            System.out.println();

            System.out.println("=== Entities ranked by [HP]: ===");
            System.out.println();

            int i = 1;
            for (Entity entity : entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getHealthPoints)).reversed()) {
                System.out.println(i + ". " + entity);
                i++;
            }

            System.out.println();
            System.out.println("=== Entities sorted by [Damage Points]: ===");
            System.out.println();

            int j = 1;
            for (Entity entity : entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getDamagePoints))) {
                System.out.println(j + ". " + entity);
                j++;
            }

            System.out.println();
            System.out.println("=== Entities sorted by [Attack Speed]: ===");
            System.out.println();

            int n = 1;
            for (Entity entity : entityManager.getEntitiesSorted(Comparator.comparingDouble(Entity::getAttackSpeed))) {
                System.out.println(n + ". " + entity);
                n++;
            }

            System.out.println();
            System.out.println("=== Entities filtered by [Special Effects]: ===");
            System.out.println();

            int k = 1;
            for (Entity entity : entityManager.getEntitiesFilteredBySpecialEffect()) {
                System.out.println(k + ". " + entity);
                k++;
            }

            System.out.println();

            AdventureEngine adventureEngine = new AdventureEngine(entityManager, combatSystem);
            adventureEngine.beatingMinecraft(player3);

        } catch (InvalidDataException e) {
            System.err.println("Error of initializing in a game! Wrong parameters: " + e.getMessage());
        } catch (EntityDeadException e) {
            System.out.println("❌ Ошибка боя: " + e.getMessage());
        } catch (ZeroDamageException e) {
            System.out.println("⚠️ Невозможно начать битву: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка системы: " + e.getMessage());
        }
    }
}