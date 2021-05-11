package com.sergiojavierre.dao.ventas;

import com.sergiojavierre.entities.Venta;

import java.util.List;

public interface DAOVentas {

    public void add(Venta venta);
    public List<Venta> getAll();

}
