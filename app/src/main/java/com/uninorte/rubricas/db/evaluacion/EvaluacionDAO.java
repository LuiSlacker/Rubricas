package com.uninorte.rubricas.db.evaluacion;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface EvaluacionDAO {
    @Query("SELECT * FROM evaluacion")
    List<Evaluacion> getAll();

    @Query("SELECT * FROM evaluacion "
            + "WHERE evaluacion.asignatura_id = (:asignaturaId)")
    List<Evaluacion> getAllForOneAsignatura(int asignaturaId);

    @Query("SELECT * FROM evaluacion "
            + "WHERE evaluacion.uid = (:evaluacionId)")
    Evaluacion getOneById(int evaluacionId);

    @Query("SELECT COUNT(*) from evaluacion")
    int countEvaluaciones();

    @Insert
    void insertAll(Evaluacion... evaluacions);

    @Insert
    long insert(Evaluacion evaluacion);

    @Delete
    void delete(Evaluacion evaluacion);
}

