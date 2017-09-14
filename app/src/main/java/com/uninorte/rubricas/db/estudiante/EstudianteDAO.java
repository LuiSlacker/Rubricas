package com.uninorte.rubricas.db.estudiante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uninorte.rubricas.db.DatabaseHandler;
import com.uninorte.rubricas.db.asignatura.AsignaturaEntry;

import java.util.ArrayList;
import java.util.List;


public class EstudianteDAO {

    private DatabaseHandler dbHandler;
    private Context context;
    private SQLiteDatabase database;

    public EstudianteDAO(Context context) {
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

    public long saveNew(EstudianteEntry entry, long asignaturaId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_ESTUDIANTES_NOMBRE, entry.getNombre());

        long estudianteId = database.insert(DatabaseHandler.TABLE_ESTUDIANTES, null, values);
        addAsignaturaFK(estudianteId, asignaturaId);
        return estudianteId;
    }

    /**
     * Returns all Estudiantes from DB
     */
    public List<EstudianteEntry> getAllEstudiantes() {
        List<EstudianteEntry> estudianteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + dbHandler.TABLE_ESTUDIANTES;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EstudianteEntry estudiante = new EstudianteEntry();
                estudiante.setId(Integer.parseInt(cursor.getString(0)));
                estudiante.setNombre(cursor.getString(1));
                // Adding Estudiante to list
                estudianteList.add(estudiante);
            } while (cursor.moveToNext());
        }

        return estudianteList;
    }

    private long addAsignaturaFK(long estudiantes_id, long asignatura_id) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_ASIGNATURAS_ID, asignatura_id);
        values.put(DatabaseHandler.KEY_ESTUDIANTES_ID, estudiantes_id);

        return database.insert(DatabaseHandler.TABLE_ASIGNATURAS_ESTUDIANTES, null, values);
    }

}
