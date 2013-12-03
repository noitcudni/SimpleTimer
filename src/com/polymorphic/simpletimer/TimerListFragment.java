package com.polymorphic.simpletimer;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

public class TimerListFragment extends ListFragment{
  private static final String TAG = "TimeListFragment";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "calling onCreate");
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    MainActivity activity = (MainActivity) getActivity();
    ArrayAdapter<Model> adapter = new TimerArrayAdapter(activity);
    setListAdapter(adapter);
  }

}
