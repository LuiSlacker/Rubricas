package com.uninorte.rubricas.db.calificacion.evaluacion;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.calificacion.elemento.CalificacionElemento;

import java.util.List;

@Dao
public interface CalificacionEvaluacionDAO {
    @Query("SELECT * FROM calificacion_evaluacion")
    List<CalificacionEvaluacion> getAll();

    @Query("SELECT * FROM calificacion_evaluacion "
            + "WHERE calificacion_evaluacion.evaluacion_id = (:evaluacionId)")
    List<CalificacionEvaluacion> getAllForOneEvaluacion(int evaluacionId);

    @Query("SELECT * FROM calificacion_evaluacion "
                  + "WHERE calificacion_evaluacion.uid = (:calificacionEvaluacionId)")
    CalificacionEvaluacion getOneById(int calificacionEvaluacionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CalificacionEvaluacion... calificacionEvaluacions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CalificacionEvaluacion calificacionEvaluacion);

    @Delete
    void delete(CalificacionEvaluacion calificacionEvaluacion);
}

