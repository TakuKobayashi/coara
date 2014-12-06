package com.hayaoki.coara;

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

public class MenuListAdapter extends BaseAdapter {

  private Activity _activity;
  private List _wordList;
  private ArrayList<Bitmap> _imageResList;

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public MenuListAdapter(Activity activity, List wordList) {
    _activity = activity;
    _wordList = wordList;
  }

  public void setImageList(ArrayList<Bitmap> imageList){
    _imageResList = imageList;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getCount() {
    return _wordList.size();
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
    ExtraLayout.setBaseImageView(_activity, (ImageView) convertView.findViewById(R.id.ListCellBackgroundImage), _imageResList.get(position));
    TextView word = (TextView) convertView.findViewById(R.id.ListTitleText);
    word.setText(_wordList.get(position).toString());
    return convertView;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void release(){
    for(int i = 0;i < _imageResList.size();++i){
      Bitmap bmp = _imageResList.get(i);
      bmp.recycle();
      bmp = null;
    }
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}
