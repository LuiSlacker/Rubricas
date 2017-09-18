package com.uninorte.rubricas.db.rubrica;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.estudiante.Estudiante;

import java.util.List;

@Dao
public interface RubricaDAO {

    @Query("SELECT * FROM rubrica")
    List<Rubrica> getAll();

        /*@Query("SELECT * FROM asignatura where first_name LIKE  :firstName AND last_name LIKE :lastName")
        Asignatura findByName(String firstName, String lastName);*/

    @Query("SELECT COUNT(*) from rubrica")
        int countUsers();

    @Insert
        void insertAll(Rubrica... rubricas);

    @Delete
    void delete(Rubrica rubrica);
}
