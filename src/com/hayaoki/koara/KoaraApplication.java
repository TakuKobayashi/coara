package com.hayaoki.koara;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import android.app.Application;
import android.content.SharedPreferences;

public class KoaraApplication extends Application {
  private static int MIGRATION_VERSION = 1;
  private static String MIGRATE_VERSION_KEY = "migration_version";

  @Override
  public void onCreate() {
    super.onCreate();
    Configuration.Builder activeAndroidBuilder = new Configuration.Builder(getBaseContext());
    activeAndroidBuilder.setDatabaseName("koara.db");
    activeAndroidBuilder.setDatabaseVersion(MIGRATION_VERSION);
    ActiveAndroid.initialize(activeAndroidBuilder.create(), true);

    /*
    ImageLoaderConfiguration.Builder imageLoaderBuilder = new ImageLoaderConfiguration.Builder(getApplicationContext());
    imageLoaderBuilder.memoryCache(new LruMemoryCache(8 * 1024 * 1024));
    imageLoaderBuilder.memoryCacheSize(8 * 1024 * 1024);
    ImageLoaderConfiguration config = imageLoaderBuilder.build();
    ImageLoader.getInstance().init(config);
    */
  }

  private void activeAndroidSeed(){
    SharedPreferences sp = Preferences.getCommonPreferences(this);
    int version = sp.getInt(MIGRATE_VERSION_KEY, 0);
    if(version < MIGRATION_VERSION){
      Preferences.saveCommonParam(this, MIGRATE_VERSION_KEY, MIGRATION_VERSION);
    }
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    ActiveAndroid.dispose();
  }
}
