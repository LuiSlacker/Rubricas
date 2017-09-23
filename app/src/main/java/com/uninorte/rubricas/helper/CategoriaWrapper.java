package com.uninorte.rubricas.helper;

import com.uninorte.rubricas.db.calificacion.elemento.CalificacionElemento;

import java.util.ArrayList;
import java.util.List;

public class CategoriaWrapper {

    private int categoriaID;
    private List<ElementoWrapper> elementoWrapperList;

    public CategoriaWrapper() {
        elementoWrapperList = new ArrayList<>();
    }

    public int getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(int categoriaID) {
        this.categoriaID = categoriaID;
    }

    public List<ElementoWrapper> getElementoWrapperList() {
        return elementoWrapperList;
    }

    public void setElementoWrapperList(List<ElementoWrapper> elementoWrapperList) {
        this.elementoWrapperList = elementoWrapperList;
    }
}
