package com.uninorte.rubricas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rubricas";

    public static final String TABLE_ASIGNATURAS = "asignaturas";
    public static final String TABLE_ESTUDIANTES = "estudiantes";
    public static final String TABLE_ASIGNATURAS_ESTUDIANTES = "asignaturas_estudiantes";
    public static final String TABLE_RUBRICAS = "rubricas";
    public static final String TABLE_CATEGORIAS = "categorias";
    public static final String TABLE_SUBCATEGORIAS = "subcategorias";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "created_at";

    // Asignaturas column names
    public static final String KEY_ASIGNATURAS_NOMBRE = "nombre";
    public static final String KEY_ASIGNATURAS_CATEGORY = "categoria";
    public static final String KEY_ASIGNATURAS_CATEGORY_WEIGHT = "peso_categoria";

    // Estudiantes column names
    public static final String KEY_ESTUDIANTES_NOMBRE = "nombre";

    // asignaturas_estudiantes - column names
    public static final String KEY_ASIGNATURAS_ID = "asignaturas_id";
    public static final String KEY_ESTUDIANTES_ID = "estudiantes_id";

    // Rubricas column names
    public static final String KEY_RUBRICAS_NOMBRE =  "nombre";

    // Categorias column names
    public static final String KEY_CATEGORIA_NOMBRE = "nombre";
    public static final String KEY_CATEGORIA_RUBRICA_ID = "id_rubrica";

    // Subcategorias column names
    public static final String KEY_SUBCATEGORIA_CATEGORIA_ID = "id__categoria";
    public static final String KEY_SUBCATEGORY_NAME = "nombre";
    public static final String KEY_SUBCATEGORY_WEIGHT = "peso_subcategoria";
    public static final String KEY_SUBCATEGORY_LINEAMIENTO1 = "lineamiento_1";
    public static final String KEY_SUBCATEGORY_LINEAMIENTO2 = "lineamiento_2";
    public static final String KEY_SUBCATEGORY_LINEAMIENTO3 = "lineamiento_3";
    public static final String KEY_SUBCATEGORY_LINEAMIENTO4 = "lineamiento_4";

    private static final String CREATE_TABLE_ASIGNATURAS = "CREATE TABLE " + TABLE_ASIGNATURAS + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +KEY_ASIGNATURAS_NOMBRE + "STRING"+KEY_ASIGNATURAS_CATEGORY+" INTEGER,"+
            KEY_ASIGNATURAS_CATEGORY_WEIGHT+"DOUBLE)";

    private static final String CREATE_TABLE_ESTUDIANTES = "CREATE TABLE " + TABLE_ESTUDIANTES + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ESTUDIANTES_NOMBRE + ")";

    private static final String CREATE_TABLE_ASIGNATURAS_ESTUDIANTES = "CREATE TABLE "
            + TABLE_ASIGNATURAS_ESTUDIANTES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ASIGNATURAS_ID + " INTEGER," + KEY_ESTUDIANTES_ID + " INTEGER)";

    private static final String CREATE_TABLE_RUBRICAS = "CREATE TABLE "+TABLE_RUBRICAS+"("
            +KEY_ID+" INTEGER PRIMARY KEY," +KEY_RUBRICAS_NOMBRE+" STRING)";

    private static final String CREATE_TABLE_CATEGORIAS = "CREATE TABLE "+TABLE_CATEGORIAS+"("+
            KEY_ID+" INTEGER PRIMARY KEY,"+ KEY_CATEGORIA_NOMBRE + " STRING,"+ KEY_CATEGORIA_RUBRICA_ID +" INTEGER)";

    private static final String CREATE_TABLE_SUBCATEGORIAS = "CREATE TABLE "+TABLE_SUBCATEGORIAS+"("+
            KEY_ID+" INTEGER PRIMARY KEY,"+KEY_SUBCATEGORY_NAME+" STRING,"+KEY_SUBCATEGORY_WEIGHT+" DOUBLE,"+
            KEY_SUBCATEGORIA_CATEGORIA_ID +" INTEGER,"+KEY_SUBCATEGORY_LINEAMIENTO1+" STRING,"+KEY_SUBCATEGORY_LINEAMIENTO2
            +" STRING,"+KEY_SUBCATEGORY_LINEAMIENTO3+" STRING,"+KEY_SUBCATEGORY_LINEAMIENTO4+" STRING)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isOpen()) {
            db.execSQL(CREATE_TABLE_ASIGNATURAS);
            db.execSQL(CREATE_TABLE_ESTUDIANTES);
            db.execSQL(CREATE_TABLE_ASIGNATURAS_ESTUDIANTES);
            db.execSQL(CREATE_TABLE_RUBRICAS);
            db.execSQL(CREATE_TABLE_CATEGORIAS);
            db.execSQL(CREATE_TABLE_SUBCATEGORIAS);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASIGNATURAS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASIGNATURAS_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS" + CREATE_TABLE_RUBRICAS);
        db.execSQL("DROP TABLE IF EXISTS" + CREATE_TABLE_CATEGORIAS);
        db.execSQL("DROP TABLE IF EXISTS" + CREATE_TABLE_SUBCATEGORIAS);
    }

    public SQLiteDatabase getWritableDB() {
        return super.getWritableDatabase();
    }
}
