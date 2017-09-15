package com.uninorte.rubricas.db.estudiante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uninorte.rubricas.db.DatabaseHandler;
import com.uninorte.rubricas.db.asignatura.AsignaturaEntry;

import java.util.ArrayList;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.uninorte.rubricas.db.DatabaseHandler.KEY_ID;


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


    /**
     * Fetches all estudiantes for one asignatura
     */
    public List<EstudianteEntry> getAllEstudiantesForOneAsignatura(long asignaturaId) {
        List<EstudianteEntry> estudianteList = new ArrayList<>();

        String selectQuery = "SELECT  te." + DatabaseHandler.KEY_ID + ",te." + DatabaseHandler.KEY_ESTUDIANTES_NOMBRE + " FROM " + DatabaseHandler.TABLE_ASIGNATURAS + " ta, "
                + DatabaseHandler.TABLE_ESTUDIANTES + " te, " + DatabaseHandler.TABLE_ASIGNATURAS_ESTUDIANTES + " tae WHERE ta."
                + DatabaseHandler.KEY_ID + " = '" + asignaturaId + "'" + " AND ta." + DatabaseHandler.KEY_ID
                + " = " + "tae." + DatabaseHandler.KEY_ASIGNATURAS_ID + " AND te." + DatabaseHandler.KEY_ID + " = "
                + "tae." + DatabaseHandler.KEY_ESTUDIANTES_ID;

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
