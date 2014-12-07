package com.hayaoki.koara;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopGridAdapter extends BaseAdapter {

  private Activity _activity;
  private ArrayList<String> _wordList;
  private List<MstShopModel> _modelList;

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public ShopGridAdapter(Activity activity, List<MstShopModel> modelList) {
    _activity = activity;
    _modelList = modelList;
    _wordList = new ArrayList<String>();
  }

  public void setWordList(ArrayList<String> wordList){
    _wordList = wordList;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getCount() {
    return _modelList.size();
  }

	//---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Object getItem(int position) {
    return position;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public long getItemId(int position) {
    return position;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null){
      convertView = _activity.getLayoutInflater().inflate(R.layout.basegridcellview,null);
    }
    AssetManager as = _activity.getResources().getAssets();
    MstShopModel model = _modelList.get(position);
    try {
      InputStream is = as.open(model.image_path);
      Bitmap bm = BitmapFactory.decodeStream(is);
      ExtraLayout.setBaseImageView(_activity, (ImageView) convertView.findViewById(R.id.GridCellImage), bm);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if(_wordList.size() > 0){
      TextView word = (TextView) convertView.findViewById(R.id.GridTitleText);
      word.setText(_wordList.get(position).toString());
    }
    return convertView;
  }

  public void remove(int position){
    _modelList.remove(position);
    this.notifyDataSetChanged();
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void release(){
    /*
    for(int i = 0;i < _modelList.size();++i){
      Bitmap bmp = _modelList.get(i);
      bmp.recycle();
      bmp = null;
    }
    */
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}
