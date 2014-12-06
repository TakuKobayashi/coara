package com.hayaoki.coara;

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

public class StatusActivity extends Activity {

  private MenuListAdapter _adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.menu_view);

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
        switch(position){
        case 0:
          break;
        case 1:
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

  @Override
  protected void onDestroy() {
    super.onDestroy();
    _adapter.release();
  }
}
