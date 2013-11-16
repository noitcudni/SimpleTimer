package com.polymorphic.simpletimer;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimerFragment extends Fragment {
  public final static String TAG = "TimeFragment";
  public final static long ONE_SEC = 1000;
  public enum State {
    FIRST_DIGIT, SECOND_DIGIT;
  }

  private TextView currTimeTextView;
  private TextView hourTimeTextView;
  private TextView minTimeTextView;
  private TextView secTimeTextView;

  private State currState;
  private Timer timer;

  class UpdateTimerTask extends TimerTask {
    private long totalTime;
    private long timeSoFar;

    public UpdateTimerTask(long ms) {
      super();
      totalTime = ms;
      timeSoFar = 0;
    }

    public void run() {
      timeSoFar += ONE_SEC;
      Log.d(TAG, "totalTime: " + totalTime + " | timeSoFar: " + timeSoFar);
      // Calculate hours, minutes and seconds
      long remainTime = totalTime - timeSoFar;

      final long hours = remainTime / 3600000;
      remainTime %= 3600000;
      final long minutes = remainTime / 60000;
      remainTime %= 60000;
      final long seconds = remainTime / 1000;
      Log.d(TAG, "hours   : " + hours);
      Log.d(TAG, "minutes : " + minutes);
      Log.d(TAG, "seconds : " + seconds);

      // Update
      hourTimeTextView.post(new Runnable() {
        public void run() {
          hourTimeTextView.setText(String.format("%02d", hours));
        }
      });
      minTimeTextView.post(new Runnable() {
        public void run() {
          minTimeTextView.setText(String.format("%02d", minutes));
        }
      });
      secTimeTextView.post(new Runnable() {
        public void run() {
          secTimeTextView.setText(String.format("%02d", seconds));
        }
      });

      if (timeSoFar >= totalTime) {
        timer.cancel();

        // play sound
        //Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarm);
        //r.play();
      }
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    hourTimeTextView = (TextView) getActivity().findViewById(R.id.hour_text_view);
    minTimeTextView = (TextView) getActivity().findViewById(R.id.minute_text_view);
    secTimeTextView = (TextView) getActivity().findViewById(R.id.second_text_view);

    if (currTimeTextView == null) {
      currTimeTextView = hourTimeTextView;
    }
    currState = State.FIRST_DIGIT;
  }

  private void clearTimerBorderHelper(int victimId, int selectedId) {
    TextView currView =
      ((TextView) getActivity().findViewById(victimId));
    if (selectedId != currView.getId()) {
      Drawable background = currView.getBackground();
      if (background != null) {
        currView.setBackgroundResource(0);
      }
    }
  }

  private void clearTimerBorder(int selectedId) {
    clearTimerBorderHelper(R.id.hour_text_view, selectedId);
    clearTimerBorderHelper(R.id.minute_text_view, selectedId);
    clearTimerBorderHelper(R.id.second_text_view, selectedId);
  }

  public void onTimeTextClick(View view) {
    selectTimeText(view);
  }

  private void selectTimeText(View view) {
    currTimeTextView = (TextView) view;
    clearTimerBorder(view.getId());
    view.setBackgroundResource(R.drawable.timer_border);
    currState = State.FIRST_DIGIT;
  }

  private String digitBuilder(String input) {
    String first_digit;
    if (currState == State.FIRST_DIGIT)
      first_digit = "0";
    else
      first_digit = Character.valueOf(currTimeTextView.getText().charAt(1)).toString();

    StringBuilder sb = new StringBuilder(2);
    sb.append(first_digit);
    sb.append(input);
    String rStr = sb.toString();
    int currTimeTextId = currTimeTextView.getId();
    if (currTimeTextId == R.id.minute_text_view || currTimeTextId == R.id.second_text_view) {
      if (Integer.parseInt(rStr) > 59) {
        rStr = "59";
      }
    }
    return rStr;
  }

  private void transitionState() {
    switch (currState) {
      case FIRST_DIGIT:
        currState = State.SECOND_DIGIT;
        break;
      case SECOND_DIGIT:
        if( currTimeTextView.getId() == R.id.hour_text_view) {
          selectTimeText(getActivity().findViewById(R.id.minute_text_view));
        } else if (currTimeTextView.getId() == R.id.minute_text_view) {
          selectTimeText(getActivity().findViewById(R.id.second_text_view));
        } else if (currTimeTextView.getId() == R.id.second_text_view) {
        }
        currState = State.FIRST_DIGIT;
        break;
    }
  }

  public void onNumPadClick(View view) {
    String input = ((TextView)view).getText().toString();
    Log.d(TAG, input);
    currTimeTextView.setText(digitBuilder(input));
    transitionState();
  }

  public void onStartClick(View view) {
    long hour = Long.parseLong(hourTimeTextView.getText().toString());
    long minute = Long.parseLong(minTimeTextView.getText().toString());
    long second = Long.parseLong(secTimeTextView.getText().toString());
    long ms = (hour*60*60 + minute*60 + second) * 1000;
    Log.d(TAG, "onStartClick: " + String.valueOf(ms));

    timer = new Timer();
    timer.scheduleAtFixedRate(new UpdateTimerTask(ms), ONE_SEC, ONE_SEC);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.timer_fragment, container, false);
  }

}
