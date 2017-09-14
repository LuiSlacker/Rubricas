package com.uninorte.rubricas.db.estudiante;

public class EstudianteEntry {

    private int id;
    private String nombre;

    public EstudianteEntry() {}

    public EstudianteEntry(String nombre) {
        this.nombre = nombre;
    }

    public EstudianteEntry(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
