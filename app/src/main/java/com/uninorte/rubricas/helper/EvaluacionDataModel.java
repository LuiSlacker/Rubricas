package com.uninorte.rubricas.helper;


public class EvaluacionDataModel {

    private String estudiante;
    private float nota;

    public EvaluacionDataModel(String estudiante, float nota) {
        this.estudiante = estudiante;
        this.nota = nota;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public float getNota() {
        return nota;
    }
}
