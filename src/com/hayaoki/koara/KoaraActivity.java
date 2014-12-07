package com.hayaoki.koara;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class KoaraActivity extends Activity {
  private KoaraView _koaraView;
  private ImageView _image;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.koara_view);
    _koaraView = (KoaraView) findViewById(R.id.KoaraView);
    AssetManager as = this.getResources().getAssets();
    ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    try {
      for(int i = 1;i <= 21;i++){
        InputStream is = as.open("koara/swing/neck" + i + ".png");
        Bitmap bm = BitmapFactory.decodeStream(is);
        list.add(bm);
      }
      InputStream is = as.open("koaraBg/ui_day.png");
      Bitmap bm = BitmapFactory.decodeStream(is);
      _koaraView.addImage(bm);
    } catch (IOException e) {
      e.printStackTrace();
    }
    _koaraView.setAnimationImageList(list);
    _image = (ImageView) findViewById(R.id.DemoImage);
    Button button1 = (Button) findViewById(R.id.BgButton);
    button1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        _image.setImageResource(R.drawable.ui_night_demo);
      }
    });
    Button button2 = (Button) findViewById(R.id.ItemButton);

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    _koaraView.releaseImage();
  }
}
