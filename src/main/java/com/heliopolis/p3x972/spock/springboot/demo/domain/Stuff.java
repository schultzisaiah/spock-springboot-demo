package com.heliopolis.p3x972.spock.springboot.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
@AllArgsConstructor
public class Stuff {
  private String value;
}
