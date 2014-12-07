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
  private ArrayList<OverlayImage> _overlayLayer;
  private OverlayImage _targetLayer = null;

  public KoaraView(Context context, AttributeSet attrs) {
    super(context, attrs);
    _overlayLayer = new ArrayList<OverlayImage>();
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
    canvas.drawBitmap(mRenderBaseImage, null, new Rect(0,0,mRenderBaseImage.getWidth(), mRenderBaseImage.getHeight()), null);
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
  }
}