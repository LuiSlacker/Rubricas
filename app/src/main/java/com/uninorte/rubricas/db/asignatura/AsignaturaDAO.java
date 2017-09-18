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

    /*@Query("SELECT * FROM asignatura where first_name LIKE  :firstName AND last_name LIKE :lastName")
    Asignatura findByName(String firstName, String lastName);*/

    @Query("SELECT COUNT(*) from asignatura")
    int countUsers();

    @Insert
    void insertAll(Asignatura... asignaturas);

    @Delete
    void delete(Asignatura asignatura);
}
