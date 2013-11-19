package com.polymorphic.simpletimer;

import java.util.HashMap;
import java.util.List;
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
  private final Context context;
  private List<Model> modelList;
  private HashMap<String, UpdateTimerTask> modelTimerMap;
  private Timer timer;

  public TimerArrayAdapter(Context context, List<Model> list) {
    super(context,R.layout.row_timer,list);
    this.context = context;
    this.modelList = list;
    this.modelTimerMap = new HashMap<String, UpdateTimerTask>();
    this.timer = new Timer();
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
    private Model model;
    private ViewHolder holder;

    public UpdateTimerTask(Model m, ViewHolder h) {
      super();
      model = m;
      holder = h;
      //totalTime = ms;
      //timeSoFar = 0;
    }

    public void run() {
      model.incTimer(ONE_SEC);
      //Log.d(TAG, this); //xxx

      final HashMap<String, Long> timerMap = model.getHrMinSec();
      holder.hourTextView.post(new Runnable() {
        public void run() {
          holder.hourTextView.setText(String.format("%02d", timerMap.get(Model.HOUR)));
        }
      });
      holder.minuteTextView.post(new Runnable() {
        public void run() {
          holder.minuteTextView.setText(String.format("%02d", timerMap.get(Model.MINUTE)));
        }
      });
      holder.secondTextView.post(new Runnable() {
        public void run() {
          holder.secondTextView.setText(String.format("%02d", timerMap.get(Model.SECOND)));
        }
      });

      // Update
      //hourTimeTextView.post(new Runnable() {
        //public void run() {
          //hourTimeTextView.setText(String.format("%02d", hours));
        //}
      //});
      //minTimeTextView.post(new Runnable() {
        //public void run() {
          //minTimeTextView.setText(String.format("%02d", minutes));
        //}
      //});
      //secTimeTextView.post(new Runnable() {
        //public void run() {
          //secTimeTextView.setText(String.format("%02d", seconds));
        //}
      //});

      if (model.isTimerOutstanding()) {
        cancel();

        //// play sound
        ////Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ////Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), alarm);
        ////r.play();
      }
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View rView = null;
    Model model = modelList.get(position);
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

    Log.d(TAG, "position: " + position);
    UpdateTimerTask task = modelTimerMap.get(model.getIdString());
    if (task == null) {
      // what if the timer is expired?
      Log.d(TAG, "creating a new timer");
      ViewHolder h = (ViewHolder) rView.getTag();

      modelTimerMap.put(model.getIdString(), task);
      timer.scheduleAtFixedRate(new UpdateTimerTask(model, h), UpdateTimerTask.ONE_SEC, UpdateTimerTask.ONE_SEC);
    } else {
      Log.d(TAG, "Got a timer already.");
    }

    return rView;
  }
}
