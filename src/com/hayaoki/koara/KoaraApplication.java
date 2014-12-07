package com.hayaoki.koara;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

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

    activeAndroidSeed();
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
      AssetManager assetManager = this.getAssets();
      try {
        InputStream is = assetManager.open("seeds/mst_shop.json");
        String json = ApplicationHelper.loadText(is);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MstShopModel>>(){}.getType();
        List<MstShopModel> list = gson.fromJson(json, listType);
        for(int i = 0;i < list.size();++i){
          list.get(i).save();
        }

        is = assetManager.open("seeds/mst_achievement.json");
        json = ApplicationHelper.loadText(is);
        gson = new Gson();
        listType = new TypeToken<List<MstAchievementModel>>(){}.getType();
        List<MstAchievementModel> acList = gson.fromJson(json, listType);
        for(int i = 0;i < acList.size();++i){
          acList.get(i).save();
        }

        is = assetManager.open("seeds/mst_level.json");
        json = ApplicationHelper.loadText(is);
        gson = new Gson();
        listType = new TypeToken<List<MstLevelModel>>(){}.getType();
        List<MstLevelModel> lList = gson.fromJson(json, listType);
        for(int i = 0;i < lList.size();++i){
          lList.get(i).save();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      Preferences.saveCommonParam(this, MIGRATE_VERSION_KEY, MIGRATION_VERSION);
    }
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    ActiveAndroid.dispose();
  }
}
