package com.uninorte.rubricas.db.calificacion.elemento;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.elementos.Elemento;
import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface CalificacionElementoDAO {
    @Query("SELECT * FROM calificacion_elemento")
    List<CalificacionElemento> getAll();

    @Query("SELECT * FROM calificacion_elemento "
            + "WHERE calificacion_elemento.calificacion_categoria_id = (:calificacionCategoriaId)")
    List<CalificacionElemento> getAllForOneCalificacionCategoria(int calificacionCategoriaId);

    @Query("SELECT * FROM calificacion_elemento "
            + "WHERE calificacion_elemento.uid = (:calificacionElementoId)")
    CalificacionElemento getOneById(int calificacionElementoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CalificacionElemento... calificacionElementos);

    @Delete
    void delete(CalificacionElemento calificacionElemento);
}

