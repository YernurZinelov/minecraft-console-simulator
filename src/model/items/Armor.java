package model.items;

import exceptions.InvalidDataException;

import java.util.Objects;

public abstract class Armor {

    private final String name;
    private final double protectionPoints;

    public Armor(String name, double protectionPoints) {
        this.name = Objects.requireNonNull(name, "Armor name cannot be null!");

        if (protectionPoints < 0) {
            throw new InvalidDataException("Armor's protection cannot be less than zero!");
        }
        this.protectionPoints = protectionPoints;
    }

    public String getName() {
        return name;
    }

    public double getProtectionPoints() {
        return protectionPoints;
    }

    @Override
    public String toString() {
        return String.format("[Name: %s, Protection Points: %.2f]", name, protectionPoints);
    }
}
