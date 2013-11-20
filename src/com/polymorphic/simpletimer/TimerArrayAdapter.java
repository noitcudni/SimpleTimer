package com.polymorphic.simpletimer;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimerArrayAdapter extends ArrayAdapter<Model> {
  public final static String TAG = "TimeArrayAdapter";
  private MainActivity context;
  //private List<Model> modelList;
  //private ConcurrentHashMap<String, UpdateTimerTask> modelTimerMap;
  private static Timer timer = null;

  public TimerArrayAdapter(MainActivity context) {
    super(context,R.layout.row_timer, context.getModelList());
    this.context = context;
    //this.modelList = list;
    //this.modelTimerMap = new ConcurrentHashMap<String, UpdateTimerTask>();

    //this.timer = new Timer(); //timer is always on.
    if (timer == null) {
      Log.d(TAG, "constructing a new timer");
      timer = new Timer();
      timer.scheduleAtFixedRate(new UpdateTimerTask(), UpdateTimerTask.ONE_SEC, UpdateTimerTask.ONE_SEC);
    }
  }

  static class ViewHolder {
    public TextView nameTextView;
    public TextView hourTextView;
    public TextView minuteTextView;
    public TextView secondTextView;
  }

  class UpdateTimerTask extends TimerTask {
    public final static long ONE_SEC = 1000;
    //private long totalTime;
    //private long timeSoFar;

    //private Model model;
    //private ViewHolder holder;

    public UpdateTimerTask() {

    }

    public void run() {
      //List<Model> modelList = context.getModelList();
      HashMap<Model, View> modelViewMap = context.getModelViewMap();
      Log.d(TAG, "addr: " + this);
      Log.d(TAG, "size of the model list: " + modelViewMap.size());

      int i = 0;
      for (Map.Entry<Model, View> entry: modelViewMap.entrySet()) {
        Model m = entry.getKey();
        View v = entry.getValue();
        Log.d(TAG, "inside the loop: " + i); //xx
        i++; //xxx

        final ViewHolder h = (ViewHolder) v.getTag();
        Log.d(TAG, "isTimerOutStanding: " + m.isTimerOutstanding()); //xxx

        if(m.isTimerOutstanding()) {
          m.incTimer(ONE_SEC);
          final HashMap<String, Long> timerMap = m.getHrMinSec();

          h.hourTextView.post(new Runnable() {
            public void run() {
              h.hourTextView.setText(String.format("%02d", timerMap.get(Model.HOUR)));
            }
          });
          h.minuteTextView.post(new Runnable() {
            public void run() {
              h.minuteTextView.setText(String.format("%02d", timerMap.get(Model.MINUTE)));
            }
          });
          h.secondTextView.post(new Runnable() {
            public void run() {
              h.secondTextView.setText(String.format("%02d", timerMap.get(Model.SECOND)));
            }
          });
        }
      } //for
    }

    //public UpdateTimerTask(Model m, ViewHolder h) {
      //super();
      //model = m;
      //holder = h;
      ////totalTime = ms;
      ////timeSoFar = 0;
    //}

    //public void run() {
      //model.incTimer(ONE_SEC);
      ////Log.d(TAG, this); //xxx

      //final HashMap<String, Long> timerMap = model.getHrMinSec();
      //holder.hourTextView.post(new Runnable() {
        //public void run() {
          //holder.hourTextView.setText(String.format("%02d", timerMap.get(Model.HOUR)));
        //}
      //});
      //holder.minuteTextView.post(new Runnable() {
        //public void run() {
          //holder.minuteTextView.setText(String.format("%02d", timerMap.get(Model.MINUTE)));
        //}
      //});
      //holder.secondTextView.post(new Runnable() {
        //public void run() {
          //holder.secondTextView.setText(String.format("%02d", timerMap.get(Model.SECOND)));
        //}
      //});

      //if (model.isTimerOutstanding()) {
        //cancel();

        ////// play sound
        //////Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //////Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), alarm);
        //////r.play();
      //}
    //}
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View rView = null;
    Model model = this.context.getModel(position);
    if (convertView == null) {
      LayoutInflater inflator = LayoutInflater.from(context);
      rView = inflator.inflate(R.layout.row_timer, null);
      final ViewHolder holder = new ViewHolder();
      holder.nameTextView = (TextView)rView.findViewById(R.id.row_timer_name);
      holder.hourTextView = (TextView)rView.findViewById(R.id.row_hour);
      holder.minuteTextView = (TextView)rView.findViewById(R.id.row_minute);
      holder.secondTextView = (TextView)rView.findViewById(R.id.row_second);

      HashMap<String, Long> timeTextMap = model.getHrMinSec();
      holder.nameTextView.setText(model.getName());
      holder.hourTextView.setText(String.valueOf(timeTextMap.get(Model.HOUR)));
      holder.minuteTextView.setText(String.valueOf(timeTextMap.get(Model.MINUTE)));
      holder.secondTextView.setText(String.valueOf(timeTextMap.get(Model.SECOND)));
      rView.setTag(holder);
    } else {
      //reuse the old view
      rView = convertView;
    }

    this.context.saveToModelViewMap(model, rView);

    //Log.d(TAG, "position: " + position);
    //UpdateTimerTask task = modelTimerMap.get(model.getIdString());
    //if (task == null) {
      //// what if the timer is expired?
      //Log.d(TAG, "creating a new timer");
      //ViewHolder h = (ViewHolder) rView.getTag();

      //modelTimerMap.put(model.getIdString(), task);
     //timer.scheduleAtFixedRate(new UpdateTimerTask(model, h), UpdateTimerTask.ONE_SEC, UpdateTimerTask.ONE_SEC);
    //} else {
      //Log.d(TAG, "Got a timer already.");
    //}

    return rView;
  }
}
