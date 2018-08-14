package com.example.joguk.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.joguk.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    public enum SchemaVersion {
        SCHEMA_VERSION_1,
        SCHEMA_VERSION_dev_2,       // drop for development
        SCHEMA_VERSION_ADD_SUSPECT,
        SCHEMA_VERSION_CURRENT;

        public int version() {
            return this.ordinal();
        }
    }

    private static final int VERSION = SchemaVersion.SCHEMA_VERSION_CURRENT.version();
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // UUID Index?
    // DATE Type
    // Boolean Type
    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB가 없으면 onCreate가 실행
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")"
        );

        // Create Index
        db.execSQL("create index " + "ind_uuid" + " on " +
                CrimeTable.NAME + "(" +
                CrimeTable.Cols.UUID + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // version이 다르면 onUpgrade 실행
        if (oldVersion < SchemaVersion.SCHEMA_VERSION_dev_2.version()) {
            db.execSQL("DROP TABLE IF EXISTS " + CrimeTable.NAME);
            onCreate(db);
        } else {
            if (oldVersion <= SchemaVersion.SCHEMA_VERSION_ADD_SUSPECT.version()) {
                db.execSQL("ALTER TABLE "
                        + CrimeTable.NAME + " ADD COULUMN " +
                        CrimeTable.Cols.SUSPECT + ";");
            }
        }
    }
}
