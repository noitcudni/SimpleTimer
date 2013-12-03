package com.polymorphic.simpletimer;

import java.util.HashMap;
import java.util.UUID;

public class Model {
  private static int anonymousCount = 0;
  private long totalTime;
  private long timeSoFar;
  private UUID id;
  private String name;
  private String customRingToneAbsolutePath;
  private boolean alarmPlayed;
  public static String HOUR = "hour";
  public static String MINUTE = "minute";
  public static String SECOND = "second";
  public static String RING_TONE_PATH_KEY = "RING_TONE_PATH_KEY";
  public static String ID_KEY = "MODEL_ID_KEY"; // used by bundle when communicating between activities
  public static String NAME_KEY = "MODEL_NAME"; // used by bundle when communicating between activities


  public Model(String name, long hour, long minute, long second) {
    this.id = UUID.randomUUID();
    alarmPlayed = false;
    totalTime = (hour*60*60 + minute*60 + second) * 1000;
    timeSoFar = 0;
    if (name == null || name.isEmpty()) {
      this.name = "Anonymous" + anonymousCount;
      anonymousCount++;
    } else {
      this.name = name;
    }
  }

  public void setCustomRingToneAbsolutePath(String path) {
    customRingToneAbsolutePath = path;
  }

  public String getCustomRingToneAbsolutePath() {
    return customRingToneAbsolutePath;
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

  public boolean isAlarmPlayed() {
    return alarmPlayed;
  }
  public void playAlarm() {
    this.alarmPlayed = true;
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
