package com.hayaoki.koara;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.activeandroid.query.Select;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public class StoreActivity extends Activity {

  private ShopGridAdapter _adapter;
  private List<MstShopModel> _mst_shop_list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.store_view);
    List<ShopModel> shopList = new Select().from(ShopModel.class).execute();
    ArrayList<String> list = new ArrayList<String>();
    for(int i = 0;i < shopList.size();++i){
    	list.add(String.valueOf(shopList.get(i).mst_shop_id));
    }
    _mst_shop_list = new Select().from(MstShopModel.class).where("item_id NOT IN (?)", ApplicationHelper.join(list, ",")).execute();
    AssetManager as = getResources().getAssets();
    ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();
    for(int i = 0;i < _mst_shop_list.size();++i){
      try {
        InputStream is = as.open(_mst_shop_list.get(i).image_path);
        Bitmap bm = BitmapFactory.decodeStream(is);
        imageList.add(bm);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    _adapter = new ShopGridAdapter(this, imageList);
    GridView gridView = (GridView) findViewById(R.id.StoreGridView);
    gridView.setAdapter(_adapter);
    gridView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //_mst_shop_list
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.StoreBgImageView));
    _adapter.release();
  }
}
