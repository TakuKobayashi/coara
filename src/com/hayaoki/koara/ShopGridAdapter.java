package com.hayaoki.koara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
  private ArrayList<Bitmap> _imageList;

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public ShopGridAdapter(Activity activity, ArrayList<Bitmap> imageList) {
    _activity = activity;
    _imageList = imageList;
    _wordList = new ArrayList<String>();
  }

  public void setWordList(ArrayList<String> wordList){
	  _wordList = wordList;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getCount() {
    return _imageList.size();
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
      convertView = _activity.getLayoutInflater().inflate(R.layout.baselistcellview,null);
    }
    ExtraLayout.setBaseImageView(_activity, (ImageView) convertView.findViewById(R.id.GridCellImage), _imageList.get(position));
    if(_wordList.size() > 0){
      TextView word = (TextView) convertView.findViewById(R.id.GridTitleText);
      word.setText(_wordList.get(position).toString());
    }
    return convertView;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void release(){
    for(int i = 0;i < _imageList.size();++i){
      Bitmap bmp = _imageList.get(i);
      bmp.recycle();
      bmp = null;
    }
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}
