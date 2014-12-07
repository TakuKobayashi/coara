package com.hayaoki.koara;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class OverlayImage{

  protected Bitmap _image;
  protected Paint _paint;
  //描画させる範囲
  protected Rect _srcRect;
  //描画する座標と幅、高さの指定
  protected RectF _dstRect;

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public OverlayImage(Bitmap image) {
    _image = image;
    _paint = new Paint();
    _srcRect = new Rect(0,0,image.getWidth(), image.getHeight());
    _dstRect = new RectF(0,0,image.getWidth(), image.getHeight());
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void resetImage(Bitmap image){
    _srcRect = new Rect(0,0,_image.getWidth(), _image.getHeight());
    _dstRect = new RectF(0,0,_image.getWidth(), _image.getHeight());
    this.release();
    _image = image;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Bitmap getImage(){
    return _image;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  //描画する時のx座標、y座標をfloat型でset
  public void setPosition(float x, float y){
    float width = _dstRect.width();
    float height = _dstRect.height();
    _dstRect.right = x + (width / 2);
    _dstRect.bottom = y + (height / 2);
    _dstRect.left = x - (width / 2);
    _dstRect.top = y  - (height / 2);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setPosition(int x, int y){
    _dstRect.right = x + (_image.getWidth() / 2);
    _dstRect.bottom = y + (_image.getHeight() / 2);
    _dstRect.left = x - (_image.getWidth() / 2);
    _dstRect.top = y  - (_image.getHeight() / 2);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public Point getPosition(){
    return new Point((int)_dstRect.left,(int)_dstRect.right);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public PointF getPositionF(){
    return new PointF(_dstRect.left,_dstRect.right);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setScale(float sx, float sy){
    float right = _dstRect.left + ((float)_image.getWidth() * sx);
    float bottom = _dstRect.top + ((float)_image.getHeight() * sx);
    _dstRect.right = right;
    _dstRect.bottom = bottom;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public boolean contains(float x, float y){
    return _dstRect.contains(x, y);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getImageWidth(){
    return (int) _dstRect.width();
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getImageHeight(){
    return (int) _dstRect.height();
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setPaint(Paint paint){
    _paint = paint;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  //画像を切りぬいて表示
  public void setClip(int x, int y, int width, int height){
    int right = x + width;
    int bottom = y + height;
    //画像の幅、高さ以上になると描画する時にエラーになる
    if(_image.getWidth() < right){
      right = _image.getWidth();
    }
    if(_image.getHeight() < bottom){
      bottom = _image.getHeight();
    }
    _srcRect.left = x;
    _srcRect.top = y;
    _srcRect.right = right;
    _srcRect.bottom = bottom;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setClip(int width, int height){
    int x = _srcRect.left;
    int y = _srcRect.top;
    int right = x + width;
    int bottom = y + height;
    //画像の幅、高さ以上になると描画する時にエラーになる
    if(_image.getWidth() < right){
      right = _image.getWidth();
    }
    if(_image.getHeight() < bottom){
      bottom = _image.getHeight();
    }
    _srcRect.left = x;
    _srcRect.top = y;
    _srcRect.right = right;
    _srcRect.bottom = bottom;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void render(Canvas canvas){
    canvas.drawBitmap(_image, _srcRect, _dstRect, _paint);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void release(){
    if(_image != null){
      _image.recycle();
      _image = null;
    }
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}