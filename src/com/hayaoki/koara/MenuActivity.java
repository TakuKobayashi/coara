package com.hayaoki.koara;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuActivity extends Activity {

  private AlarmManager _alarmManager;
  private MenuListAdapter _adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.menu_view);
    _alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    ArrayList<Bitmap> imagelist = new ArrayList<Bitmap>();
    TypedArray images = this.getResources().obtainTypedArray(R.array.bg_array_drawable);
    for(int i = 0;i < images.length();++i){
      imagelist.add(((BitmapDrawable) images.getDrawable(i)).getBitmap());
    }

    String[] words = this.getResources().getStringArray(R.array.menu_word_list);
    _adapter = new MenuListAdapter(this, Arrays.asList(words));
    _adapter.setImageList(imagelist);
    ListView listView = (ListView) findViewById(R.id.MenuList);
    listView.setAdapter(_adapter);
    listView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch(position){
        case 0:
          intent.setClass(MenuActivity.this, KoaraActivity.class);
          startActivity(intent);
          break;
        case 1:
          intent.setClass(MenuActivity.this, StoreActivity.class);
          startActivity(intent);
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
        }
      }
    });
  }

  private void setAlarm(long time){
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    // 5秒に設定
    calendar.add(Calendar.SECOND, 1);
    Log.d("coara", String.valueOf(System.currentTimeMillis()));
    Log.d("coara", String.valueOf(calendar.getTimeInMillis()));

  //発行するIntentの生成
    Intent bootIntent = new Intent(MenuActivity.this, AlarmBroadcastReciever.class);
    PendingIntent sender = PendingIntent.getBroadcast(MenuActivity.this, 0, bootIntent, 0);
    _alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    Log.d("coara", "hogehoge");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    _adapter.release();
  }
}
