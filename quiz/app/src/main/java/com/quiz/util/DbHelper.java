package com.quiz.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "kidzy";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql = "create table partie(id integer primary key, idUser text, idCategorie text, partieJson text, nbrSuccess integer, nbrTotal integer, lastIndex integer)";
        db.execSQL(sql);
        sql = "create unique index partie_user_categorie on partie(idUser, idCategorie) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        sql = "drop table if exists partie ";
        db.execSQL(sql);
        onCreate(db);
    }
}
