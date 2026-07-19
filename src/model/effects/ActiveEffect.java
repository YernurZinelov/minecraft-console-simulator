package model.effects;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveEffect {

    private final EffectApplier effectApplier;
    private volatile ScheduledFuture<?> scheduledFuture;
    private final AtomicInteger ticksLeft;

    public ActiveEffect(EffectApplier effectApplier, int ticksLeft) {
        this.effectApplier = Objects.requireNonNull(effectApplier, "Effect applier cannot be null!");
        this.ticksLeft = new AtomicInteger(ticksLeft);
        this.scheduledFuture = null;
    }

    public EffectApplier getEffectApplier() {
        return effectApplier;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    public int getTicksLeft() {
        return ticksLeft.get();
    }

    public int decrementAndGetTicks() {
        return this.ticksLeft.decrementAndGet();
    }
}
