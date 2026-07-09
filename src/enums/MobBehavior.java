package enums;

public enum MobBehavior {
    PASSIVE("Passive"),
    NEUTRAL("Neutral"),
    HOSTILE("Hostile");

    private final String displayName;

    MobBehavior(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
