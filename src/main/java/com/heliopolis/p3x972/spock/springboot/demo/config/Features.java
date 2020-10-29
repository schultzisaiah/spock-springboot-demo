package com.heliopolis.p3x972.spock.springboot.demo.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.InfoLink;
import org.togglz.core.annotation.Label;
import org.togglz.core.annotation.Owner;
import org.togglz.core.context.FeatureContext;

public enum Features implements Feature {

  @Label("Allow updating existing records")
  @Owner("@schultzisaiah")
  @InfoLink("https://github.com/link/to/issue")
  ALLOW_UPDATES;

  public boolean isActive() {
    return FeatureContext.getFeatureManager().isActive(this);
  }
}
