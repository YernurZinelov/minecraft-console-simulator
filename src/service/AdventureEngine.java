package service;

import enums.LocationType;
import model.base.Mob;
import model.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdventureEngine {

    private final EntityManager entityManager;
    private final CombatSystem combatSystem;

    public AdventureEngine(EntityManager entityManager, CombatSystem combatSystem) {
        this.entityManager = entityManager;
        this.combatSystem = combatSystem;
    }

    public void beatingMinecraft(Player player) {
        System.out.println("=== PLAYER: " + player.getName() + " IS ATTEMPTING TO BEAT MINECRAFT ===");
        System.out.println();

        List<LocationType> locationsList = new ArrayList<>();

        locationsList.add(LocationType.PLAIN);
        locationsList.add(LocationType.CAVE);
        locationsList.add(LocationType.NETHER);
        locationsList.add(LocationType.END);

        for (LocationType l : locationsList) {
            if (!hasBeatenMobsInTheLocation(player, l)) return;
        }

        System.out.println("=== CONGRATULATIONS!!! ===");
        System.out.println("Player: " + player.getName() + " has beaten Minecraft!!!");
    }

    private boolean hasBeatenMobsInTheLocation(Player player, LocationType location) {
        List<Mob> mobsInThisLocation = entityManager.getMobsByLocation(location);

        System.out.println("\n==================================================");
        System.out.println(" 🌳 CURRENT DIMENSION: " + location.toString().toUpperCase() + " 🌳");
        System.out.println("==================================================");

        Random random = new Random();
        boolean hadFights = false;

        if (!mobsInThisLocation.isEmpty()) {
            for (Mob mob : mobsInThisLocation) {

                switch (mob.getBehavior()) {
                    case HOSTILE:
                        System.out.println("\n⚔️ 🔴 AGGRESSIVE MOB SPOTTED! [" + mob.getName() + "] attacks!");
                        if (!conductFight(player, mob)) return false;
                        hadFights = true;
                        break;

                    case NEUTRAL:
                        System.out.println("\n👁️  🟡 NEUTRAL MOB: [" + mob.getName() + "] is watching you...");
                        boolean accidentallyHit = random.nextBoolean();
                        if (accidentallyHit) {
                            System.out.println("💥 OOPS! You accidentally hit it!");
                            if (!conductFight(player, mob)) return false;
                            hadFights = true;
                        } else {
                            System.out.println("🍃 Safe! You carefully walked past " + mob.getName() + ".");
                        }
                        break;

                    case PASSIVE:
                        System.out.println("\n🐑 🟢 " + mob.getName() + " is just chilling here. You walk past.");
                        break;
                }
            }
        }

        System.out.println("\n--------------------------------------------------");
        if (hadFights) {
            System.out.println("🏆 REGION CLEAR: " + location + " is now safe!");
            System.out.println("📊 Final Stats: " + player);
            player.setHealthPoints(player.getMaxHealthPoints());
            System.out.println("🍖 Munching on food... HP fully regenerated to " + player.getHealthPoints() + "!");
        } else {
            System.out.println("🕊️  PEACEFUL WALK: No blood spilled in " + location + ".");
        }
        System.out.println("🚀 Advancing to the next dimension...");
        System.out.println("--------------------------------------------------");
        return true;
    }

    private boolean conductFight(Player player, Mob mob) {
        combatSystem.oneVSOne(player, mob);

        if (player.getHealthPoints() <= 0) {
            System.out.println("Player: " + player.getName() + " has been defeated by " + mob.getName());
            System.out.println("His journey ends there...");
            return false;
        }
        System.out.println("⭐ Success! " + mob.getName() + " is no more. Moving forward...");
        return true;
    }
}
