package com.uninorte.rubricas.db.asignatura;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uninorte.rubricas.db.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO {

    private DatabaseHandler dbHandler;
    private Context context;
    private SQLiteDatabase database;

    public AsignaturaDAO(Context context) {
        this.context = context;
        this.dbHandler = new DatabaseHandler(context);

        open();
    }

    public void open() {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public long saveNew(AsignaturaEntry entry) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_ASIGNATURAS_NOMBRE, entry.getNombre());

        return database.insert(DatabaseHandler.TABLE_ASIGNATURAS, null, values);
    }

    public AsignaturaEntry getAignatura() {
        /*SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;*/
        return null;
    }

    /**
     * Returns all Asignaturas from DB
     */
    public List<AsignaturaEntry> getAllAsignaturas() {
        List<AsignaturaEntry> asignaturasList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + dbHandler.TABLE_ASIGNATURAS;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AsignaturaEntry asignatura = new AsignaturaEntry();
                asignatura.setId(Integer.parseInt(cursor.getString(0)));
                asignatura.setNombre(cursor.getString(1));
                // Adding Asignatura to list
                asignaturasList.add(asignatura);
            } while (cursor.moveToNext());
        }

        return asignaturasList;
    }
}
