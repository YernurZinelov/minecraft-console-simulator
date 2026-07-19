package model.effects;

import model.base.Entity;

public interface EffectApplier {
    void applyEffect(Entity target);

    int getTickPeriodSeconds();
}
