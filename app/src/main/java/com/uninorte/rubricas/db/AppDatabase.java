package com.uninorte.rubricas.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.asignatura.AsignaturaDAO;
import com.uninorte.rubricas.db.estudiante.Estudiante;
import com.uninorte.rubricas.db.estudiante.EstudianteDAO;
import com.uninorte.rubricas.db.evaluacion.Evaluacion;
import com.uninorte.rubricas.db.evaluacion.EvaluacionDAO;
import com.uninorte.rubricas.db.rubrica.Rubrica;
import com.uninorte.rubricas.db.rubrica.RubricaDAO;

@Database(entities = {
        Asignatura.class,
        Estudiante.class,
        Evaluacion.class,
        Rubrica.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AsignaturaDAO asignaturaDao();
    public abstract EstudianteDAO estudianteDao();
    public abstract EvaluacionDAO evaluacionDao();
    public abstract RubricaDAO rubricaDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}