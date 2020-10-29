package com.heliopolis.p3x972.spock.springboot.demo.controller;

import com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff;
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing;
import com.heliopolis.p3x972.spock.springboot.demo.service.ThingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/v1/thing", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {

  private final ThingService service;

  public ThingController(ThingService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ThingAndStuff getThing(@PathVariable int id) {
    return service.get(id);
  }

  @PutMapping
  @ResponseBody
  public ThingAndStuff saveThing(@RequestBody Thing newThing) {
    return service.add(newThing);
  }
}
