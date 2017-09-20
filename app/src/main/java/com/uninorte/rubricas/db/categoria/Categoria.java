package com.uninorte.rubricas.db.categoria;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.rubrica.Rubrica;

@Entity(tableName = "categoria",
        foreignKeys = @ForeignKey(entity = Rubrica.class,
                        parentColumns = "uid",
                        childColumns = "rubrica_id"))
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "peso")
    public int peso;

    @ColumnInfo(name = "rubrica_id")
    public int rubricaId;

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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getRubricaId() {
        return rubricaId;
    }

    public void setRubricaId(int rubricaId) {
        this.rubricaId = rubricaId;
    }
}