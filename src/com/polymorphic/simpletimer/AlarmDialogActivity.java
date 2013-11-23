package com.polymorphic.simpletimer;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class AlarmDialogActivity extends Activity{
    private String alarmId;
    private Ringtone ringtone;

    private void playAlarm() {
      Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
      ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarm);
      ringtone.play();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm_dialog);
        Bundle b = getIntent().getExtras();
        alarmId = b.getString(Model.ID_KEY);
        String alarmName = b.getString(Model.NAME_KEY);
        ((TextView) findViewById(R.id.alarm_dialog_alarm_name)).setText(alarmName);
        playAlarm();
    }

    public void finishDialog(View v) {
        ringtone.stop();
        finish();
    }
}
