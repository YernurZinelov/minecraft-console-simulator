package service;

import enums.LocationType;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import model.base.Entity;
import model.base.Mob;

import java.util.*;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity) {
        if (entities.contains(entity))
            throw new DuplicateEntityException("Entity [" + entity.getName() + "] already exist. No duplication allowed!");
        entities.add(entity);
    }

    public void removeEntity(String name) {
        Optional<Entity> optionalEntity = findByName(name);
        if (optionalEntity.isPresent()) {
            entities.remove(optionalEntity.get());
            System.out.println("Entity [" + name + "] was successfully removed from the list.");
        } else {
            throw new EntityNotFoundException("Entity with name: [" + name + "] was not found!");
        }
    }

    public Optional<Entity> findByName(String name) {
        return entities.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst();
    }

    public List<Entity> getEntitiesSorted(Comparator<Entity> comparator) {
        return entities.stream()
                .sorted(comparator)
                .toList();
    }

    public List<Entity> getEntitiesFilteredBySpecialEffect() {
        return entities.stream()
                .filter(Entity::hasSpecialEffect)
                .toList();
    }

    public List<Mob> getMobsByLocation(LocationType location) {
        return entities.stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .filter(mob -> mob.getMainLocation() == location)
                .toList();
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
