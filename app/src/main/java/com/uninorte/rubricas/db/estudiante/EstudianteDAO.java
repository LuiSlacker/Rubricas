package com.uninorte.rubricas.db.estudiante;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.asignatura.Asignatura;

import java.util.List;

@Dao
public interface EstudianteDAO {

    @Query("SELECT * FROM estudiante")
    List<Estudiante> getAll();

    @Query("SELECT * FROM estudiante "
            + "WHERE estudiante.asignatura_id = (:asignaturaId)")
    List<Estudiante> getAllForOneAsignatura(int asignaturaId);

    @Query("SELECT COUNT(*) from estudiante")
    int countEstudiantes();

    @Insert
    void insertAll(Estudiante... estudiantes);

    @Delete
    void delete(Estudiante estudiante);
}