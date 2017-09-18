package com.uninorte.rubricas.db.estudiante;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ForeignKey;

import com.uninorte.rubricas.db.asignatura.Asignatura;

import static android.R.attr.id;

@Entity(tableName = "estudiante", foreignKeys = @ForeignKey(entity = Asignatura.class,
        parentColumns = "uid",
        childColumns = "asignatura_id"))
public class Estudiante {

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

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
