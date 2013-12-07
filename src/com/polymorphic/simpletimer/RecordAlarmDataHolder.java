package com.polymorphic.simpletimer;

public class RecordAlarmDataHolder {
  private static RecordAlarmDataHolder instance;
  private String filePath;
  private String filename;
  private RecordAlarmDataHolder() {

  }

  public static RecordAlarmDataHolder instance() throws DataHolderInstanceNotFoundException {
    if (instance == null) {
      throw new DataHolderInstanceNotFoundException("data holder instance is missing.");
    }
    return instance;
  }

  public static RecordAlarmDataHolder newInstance() {
    instance = new RecordAlarmDataHolder();
    return instance;
  }

  public void storeFileInfo(String filePath, String filename) {
    this.filePath = filePath;
    this.filename = filename;
  }

  public static void destroyInstance() {
    instance = null;
  }

  public String getFilePath() {
    return filePath;
  }
  public String getFilename() {
    return filename;
  }
}
