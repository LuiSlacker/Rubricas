package com.uninorte.rubricas.db.asignatura;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AsignaturaDAO {

    @Query("SELECT * FROM asignatura")
    List<Asignatura> getAll();

    @Query("SELECT COUNT(*) from asignatura")
    int countAsignaturas();

    @Insert
    void insertAll(Asignatura... asignaturas);

    @Delete
    void delete(Asignatura asignatura);
}
