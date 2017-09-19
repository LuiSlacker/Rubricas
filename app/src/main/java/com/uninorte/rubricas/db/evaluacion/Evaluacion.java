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

    @ColumnInfo(name = "asignatura_id")
    public int asignaturaId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }
}