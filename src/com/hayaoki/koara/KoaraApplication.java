package com.hayaoki.koara;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import android.app.Application;

public class KoaraApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Configuration.Builder activeAndroidBuilder = new Configuration.Builder(getBaseContext());
    activeAndroidBuilder.setDatabaseName("koara.db");
    activeAndroidBuilder.setDatabaseVersion(1);
    ActiveAndroid.initialize(activeAndroidBuilder.create(), true);

    /*
    ImageLoaderConfiguration.Builder imageLoaderBuilder = new ImageLoaderConfiguration.Builder(getApplicationContext());
    imageLoaderBuilder.memoryCache(new LruMemoryCache(8 * 1024 * 1024));
    imageLoaderBuilder.memoryCacheSize(8 * 1024 * 1024);
    ImageLoaderConfiguration config = imageLoaderBuilder.build();
    ImageLoader.getInstance().init(config);
    */
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    ActiveAndroid.dispose();
  }
}
