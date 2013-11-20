package com.polymorphic.simpletimer;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class TimerListFragment extends ListFragment{

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    MainActivity activity = (MainActivity) getActivity();
    ArrayAdapter<Model> adapter = new TimerArrayAdapter(activity);
    setListAdapter(adapter);
  }

}
