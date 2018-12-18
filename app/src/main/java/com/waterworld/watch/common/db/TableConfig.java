package com.waterworld.watch.common.db;

/**
 * Created by nhuan
 * Time:2018/12/13.
 * table information
 */

public class TableConfig {

    public static final String DATABASE_NAME = "wtwd_childwatch.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SAVE_TIME = "saveTime"; //存入表的事件

    public static class LOGIN {
        public static final String LOGIN_TABLE = "login_table";
        public static final String LOGIN_USERNAME = "login_username";
        public static final String LOGIN_PASSWORD = "login_password";
        public static final String LOGIN_USER_ID = "login_user_id";

        public static final String LOGIN_SQL = "create table if not exist " + LOGIN_TABLE + " ( " +
                "_id integer ," +
                LOGIN_USERNAME + " varchar(100) ," +
                LOGIN_PASSWORD + " varchar(100) ," +
                LOGIN_USER_ID + " integer primary key," +
                SAVE_TIME +" integer )";
    }

}
