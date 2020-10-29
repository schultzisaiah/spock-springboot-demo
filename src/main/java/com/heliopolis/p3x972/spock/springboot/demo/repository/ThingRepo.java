package com.heliopolis.p3x972.spock.springboot.demo.repository;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ThingRepo implements CrudRepository<Thing, Integer> {

  private final List<Thing> storage;

  public ThingRepo() {
    storage = new ArrayList<>();
    storage.add(new Thing(0, "TARDIS", now().minusDays(2)));
    storage.add(new Thing(1, "Holocron", now().minusYears(2000)));
    storage.add(new Thing(2, "Omega 13", now().minusYears(10)));
  }

  @Override
  public <S extends Thing> S save(S entity) {
    delete(entity);
    entity.setUpdateDate(now());
    storage.add(entity);
    return entity;
  }

  @Override
  public <S extends Thing> Iterable<S> saveAll(Iterable<S> entities) {
    stream(entities.spliterator(), false).forEach(this::save);
    return entities;
  }

  @Override
  public Optional<Thing> findById(Integer integer) {
    return storage.stream().filter(t -> integer == t.getId()).findAny();
  }

  @Override
  public boolean existsById(Integer integer) {
    return storage.stream().anyMatch(t -> integer == t.getId());
  }

  @Override
  public Iterable<Thing> findAll() {
    return storage;
  }

  @Override
  public Iterable<Thing> findAllById(Iterable<Integer> integers) {
    Set<Integer> ids =
        stream(integers.spliterator(), false).collect(toSet());
    return storage.stream().filter(t -> ids.contains(t.getId())).collect(toList());
  }

  @Override
  public long count() {
    return storage.size();
  }

  @Override
  public void deleteById(Integer integer) {
    storage.stream().filter(t -> integer == t.getId()).findAny().ifPresent(storage::remove);
  }

  @Override
  public void delete(Thing entity) {
    deleteById(entity.getId());
  }

  @Override
  public void deleteAll(Iterable<? extends Thing> entities) {
    stream(entities.spliterator(), false).forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    storage.clear();
  }
}
