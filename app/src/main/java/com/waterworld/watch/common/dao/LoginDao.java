package com.waterworld.watch.common.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.waterworld.watch.common.db.DBHelper;
import com.waterworld.watch.common.db.TableConfig;
import com.waterworld.watch.common.interfaces.ILoginDao;
import com.waterworld.watch.login.bean.LoginBean;

/**
 * Created by nhuan
 * Time:2018/12/13.
 */

public class LoginDao implements ILoginDao {

    private SQLiteDatabase db;

    public LoginDao() {
        super();
        db = DBHelper.getInstance().getWritableDatabase();
    }

    @Override
    public void insert(Object o) {
        if (o instanceof LoginBean) {
           LoginBean loginBean = (LoginBean) o ;
           if(db != null) {
               ContentValues contentValues = new ContentValues();
               contentValues.put(TableConfig.LOGIN.LOGIN_USERNAME,loginBean.getUsername());
               contentValues.put(TableConfig.LOGIN.LOGIN_PASSWORD,loginBean.getPassword());
               contentValues.put(TableConfig.LOGIN.LOGIN_USER_ID,loginBean.getUser_id());
               contentValues.put(TableConfig.SAVE_TIME,System.currentTimeMillis()/1000);
               db.replace(TableConfig.LOGIN.LOGIN_TABLE,null,contentValues);
           }
        }
    }

    @Override
    public void upDate(Object o) {
        if (o instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) o;
            if(db != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TableConfig.LOGIN.LOGIN_USERNAME,loginBean.getUsername());
                contentValues.put(TableConfig.LOGIN.LOGIN_PASSWORD,loginBean.getPassword());
                contentValues.put(TableConfig.LOGIN.LOGIN_USER_ID,loginBean.getUser_id());
                contentValues.put(TableConfig.SAVE_TIME,System.currentTimeMillis()/1000);
                db.replace(TableConfig.LOGIN.LOGIN_TABLE,null,contentValues);
            }
        }
    }

    @Override
    public Object getNewsLogin()  {
        if(db != null) {
            LoginBean loginBean = null;
            Cursor cursor = null;
            try {
                cursor = db.query(TableConfig.LOGIN.LOGIN_TABLE,null,null,null,null,null,TableConfig.SAVE_TIME + " desc","1");
                if(cursor != null  && cursor.getCount() > 0){
                    while (cursor.moveToNext()){
                        int username_Index = cursor.getColumnIndex(TableConfig.LOGIN.LOGIN_USERNAME);
                        String username = cursor.getString(username_Index);

                        int password_Index = cursor.getColumnIndex(TableConfig.LOGIN.LOGIN_PASSWORD);
                        String password = cursor.getString(password_Index);

                        int userId_Index = cursor.getColumnIndex(TableConfig.LOGIN.LOGIN_USER_ID);
                        int userId = cursor.getInt(userId_Index);

                        loginBean = LoginBean.getInstance();
                        loginBean.setUsername(username);
                        loginBean.setPassword(password);
                        loginBean.setUser_id(userId);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
            return loginBean;
        }else {
            return null;
        }
    }

    @Override
    public void delPassword(int id) {
        if (db != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableConfig.LOGIN.LOGIN_PASSWORD,"");
            contentValues.put(TableConfig.SAVE_TIME, System.currentTimeMillis() / 1000);
            db.update(TableConfig.LOGIN.LOGIN_TABLE, contentValues, TableConfig.LOGIN.LOGIN_USER_ID + " = ?", new String[]{id+""});
        }
    }
}
