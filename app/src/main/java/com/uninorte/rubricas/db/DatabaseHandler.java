package com.uninorte.rubricas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.version;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rubricas";

    public static final String TABLE_ASIGNATURAS = "asignaturas";
    public static final String TABLE_ESTUDIANTES = "estudiantes";
    public static final String TABLE_ASIGNATURAS_ESTUDIANTES = "asignaturas_estudiantes";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "created_at";

    // Asignaturas column names
    public static final String KEY_ASIGNATURAS_NOMBRE = "nombre";

    // Estudiantes column names
    public static final String KEY_ESTUDIANTES_NOMBRE = "nombre";

    // asignaturas_estudiantes - column names
    public static final String KEY_ASIGNATURAS_ID = "asignaturas_id";
    public static final String KEY_ESTUDIANTES_ID = "estudiantes_id";

    private static final String CREATE_TABLE_ASIGNATURAS = "CREATE TABLE " + TABLE_ASIGNATURAS + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ASIGNATURAS_NOMBRE + ")";

    private static final String CREATE_TABLE_ESTUDIANTES = "CREATE TABLE " + TABLE_ESTUDIANTES + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ESTUDIANTES_NOMBRE + ")";

    private static final String CREATE_TABLE_ASIGNATURAS_ESTUDIANTES = "CREATE TABLE "
            + TABLE_ASIGNATURAS_ESTUDIANTES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ASIGNATURAS_ID + " INTEGER," + KEY_ESTUDIANTES_ID + " INTEGER)";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isOpen()) {
            db.execSQL(CREATE_TABLE_ASIGNATURAS);
            db.execSQL(CREATE_TABLE_ESTUDIANTES);
            db.execSQL(CREATE_TABLE_ASIGNATURAS_ESTUDIANTES);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASIGNATURAS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASIGNATURAS_ESTUDIANTES);
    }

    public SQLiteDatabase getWritableDB() {
        return super.getWritableDatabase();
    }
}
