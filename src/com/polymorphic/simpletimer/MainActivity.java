package com.polymorphic.simpletimer;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
  public final static String TAG = "MainActivity";
  //public final static String EXTRA_MESSAGE = "com.polymorphic.simpletimer";



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //android.support.v7.app.ActionBar bar = getSupportActionBar();
    //bar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

    if (findViewById(R.id.fragment_container) != null) {
      TimerFragment timerFragment = new TimerFragment();
      getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, timerFragment).commit();
    }
  }

  //@Override
  //protected void onCreate(Bundle savedInstanceState) {
    //super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);
    ////android.support.v7.app.ActionBar bar = getSupportActionBar();
    ////bar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
    //hourTimeTextView = (TextView) findViewById(R.id.hour_text_view);
    //minTimeTextView = (TextView) findViewById(R.id.minute_text_view);
    //secTimeTextView = (TextView) findViewById(R.id.second_text_view);

    //if (currTimeTextView == null) {
      //currTimeTextView = hourTimeTextView;
    //}
    //currState = State.FIRST_DIGIT;
  //}


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main_activity_actions, menu);
    return super.onCreateOptionsMenu(menu);
  }

  //public void sendMessage(View view) {
    //Intent intent = new Intent(this, DisplayMessageActivity.class);
    //EditText editText = (EditText) findViewById(R.id.edit_message);
    //String message = editText.getText().toString();
    //intent.putExtra(EXTRA_MESSAGE, message);
    //startActivity(intent);
  //}

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
      case R.id.action_search:
        //openSearch();
        return true;
      case R.id.action_settings:
        //openSettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
