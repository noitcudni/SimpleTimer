package com.polymorphic.simpletimer;

import java.util.HashMap;
import java.util.UUID;

public class Model {
  private long totalTime;
  private long timeSoFar;
  private UUID id;
  private String name;
  public static String HOUR = "hour";
  public static String MINUTE = "minute";
  public static String SECOND = "second";


  public Model(String name, long hour, long minute, long second) {
    this.id = UUID.randomUUID();
    this.name = name;
    totalTime = (hour*60*60 + minute*60 + second) * 1000;
    timeSoFar = 0;
  }

  public String getIdString() {
    return this.id.toString();
  }

  public boolean isTimerOutstanding() {
    return totalTime > timeSoFar;
  }
  public long getTotalTimeMs() {
    return totalTime;
  }

  public String getName() {
    return name;
  }

  public void incTimer(long incAmt) {
    timeSoFar += incAmt;
  }

  public void setName(String n) {
    name = n;
  }

  public HashMap<String, Long> getHrMinSec() {
    HashMap<String, Long> rMap = new HashMap<String, Long>();
    long remainTime = totalTime - timeSoFar;

    long hours = remainTime / 3600000;
    remainTime %= 3600000;
    long minutes = remainTime / 60000;
    remainTime %= 60000;
    long seconds = remainTime / 1000;

    rMap.put(HOUR, hours);
    rMap.put(MINUTE, minutes);
    rMap.put(SECOND, seconds);
    return rMap;
  }
}
