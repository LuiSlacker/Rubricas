package com.uninorte.rubricas.db.categoria;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface CategoriaDAO {
    @Query("SELECT * FROM categoria")
    List<Categoria> getAll();

    @Query("SELECT * FROM categoria "
            + "WHERE categoria.rubrica_id = (:rubricaId)")
    List<Categoria> getAllForOneRubrica(int rubricaId);

    @Query("SELECT COUNT(*) from categoria")
    int countRubricas();

    @Insert
    void insertAll(Categoria... categorias);

    @Delete
    void delete(Categoria categoria);
}

