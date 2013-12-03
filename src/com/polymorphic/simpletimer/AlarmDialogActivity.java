package com.polymorphic.simpletimer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class AlarmDialogActivity extends Activity{
    private static final String TAG = "AlarmDialogActivity";
    private String alarmId;
    private Ringtone ringtone;
    private MediaPlayer player;

    private void playAlarm() {
      Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
      ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarm);
      ringtone.play();
    }

    private void playCustomRingTone(String ringToneFile) {
      player = new MediaPlayer();
      try {
        player.setDataSource(ringToneFile);
        player.setLooping(true);
        player.prepare();
        player.start();
      } catch (IOException e) {
        Log.e(TAG, "MedialPlayer failed to play.");
      }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm_dialog);
        Bundle b = getIntent().getExtras();
        alarmId = b.getString(Model.ID_KEY);
        String alarmName = b.getString(Model.NAME_KEY);
        String customRingTonePath = b.getString(Model.RING_TONE_PATH_KEY);

        ((TextView) findViewById(R.id.alarm_dialog_alarm_name)).setText(alarmName);

        if (customRingTonePath == null) {
          playAlarm();
        } else {
          playCustomRingTone(customRingTonePath);
        }
    }

    public void finishDialog(View v) {
        if (ringtone != null) {
          ringtone.stop();
          ringtone = null;
        } else if (player != null) {
          player.release();
          player = null;
        }
        finish();
    }
}
