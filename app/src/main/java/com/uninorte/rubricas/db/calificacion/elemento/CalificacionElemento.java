package com.uninorte.rubricas.db.calificacion.elemento;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.calificacion.categoria.CalificacionCategoria;
import com.uninorte.rubricas.db.elementos.Elemento;
import com.uninorte.rubricas.db.rubrica.Rubrica;

@Entity(tableName = "calificacion_elemento",
        foreignKeys = {
                @ForeignKey(entity = CalificacionCategoria.class,
                        parentColumns = "uid",
                        childColumns = "calificacion_categoria_id"),
                @ForeignKey(entity = Elemento.class,
                        parentColumns = "uid",
                        childColumns = "elemento_id")})
public class CalificacionElemento {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nivel")
    private int nivel;

    @ColumnInfo(name = "calificacion_categoria_id")
    public int calificacionCategoriaId;

    @ColumnInfo(name = "elemento_id")
    public int elementoId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getCalificacionCategoriaId() {
        return calificacionCategoriaId;
    }

    public void setCalificacionCategoriaId(int calificacionCategoriaId) {
        this.calificacionCategoriaId = calificacionCategoriaId;
    }

    public int getElementoId() {
        return elementoId;
    }

    public void setElementoId(int elementoId) {
        this.elementoId = elementoId;
    }
}