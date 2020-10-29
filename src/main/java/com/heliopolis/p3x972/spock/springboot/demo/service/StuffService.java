package com.heliopolis.p3x972.spock.springboot.demo.service;

import com.heliopolis.p3x972.spock.springboot.demo.domain.Stuff;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StuffService {

  public List<Stuff> get(int id) {
    switch (id) {
      case 0:
        return Arrays.asList(
            createStuff("sonic screwdriver"), createStuff("The Doctor"), createStuff("Bad Wolf"));
      case 1:
        return Arrays.asList(createStuff("Jedi"), createStuff("Sith"));
      case 2:
        return Arrays.asList(createStuff("Never give up!"), createStuff("Never surrender!"));
      case 3:
        return Arrays.asList(createStuff("Federation"), createStuff("Romulan Star Empire"));
      default:
        return new ArrayList<>();
    }
  }

  private static Stuff createStuff(String value) {
    return new Stuff(value);
  }
}
