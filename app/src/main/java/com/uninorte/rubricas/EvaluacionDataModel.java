package com.uninorte.rubricas;


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
