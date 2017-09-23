package com.uninorte.rubricas.db.calificacion.categoria;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.uninorte.rubricas.db.asignatura.Asignatura;
import com.uninorte.rubricas.db.calificacion.evaluacion.CalificacionEvaluacion;
import com.uninorte.rubricas.db.categoria.Categoria;
import com.uninorte.rubricas.db.estudiante.Estudiante;
import com.uninorte.rubricas.db.evaluacion.Evaluacion;
import com.uninorte.rubricas.db.rubrica.Rubrica;

@Entity(tableName = "calificacion_categoria",
        foreignKeys = {
                @ForeignKey(entity = CalificacionEvaluacion.class,
                        parentColumns = "uid",
                        childColumns = "calificacion_evaluacion_id"),
                @ForeignKey(entity = Categoria.class,
                        parentColumns = "uid",
                        childColumns = "categoria_id")})
public class CalificacionCategoria {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "calificacion_evaluacion_id")
    public int calificacionEvaluacionId;

    @ColumnInfo(name = "categoria_id")
    public int categoriaId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCalificacionEvaluacionId() {
        return calificacionEvaluacionId;
    }

    public void setCalificacionEvaluacionId(int calificacionEvaluacionId) {
        this.calificacionEvaluacionId = calificacionEvaluacionId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}