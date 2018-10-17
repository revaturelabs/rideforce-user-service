package com.revature.rideforce.user.exceptions;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class CustomException extends Exception {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public CustomException(String msg) {
    super(msg);
    log.error(msg, this);
  }
}

