package model.items;

import exceptions.InvalidDataException;

public abstract class Armor {

    private String name;
    private double protectionPoints;

    public Armor(String name, double protectionPoints) {
        this.name = name;

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
