package service;

import model.base.Entity;

import java.util.concurrent.atomic.AtomicBoolean;

public class AttackTask implements Runnable {

    private final Entity attacker;
    private final Entity defender;
    private final AtomicBoolean battleIsOver;
    private final boolean startsFirst;

    public AttackTask(Entity attacker, Entity defender, AtomicBoolean battleIsOver, boolean startsFirst) {
        this.attacker = attacker;
        this.defender = defender;
        this.battleIsOver = battleIsOver;
        this.startsFirst = startsFirst;
    }

    @Override
    public void run() {
        try {
            if (!startsFirst) {
                Thread.sleep((long) (attacker.getAttackSpeed() * 1000));
            }

            while (!battleIsOver.get() && attacker.isAlive() && defender.isAlive()) {

                if (battleIsOver.get() || !attacker.isAlive() || !defender.isAlive()) {
                    break;
                }

                attacker.attack(defender);

                if (!defender.isAlive()) {
                    if (battleIsOver.compareAndSet(false, true)) {
                        Log.printf("%s has defeated %s! (%s has %.1f HP left)\n",
                                attacker.getName(), defender.getName(), attacker.getName(), attacker.getHealthPoints());
                        defender.shutDownScheduler();
                    }
                    break;
                }

                Thread.sleep((long) (attacker.getAttackSpeed() * 1000));
            }
        } catch (InterruptedException e) {
            Log.println(attacker.getName() + " was interrupted mid fight!");
            Thread.currentThread().interrupt();
        }
    }
}
