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
        Log.println("=== PLAYER: " + player.getName() + " IS ATTEMPTING TO BEAT MINECRAFT ===");

        List<LocationType> locationsList = new ArrayList<>();

        locationsList.add(LocationType.PLAIN);
        locationsList.add(LocationType.CAVE);
        locationsList.add(LocationType.NETHER);
        locationsList.add(LocationType.END);

        for (LocationType l : locationsList) {
            if (!hasBeatenMobsInTheLocation(player, l)) return;
        }

        Log.println("=== CONGRATULATIONS!!! ===");
        Log.println("Player: " + player.getName() + " has beaten Minecraft!!!");
    }

    private boolean hasBeatenMobsInTheLocation(Player player, LocationType location) {
        List<Mob> mobsInThisLocation = entityManager.getMobsByLocation(location);

        Log.println("\n==================================================");
        Log.println(" 🌳 CURRENT DIMENSION: " + location.toString().toUpperCase() + " 🌳");
        Log.println("==================================================");

        Random random = new Random();
        boolean hadFights = false;

        if (!mobsInThisLocation.isEmpty()) {
            for (Mob mob : mobsInThisLocation) {

                switch (mob.getBehavior()) {
                    case HOSTILE:
                        Log.println("\n⚔️ 🔴 AGGRESSIVE MOB SPOTTED! [" + mob.getName() + "] attacks!");
                        if (!conductFight(player, mob)) return false;
                        hadFights = true;
                        break;

                    case NEUTRAL:
                        Log.println("\n👁️  🟡 NEUTRAL MOB: [" + mob.getName() + "] is watching you...");
                        boolean accidentallyHit = random.nextBoolean();
                        if (accidentallyHit) {
                            Log.println("💥 OOPS! You accidentally hit it!");
                            if (!conductFight(player, mob)) return false;
                            hadFights = true;
                        } else {
                            Log.println("🍃 Safe! You carefully walked past " + mob.getName() + ".");
                        }
                        break;

                    case PASSIVE:
                        Log.println("\n🐑 🟢 " + mob.getName() + " is just chilling here. You walk past.");
                        break;
                }
            }
        }

        Log.println("\n--------------------------------------------------");
        if (hadFights) {
            Log.println("🏆 REGION CLEAR: " + location + " is now safe!");
            Log.println("📊 Final Stats: " + player);
            player.setHealthPoints(player.getMaxHealthPoints());
            Log.println("🍖 Munching on food... HP fully regenerated to " + player.getHealthPoints() + "!");
        } else {
            Log.println("🕊️  PEACEFUL WALK: No blood spilled in " + location + ".");
        }
        Log.println("🚀 Advancing to the next dimension...");
        Log.println("--------------------------------------------------");
        return true;
    }

    private boolean conductFight(Player player, Mob mob) {
        combatSystem.oneVSOne(player, mob);

        if (player.getHealthPoints() <= 0) {
            Log.println("Player: " + player.getName() + " has been defeated by " + mob.getName());
            Log.println("His journey ends there...");
            return false;
        }
        Log.println("⭐ Success! " + mob.getName() + " is no more. Moving forward...");
        return true;
    }
}
