package com.polymorphic.simpletimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {
  public final static String TAG = "MainActivity";
  private List<Model> modelList;
  private HashMap<Model, View> modelViewMap;

  public List<Model> getModelList() {
    synchronized(this) {
      return modelList;
    }
  }

  public Model getModel(int pos) {
    synchronized(this) {
      return modelList.get(pos);
    }
  }

  public void saveToModelList(Model m) {
    synchronized(this) {
      modelList.add(m);
    }
  }

  public void saveToModelViewMap(Model m, View v) {
    synchronized(this) {
      modelViewMap.put(m, v);
    }
  }

  public HashMap<Model, View> getModelViewMap() {
    synchronized(this) {
      return modelViewMap;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //android.support.v7.app.ActionBar bar = getSupportActionBar();
    //bar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

    if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
      TimerFragment timerFragment = new TimerFragment();
      getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, timerFragment).commit();
    }
    modelList = new ArrayList<Model>();
    modelViewMap = new HashMap<Model, View>();
  }

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

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    // save custom states
    Log.d(TAG, "calling MainActivity:onSaveInstanceState");
    super.onSaveInstanceState(savedInstanceState);
  }
}
