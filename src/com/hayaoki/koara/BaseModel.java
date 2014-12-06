package com.hayaoki.koara;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;

//ActiveRecordっぽいものを作ってみました
abstract public class BaseModel extends Model{

  @Column(name = "item_id", notNull = true)
  public long id;
  @Column(name = "updated_at", notNull = true)
  public long updated_at = System.currentTimeMillis();
  @Column(name = "created_at", notNull = true)
  public long created_at = System.currentTimeMillis();

  private static <T extends BaseModel> T find_or_initialize(Class<T> klazz, ArrayList<String> list){
    T record = new Select().from(klazz).where(ApplicationHelper.join(list, " AND ")).executeSingle();
    if(record == null){
      try {
        return klazz.newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
        return null;
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    }else{
      return record;
    }
  }

  public static <T extends BaseModel> T find_or_initialize_by(Class<T> klazz, Bundle hash){
    Set<String> keys = hash.keySet();
    ArrayList<String> andList = new ArrayList<String>();
    for (String key : keys) {
      andList.add(key + "=" + hash.get(key).toString());
    }
    return find_or_initialize(klazz, andList);
  }

  public static <T extends BaseModel> T find_or_initialize_by(Class<T> klazz, Map hash){
    Set keys = hash.keySet();
    ArrayList<String> andList = new ArrayList<String>();
    for(Object key : keys) {
      andList.add(key.toString() + "=" + hash.get(key).toString());
    }
    return find_or_initialize(klazz, andList);
  }

  public static <T extends BaseModel> T find_or_create_by(Class<T> klazz, Bundle hash){
    Set<String> keys = hash.keySet();
    ArrayList<String> andList = new ArrayList<String>();
    for (String key : keys) {
      andList.add(key + "=" + hash.get(key).toString());
    }
    return find_or_initialize(klazz, andList);
  }

  //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}