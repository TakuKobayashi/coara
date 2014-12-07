package com.hayaoki.koara;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.widget.RelativeLayout;

public class KoaraView extends View {

  private Bitmap mClearImage = null;
  private Bitmap mRenderBaseImage = null;
  private ArrayList<Bitmap> _imageList;
  private ArrayList<OverlayImage> _overlayLayer;
  private OverlayImage _targetLayer = null;
  private int frameNum = 0;
  private RectF _dst;
  private boolean hit = false;

  public KoaraView(Context context, AttributeSet attrs) {
    super(context, attrs);
    _overlayLayer = new ArrayList<OverlayImage>();
    _imageList = new ArrayList<Bitmap>();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if(mRenderBaseImage != null){
      mRenderBaseImage.recycle();
      mRenderBaseImage = null;
    }
    mRenderBaseImage = mClearImage.copy(Bitmap.Config.ARGB_8888, true);
    Canvas bitmapCanvas = new Canvas(mRenderBaseImage);
    for(int i = 0;i < _overlayLayer.size();i++){
      _overlayLayer.get(i).render(bitmapCanvas);
    }
    if(_imageList.size() > 0){
      if(frameNum >= _imageList.size()){
        frameNum = 0;
      }
      Bitmap image = _imageList.get(frameNum);
      frameNum = frameNum + 1;
      _dst = new RectF(0, 0, image.getWidth(), image.getHeight());
      float width = _dst.width();
      float height = _dst.height();
      _dst.right = 873 + (width / 2);
      _dst.bottom = 1127.7f + (height / 2);
      _dst.left = 873 - (width / 2);
      _dst.top = 1127.7f  - (height / 2);
      bitmapCanvas.drawBitmap(image, new Rect(0, 0, image.getWidth(), image.getHeight()), _dst, null);
    }
    canvas.drawBitmap(mRenderBaseImage, null, new Rect(0,0,mRenderBaseImage.getWidth(), mRenderBaseImage.getHeight()), null);
    this.invalidate();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if(_dst.contains(event.getX(), event.getY()) && !hit){
      AssetManager as = this.getResources().getAssets();
      ArrayList<Bitmap> list = new ArrayList<Bitmap>();
      try {
        for(int i = 1;i <= 7;i++){
          InputStream is = as.open("koara/extended/yawn" + i + ".png");
          Bitmap bm = BitmapFactory.decodeStream(is);
          list.add(bm);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      releaseImageSet();
      setAnimationImageList(list);
      hit = true;
    }else if(hit){
      hit = false;
      AssetManager as = this.getResources().getAssets();
      ArrayList<Bitmap> list = new ArrayList<Bitmap>();
      try {
        for(int i = 1;i <= 21;i++){
          InputStream is = as.open("koara/swing/neck" + i + ".png");
          Bitmap bm = BitmapFactory.decodeStream(is);
          list.add(bm);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      releaseImageSet();
      setAnimationImageList(list);
    };
    this.invalidate();
    return true;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if(mClearImage != null){
      mClearImage.recycle();
      mClearImage = null;
    }
    mClearImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    this.invalidate();
  }

  public void addImage(Bitmap image){
    OverlayImage oImage = new OverlayImage(image);
    _overlayLayer.add(oImage);
    _targetLayer = oImage;
    this.invalidate();
  }

  public void setAnimationImageList(ArrayList<Bitmap> images){
    _imageList.addAll(images);
    this.invalidate();
  }

  public Bitmap getOverrideImage(){
    return mRenderBaseImage;
  }

  public void releaseImage(){
    for(int i = 0;i < _overlayLayer.size();i++){
      _overlayLayer.get(i).release();
    }
    _overlayLayer.clear();
    if(_targetLayer != null){
      _targetLayer.release();
    }
    if(mClearImage != null){
      mClearImage.recycle();
      mClearImage = null;
    }
    if(mRenderBaseImage != null){
      mRenderBaseImage.recycle();
      mRenderBaseImage = null;
    }
    releaseImageSet();
  }

  public void releaseImageSet(){
    for(int i = 0; i< _imageList.size();++i){
      _imageList.get(i).recycle();
    }
    _imageList.clear();
  }
}