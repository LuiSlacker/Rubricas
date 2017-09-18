package com.uninorte.rubricas.db.evaluacion;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.uninorte.rubricas.db.asignatura.Asignatura;

@Entity(tableName = "evaluacion",
        foreignKeys = @ForeignKey(entity = Asignatura.class,
            parentColumns = "uid",
            childColumns = "asignatura_id"))
public class Evaluacion {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nombre")
    private String nombre;

}