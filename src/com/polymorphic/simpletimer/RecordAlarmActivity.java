package com.polymorphic.simpletimer;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

public class RecordAlarmActivity extends FragmentActivity {
  private static String TAG = "RecordAlarmActivity";
  private MediaRecorder recorder;
  private String fileDir;
  public static String TMP_FILENAME = "/simple_timer_tmp_sound_clip.3gp";

  public RecordAlarmActivity() {
    if (isExternalStorageWritable()) {
      fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    } else {
      fileDir = this.getFilesDir().getAbsolutePath();
    }
  }

  private boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record_alarm);
  }

  @Override
  protected void onStart() {
    super.onStart();
    recorder = new MediaRecorder();
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    Log.d(TAG, fileDir + TMP_FILENAME); //xxx
    recorder.setOutputFile(fileDir + TMP_FILENAME);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    try {
      recorder.prepare();
    } catch (IOException e) {
      Log.e(TAG, "recorder.prepare() failed");
    }

    recorder.start();
  }

  public void stopRecording(View v) {
    recorder.stop();
    recorder.release();
    recorder = null;
    try {
      RecordAlarmDataHolder.instance().storeFileInfo(fileDir, TMP_FILENAME);
    } catch (DataHolderInstanceNotFoundException e) {
      Log.e(TAG, e.getMessage());
    }
    finish();
  }
}
