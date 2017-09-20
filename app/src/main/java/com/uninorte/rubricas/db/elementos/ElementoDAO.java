package com.uninorte.rubricas.db.elementos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface ElementoDAO {
    @Query("SELECT * FROM elemento")
    List<Elemento> getAll();

    @Query("SELECT * FROM elemento "
            + "WHERE elemento.categoria_id = (:categoriaId)")
    List<Elemento> getAllForOneCategoria(int categoriaId);

    @Query("SELECT COUNT(*) from elemento")
    int countElementos();

    @Insert
    void insertAll(Elemento... elementos);

    @Delete
    void delete(Elemento elemento);
}

