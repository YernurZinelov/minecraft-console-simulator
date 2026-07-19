package service;

import enums.LocationType;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import model.base.Entity;
import model.base.Mob;

import java.util.*;

public class EntityManager<T extends Entity> {
    private final List<T> entities = new ArrayList<>();

    public synchronized void addEntity(T entity) {
        if (entities.contains(entity))
            throw new DuplicateEntityException("Entity [" + entity.getName() + "] already exist. No duplication allowed!");
        entities.add(entity);
    }

    public synchronized void removeEntity(String name) {
        T entity = findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Entity with name: [" + name + "] was not found!"));

        entities.remove(entity);
        entity.shutDownScheduler();
        Log.println("Entity [" + name + "] was successfully removed from the list.");
    }

    public synchronized Optional<T> findByName(String name) {
        return entities.stream()
                .filter(e -> name.equals(e.getName()))
                .findFirst();
    }

    public synchronized List<T> getAllEntities() {
        return new ArrayList<>(entities);
    }

    public synchronized List<T> getEntitiesSorted(Comparator<? super T> comparator) {
        return entities.stream()
                .sorted(comparator)
                .toList();
    }

    public synchronized List<T> getEntitiesFilteredBySpecialEffect() {
        return entities.stream()
                .filter(Entity::hasSpecialEffect)
                .toList();
    }

    public synchronized List<Mob> getMobsByLocation(LocationType location) {
        return entities.stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .filter(mob -> mob.getMainLocation() == location)
                .toList();
    }

    public synchronized void printList() {
        Log.println("=== PRINTING CONTENT OF LIST: ===");
        Log.println("");

        for (int i = 1; i <= entities.size(); i++) {

            String formattedLine = String.format(Locale.US, "%2d. %s", i, entities.get(i-1));
            Log.println(formattedLine);
        }
    }
}
