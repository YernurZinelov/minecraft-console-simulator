package service;

import enums.LocationType;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import model.base.Entity;
import model.base.Mob;

import java.util.*;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();

    public void addEntities(Entity entity) {
        if (entities.contains(entity))
            throw new DuplicateEntityException("Entity [" + entity.getName() + "] already exist. No duplication allowed!");
        entities.add(entity);
    }

    public void removeEntities(String name) {
        Optional<Entity> optionalEntity = findByName(name);
        if (optionalEntity.isPresent()) {
            entities.remove(optionalEntity.get());
            System.out.println("Entity [" + name + "] was successfully removed from the list.");
        } else {
            throw new EntityNotFoundException("Entity with name: [" + name + "] was not found!");
        }
    }

    public Optional<Entity> findByName(String name) {
        for (Entity entity : entities) {
            if (entity.getName().equals(name)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    public List<Entity> getEntitiesSortedByHealth() {
        List<Entity> sorted = new ArrayList<>(entities);
        sorted.sort(Comparator.comparingDouble(Entity::getHealthPoints));
        return sorted;
    }

    public List<Entity> getEntitiesSortedByDamage() {
        List<Entity> sorted = new ArrayList<>(entities);
        sorted.sort(Comparator.comparingDouble(Entity::getDamagePoints));
        return sorted;
    }

    public List<Entity> getEntitiesSortedByAttackSpeed() {
        List<Entity> sorted = new ArrayList<>(entities);
        sorted.sort(Comparator.comparingDouble(Entity::getAttackSpeed));
        return sorted;
    }

    public List<Entity> getEntitiesFilteredBySpecialEffect() {
        List<Entity> filtered = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.hasSpecialEffect()) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    public List<Mob> getMobsByLocation(LocationType location) {
        List<Mob> mobList = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Mob && ((Mob) e).getMainLocation() == location) {
                mobList.add((Mob)e);
            }
        }
        return mobList;
    }

    public void printList() {
        System.out.println("=== PRINTING CONTENT OF LIST: ===");
        System.out.println();

        for (int i = 1; i <= entities.size(); i++) {
            String indexStr = i + ".";

            System.out.printf(Locale.US, "%-4s %s\n", indexStr, entities.get(i-1));
        }
    }
}
