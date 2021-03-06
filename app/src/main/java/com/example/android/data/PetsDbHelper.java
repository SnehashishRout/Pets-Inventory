
package com.example.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PetsDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pets.db";

    public PetsDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String SQL_CREATE_ENTRIES = "CREATE TABLE "+ PetContract.PetEntry.TABLE_NAME+ " (" + PetContract.PetEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + PetContract.PetEntry.COLUMN_PET_NAME+" TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_BREED+" TEXT, "+ PetContract.PetEntry.COLUMN_PET_GENDER+" INTEGER, " + PetContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0" + ");" ;
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
