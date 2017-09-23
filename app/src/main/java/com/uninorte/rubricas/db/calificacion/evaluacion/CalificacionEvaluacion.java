package com.uninorte.rubricas.db.calificacion.evaluacion;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.uninorte.rubricas.db.estudiante.Estudiante;
import com.uninorte.rubricas.db.evaluacion.Evaluacion;

@Entity(tableName = "calificacion_evaluacion",
        foreignKeys = {
                @ForeignKey(entity = Evaluacion.class,
                        parentColumns = "uid",
                        childColumns = "evaluacion_id"),
                @ForeignKey(entity = Estudiante.class,
                        parentColumns = "uid",
                        childColumns = "estudiante_id")})
public class CalificacionEvaluacion {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nota")
    private float nota;

    @ColumnInfo(name = "evaluacion_id")
    public int evaluacionId;

    @ColumnInfo(name = "estudiante_id")
    public int estudianteId;

    @ColumnInfo(name = "estudiante_nombre")
    public String estudianteNombre;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(int evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getEstudianteNombre() {
        return estudianteNombre;
    }

    public void setEstudianteNombre(String estudianteNombre) {
        this.estudianteNombre = estudianteNombre;
    }
}