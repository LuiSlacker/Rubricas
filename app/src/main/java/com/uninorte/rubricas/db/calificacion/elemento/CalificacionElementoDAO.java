package com.uninorte.rubricas.db.calificacion.elemento;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface CalificacionElementoDAO {
    @Query("SELECT * FROM evaluacion")
    List<CalificacionElemento> getAll();

    @Query("SELECT * FROM evaluacion "
            + "WHERE evaluacion.asignatura_id = (:asignaturaId)")
    List<Estudiante> getAllForOneAsignatura(int asignaturaId);

    @Query("SELECT COUNT(*) from evaluacion")
    int countEvaluaciones();

    @Insert
    void insertAll(CalificacionElemento... notases);

    @Delete
    void delete(CalificacionElemento calificacionElemento);
}

