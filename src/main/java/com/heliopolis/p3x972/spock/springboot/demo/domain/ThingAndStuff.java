package com.heliopolis.p3x972.spock.springboot.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_EMPTY)
@Data
public class ThingAndStuff extends Thing {
  private List<Stuff> listOfStuff;

  public ThingAndStuff(Thing thing, List<Stuff> listOfStuff) {
    super(thing.getId(), thing.getName(), thing.getUpdateDate());
    this.listOfStuff = listOfStuff;
  }
}
