package com.uninorte.rubricas.db.asignatura;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "asignatura")
public class Asignatura {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nombre")
    private String nombre;

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
}
