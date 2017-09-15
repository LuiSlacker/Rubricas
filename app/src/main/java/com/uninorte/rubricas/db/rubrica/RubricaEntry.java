package com.uninorte.rubricas.db.rubrica;

/**
 * Created by Usuario on 15/09/2017.
 */

public class RubricaEntry {

    public int id;
    public String nombre;

    public RubricaEntry() {}

    public RubricaEntry(String nombre) {
        this.nombre = nombre;
    }

    public RubricaEntry(int id, String nombre) {
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
