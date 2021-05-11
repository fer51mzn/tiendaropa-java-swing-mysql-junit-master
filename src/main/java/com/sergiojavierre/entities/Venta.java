package com.sergiojavierre.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Venta implements Serializable {

    private HashMap<Prenda,Integer> prendas;
    private Date fecha;

    public Venta(){
        this.prendas = new HashMap<Prenda,Integer>();
        this.fecha = new Date();
    }

    public void setPrendas(HashMap<Prenda,Integer> carrito){
        this.prendas = carrito;
    }

    public HashMap<Prenda,Integer> getPrendas() {
        return prendas;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "prendas=" + prendas +
                ", fecha=" + fecha +
                '}';
    }
}
