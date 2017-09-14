package com.uninorte.rubricas.db.asignatura;

public class AsignaturaEntry {

    public int id;
    private String nombre;

    public AsignaturaEntry() {}

    public AsignaturaEntry(String nombre) {
        this.nombre = nombre;
    }

    public AsignaturaEntry(int id, String nombre) {
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
