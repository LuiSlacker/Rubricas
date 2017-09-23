package com.uninorte.rubricas.db.calificacion.categoria;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface CalificacionCategoriaDAO {
    @Query("SELECT * FROM calificacion_categoria")
    List<CalificacionCategoria> getAll();

    @Query("SELECT * FROM calificacion_categoria "
            + "WHERE calificacion_categoria.calificacion_evaluacion_id = (:calificacionEvaluacionId)")
    List<CalificacionCategoria> getAllForOneCalificacionEvaluacion(int calificacionEvaluacionId);

    @Insert
    void insertAll(CalificacionCategoria... calificacionCategorias);

    @Insert
    long insert(CalificacionCategoria calificacionCategoria);

    @Delete
    void delete(CalificacionCategoria calificacionCategoria);
}

