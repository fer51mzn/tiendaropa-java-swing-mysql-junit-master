package com.sergiojavierre.dao.stock;

import com.sergiojavierre.entities.Prenda;

import java.util.HashMap;

public interface DAOStock {

    public HashMap<Prenda,Integer> getStock();
    public void update(Prenda prenda, Integer value);
    public Integer getStock(Prenda prenda);

}
