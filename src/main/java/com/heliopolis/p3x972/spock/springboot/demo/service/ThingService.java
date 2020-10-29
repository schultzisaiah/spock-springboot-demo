package com.heliopolis.p3x972.spock.springboot.demo.service;

import com.heliopolis.p3x972.spock.springboot.demo.config.Features;
import com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff;
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing;
import com.heliopolis.p3x972.spock.springboot.demo.repository.ThingRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ThingService {

  @Value("${app.env}")
  private String env;

  private final ThingRepo thingRepo;
  private final StuffService stuffService;

  public ThingService(ThingRepo thingRepo,
      StuffService stuffService) {
    this.thingRepo = thingRepo;
    this.stuffService = stuffService;
  }

  public ThingAndStuff get(int id) {
    return thingRepo.findById(id)
        .map(t -> new ThingAndStuff(t, stuffService.get(t.getId())))
        .orElseThrow(
            () ->
                new HttpClientErrorException(
                    HttpStatus.NOT_FOUND, String.format("Thing with id %s does not exist in %s", id, env)));
  }

  public ThingAndStuff add(Thing newThing) {
    if (!Features.ALLOW_UPDATES.isActive() && thingRepo.existsById(newThing.getId())) {
      throw new HttpClientErrorException(
          HttpStatus.CONFLICT, String.format("Thing with id %s already exists in %s!", newThing.getId(), env));
    }
    return new ThingAndStuff(thingRepo.save(newThing), stuffService.get(newThing.getId()));
  }
}
