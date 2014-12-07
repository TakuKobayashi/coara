package com.hayaoki.koara;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
      RectF dst = new RectF(0, 0, image.getWidth(), image.getHeight());
      float width = dst.width();
      float height = dst.height();
      dst.right = 873 + (width / 2);
      dst.bottom = 1127.7f + (height / 2);
      dst.left = 873 - (width / 2);
      dst.top = 1127.7f  - (height / 2);
      bitmapCanvas.drawBitmap(image, new Rect(0, 0, image.getWidth(), image.getHeight()), dst, null);
    }
    canvas.drawBitmap(mRenderBaseImage, null, new Rect(0,0,mRenderBaseImage.getWidth(), mRenderBaseImage.getHeight()), null);
    this.invalidate();
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
    for(int i = 0; i< _imageList.size();++i){
      _imageList.get(i).recycle();
    }
    _imageList.clear();
  }
}