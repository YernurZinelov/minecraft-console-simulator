package enums;

public enum LocationType {
    CAVE("Cave"),
    PLAIN("Plain"),
    NETHER("Nether"),
    END("End");

    private final String displayName;

    LocationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
