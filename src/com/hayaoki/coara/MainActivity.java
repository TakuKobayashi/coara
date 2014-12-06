package com.hayaoki.coara;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

  private AlarmManager _alarmManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    _alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  }

  private void setAlarm(long time){
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    // 5秒に設定
    calendar.add(Calendar.SECOND, 1);
    Log.d("coara", String.valueOf(System.currentTimeMillis()));
    Log.d("coara", String.valueOf(calendar.getTimeInMillis()));

  //発行するIntentの生成
    Intent bootIntent = new Intent(MainActivity.this, AlarmBroadcastReciever.class);
    PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, bootIntent, 0);
    _alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    Log.d("coara", "hogehoge");
  }
}
