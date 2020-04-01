package com.example.myapplication.database;

import android.provider.BaseColumns;

public class User {
    private User(){

    }

    public static final class UserDetails implements BaseColumns{
        public static final String TABLE_NAME="user";
        public static final String COL_ID="id";
        public static final String COL_NAME="name";
        public static final String COL_EMAIL="email";
        public static final String COL_PHONE="phone";
        public static final String COL_PASSWORD="password";
    }
}
