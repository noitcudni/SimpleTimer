package com.polymorphic.simpletimer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TimerFragment extends Fragment implements OnClickListener{
  public final static String TAG = "TimeFragment";
  public final static long ONE_SEC = 1000;
  private static final String STATE_SEL_TIME = "sel_time_state";
  private static final String STATE_NAME = "name_state";
  private static final String STATE_HOUR = "hour_state";
  private static final String STATE_MINUTE = "minute_state";
  private static final String STATE_SECOND = "second_state";
  private static final String STATE_DIGIT_STATE = "digit_state";

  public enum State {
    FIRST_DIGIT, SECOND_DIGIT;
  }

  private EditText nameEditTextView;
  private TextView currTimeTextView;
  private TextView hourTimeTextView;
  private TextView minTimeTextView;
  private TextView secTimeTextView;

  private State currDigitState;
  //private Timer timer;

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.hour_text_view:
      case R.id.minute_text_view:
      case R.id.second_text_view:
        onTimeTextClick(v);
        break;
      case R.id.btn_1:
      case R.id.btn_2:
      case R.id.btn_3:
      case R.id.btn_4:
      case R.id.btn_5:
      case R.id.btn_6:
      case R.id.btn_7:
      case R.id.btn_8:
      case R.id.btn_9:
      case R.id.btn_0:
        onNumPadClick(v);
        break;
      case R.id.start_btn:
        onStartClick(v);
        break;
    }
  }


  @Override
  public void onStart()
  {
    super.onStart();
    Log.d(TAG, this + ": onStart()");
  }

  private void clearTimerBorderHelper(int victimId, int selectedId) {
    TextView currView =
      ((TextView) getActivity().findViewById(victimId));
    Drawable background = currView.getBackground();
    if (background != null) {
      currView.setBackgroundResource(0);
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
    currDigitState = State.FIRST_DIGIT;
  }

  private String digitBuilder(String input) {
    String first_digit;
    if (currDigitState == State.FIRST_DIGIT)
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
    switch (currDigitState) {
      case FIRST_DIGIT:
        currDigitState = State.SECOND_DIGIT;
        break;
      case SECOND_DIGIT:
        if( currTimeTextView.getId() == R.id.hour_text_view) {
          selectTimeText(getActivity().findViewById(R.id.minute_text_view));
        } else if (currTimeTextView.getId() == R.id.minute_text_view) {
          selectTimeText(getActivity().findViewById(R.id.second_text_view));
        } else if (currTimeTextView.getId() == R.id.second_text_view) {
        }
        currDigitState = State.FIRST_DIGIT;
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
    String name = nameEditTextView.getText().toString();
    long hour = Long.parseLong(hourTimeTextView.getText().toString());
    long minute = Long.parseLong(minTimeTextView.getText().toString());
    long second = Long.parseLong(secTimeTextView.getText().toString());

    long ms = (hour*60*60 + minute*60 + second) * 1000;
    Log.d(TAG, "onStartClick: " + String.valueOf(ms));

    Model model = new Model(name, hour, minute, second);
    ((MainActivity)getActivity()).saveToModelList(model);

    // replace the current fragment with the list of timer fragment
    TimerListFragment listFragment = new TimerListFragment();
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, listFragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.timer_fragment, container, false);

    view.findViewById(R.id.hour_text_view).setOnClickListener(this);
    view.findViewById(R.id.minute_text_view).setOnClickListener(this);
    view.findViewById(R.id.second_text_view).setOnClickListener(this);
    view.findViewById(R.id.btn_0).setOnClickListener(this);
    view.findViewById(R.id.btn_1).setOnClickListener(this);
    view.findViewById(R.id.btn_2).setOnClickListener(this);
    view.findViewById(R.id.btn_3).setOnClickListener(this);
    view.findViewById(R.id.btn_4).setOnClickListener(this);
    view.findViewById(R.id.btn_5).setOnClickListener(this);
    view.findViewById(R.id.btn_6).setOnClickListener(this);
    view.findViewById(R.id.btn_7).setOnClickListener(this);
    view.findViewById(R.id.btn_8).setOnClickListener(this);
    view.findViewById(R.id.btn_9).setOnClickListener(this);
    view.findViewById(R.id.start_btn).setOnClickListener(this);

    nameEditTextView = (EditText) view.findViewById(R.id.timer_name_view);
    hourTimeTextView = (TextView) view.findViewById(R.id.hour_text_view);
    minTimeTextView = (TextView) view.findViewById(R.id.minute_text_view);
    secTimeTextView = (TextView) view.findViewById(R.id.second_text_view);

    if (currTimeTextView == null) {
      currTimeTextView = hourTimeTextView;
    }
    currDigitState = State.FIRST_DIGIT;
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (savedInstanceState != null) {
      // restore state from onSaveInstanceState
      String name = savedInstanceState.getString(STATE_NAME);
      String hour = savedInstanceState.getString(STATE_HOUR);
      String minute = savedInstanceState.getString(STATE_MINUTE);
      String second = savedInstanceState.getString(STATE_SECOND);
      nameEditTextView.setText(name);
      hourTimeTextView.setText(hour);
      minTimeTextView.setText(minute);
      secTimeTextView.setText(second);

      String selTimeState = savedInstanceState.getString(STATE_SEL_TIME);
      if (selTimeState.equals(STATE_HOUR)) {
        selectTimeText(hourTimeTextView);
      } else if (selTimeState.equals(STATE_MINUTE)) {
        selectTimeText(minTimeTextView);
      } else if (selTimeState.equals(STATE_SECOND)) {
        selectTimeText(secTimeTextView);
      }

      currDigitState = State.values()[savedInstanceState.getInt(STATE_DIGIT_STATE)];
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    // save custom states
    String name = nameEditTextView.getText().toString();
    String hour = hourTimeTextView.getText().toString();
    String minute = minTimeTextView.getText().toString();
    String second = secTimeTextView.getText().toString();

    savedInstanceState.putString(STATE_NAME, name);
    savedInstanceState.putString(STATE_HOUR, hour);
    savedInstanceState.putString(STATE_MINUTE, minute);
    savedInstanceState.putString(STATE_SECOND, second);

    switch(currTimeTextView.getId()) {
      case R.id.hour_text_view:
        savedInstanceState.putString(STATE_SEL_TIME, STATE_HOUR);
        break;
      case R.id.minute_text_view:
        savedInstanceState.putString(STATE_SEL_TIME, STATE_MINUTE);
        break;
      case R.id.second_text_view:
        savedInstanceState.putString(STATE_SEL_TIME, STATE_SECOND);
        break;
    }

    savedInstanceState.putInt(STATE_DIGIT_STATE, currDigitState.ordinal());
    super.onSaveInstanceState(savedInstanceState);
  }
}

