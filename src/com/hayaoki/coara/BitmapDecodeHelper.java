//  Created by 拓 小林
//  Copyright (c) 2013年 TakuKobayashi All rights reserved.

package com.hayaoki.coara;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class BitmapDecodeHelper {

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static Bitmap getBitmap(Activity act, Integer res) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    Rect displaySize = ExtraLayout.getDisplaySize(act);
    Rect imageSize = getImageSize(act, res);
    //画面に対する画像の縮小率と希望の縮小率の大きい方の値を用いる
    int nInSampleSize = (int)Math.floor(Math.max(imageSize.width() / displaySize.width(), imageSize.height() / displaySize.height()));
    if(nInSampleSize < 1) nInSampleSize = 1;
    options.inSampleSize = nInSampleSize;
    Bitmap bitmap = BitmapFactory.decodeResource(act.getResources(), res, options);
    Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    bitmap.recycle();
    bitmap = null;
    return mutableBitmap;
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static Rect getImageSize(Context context, Integer resId) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    options.inScaled = false;
    BitmapFactory.decodeResource(context.getResources(), resId, options);
    return new Rect(0, 0, options.outWidth, options.outHeight);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}
