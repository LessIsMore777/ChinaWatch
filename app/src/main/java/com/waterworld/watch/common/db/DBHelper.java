package com.waterworld.watch.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.waterworld.watch.common.application.MyApplication;

/**
 * Created by nhuan
 * Time:2018/12/13.
 * DataBase assistant
 */

public class DBHelper extends SQLiteOpenHelper{

    private static DBHelper instance;

    /**
     * Double lock for check
     */
    public static synchronized DBHelper getInstance(){
        if(instance == null){
            synchronized (DBHelper.class){
                if(instance == null){
                    instance = new DBHelper(MyApplication.getInstance());
                }
            }
        }
        return instance;
    }

    public DBHelper(Context context){
        super(context,TableConfig.DATABASE_NAME,null,TableConfig.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(TableConfig.LOGIN.LOGIN_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
