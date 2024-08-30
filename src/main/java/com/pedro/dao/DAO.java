package com.pedro.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

abstract class DAO <Entity>{

    protected Map<Long, Entity> data = new HashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public Entity get(Long id) {
        return data.get(id);
    }

    public Entity getFirstBy(Predicate<Entity> condition) {
        return data.values()
                .stream()
                .filter(condition)
                .findFirst()
                .orElse(null);
    }

    public List<Entity> getPageBy(Predicate<Entity> condition, int pageSize, int pageNum) {
        return data.values()
                .stream()
                .filter(condition)
                .skip((long) pageSize*(pageNum-1))
                .limit(pageSize)
                .toList();
    }

    public Entity store(Entity entity, BiConsumer<Entity,Long> setId){
        long id = idGenerator.getAndIncrement();
        setId.accept(entity, id);
        data.put(id, entity);
        return entity;
    }

    public Entity update(Long id, Entity entity){
        data.put(id, entity);
        return entity;
    }

    public boolean delete(Long id){
        return data.remove(id) != null;
    }

    protected abstract Entity from(String fileLine);

    protected void loadData(String dataFile, BiConsumer<Entity,Long> setId) {
        Class clazz = DAO.class;
        InputStream inputStream = clazz.getResourceAsStream(dataFile);
        List<String> lines = readFromInputStream(inputStream);
        lines.forEach(l -> store(from(l), setId));
    }

    private List<String> readFromInputStream(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
