package com.sergiojavierre.dao.stock;

import com.sergiojavierre.dao.DAOSerializable;
import com.sergiojavierre.entities.Prenda;

import java.util.HashMap;

public class DAOStockSerializable extends DAOSerializable implements DAOStock{

    private HashMap<Prenda,Integer> stock;

    public DAOStockSerializable(){
        super("stocks");
        Object obj = read();
        if(obj == null) {
            stock = new HashMap<>();
            stock.put(Prenda.CAMISETA,0);
            stock.put(Prenda.PANTALON,0);
            stock.put(Prenda.ZAPATO,0);
            save(stock);
        }
        else stock = (HashMap<Prenda, Integer>) obj;
    }

    @Override
    public HashMap<Prenda, Integer> getStock() {
        return stock;
    }

    @Override
    public void update(Prenda prenda, Integer value) {
        if(stock.containsKey(prenda)) {
            int currentStock = stock.get(prenda);
            stock.put(prenda, currentStock + value);
        }
        else {
            stock.put(prenda,value);
        }
        save(stock);
    }

    @Override
    public Integer getStock(Prenda prenda) {
        return stock.get(prenda);
    }
}
