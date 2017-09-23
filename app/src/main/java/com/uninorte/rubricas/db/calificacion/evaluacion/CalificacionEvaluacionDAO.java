package com.uninorte.rubricas.db.calificacion.evaluacion;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CalificacionEvaluacionDAO {
    @Query("SELECT * FROM calificacion_evaluacion")
    List<CalificacionEvaluacion> getAll();

    @Query("SELECT * FROM calificacion_evaluacion "
            + "WHERE calificacion_evaluacion.evaluacion_id = (:evaluacionId)")
    List<CalificacionEvaluacion> getAllForOneEvaluacion(int evaluacionId);

    @Insert
    void insertAll(CalificacionEvaluacion... calificacionEvaluacions);

    @Delete
    void delete(CalificacionEvaluacion calificacionEvaluacion);
}

