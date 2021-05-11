package com.sergiojavierre.dao.stock;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.entities.Prenda;
import com.sergiojavierre.entities.Venta;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

class DAOStockTest {

    @BeforeAll
    @AfterAll
    static void clearStock(){
        HashMap<Prenda,Integer> stock = DAOFactory.getInstance().getDaoStock().getStock();
        DAOFactory.getInstance().getDaoStock().update(Prenda.CAMISETA,-stock.get(Prenda.CAMISETA));
        DAOFactory.getInstance().getDaoStock().update(Prenda.PANTALON,-stock.get(Prenda.PANTALON));
        DAOFactory.getInstance().getDaoStock().update(Prenda.ZAPATO,-stock.get(Prenda.ZAPATO));
    }

    @Test
    public void getStockAdding(){
        clearStock();
        int numCamisetas = 5;
        DAOFactory.getInstance().getDaoStock().update(Prenda.CAMISETA,numCamisetas);
        HashMap<Prenda,Integer> stock = DAOFactory.getInstance().getDaoStock().getStock();
        int camisetas = stock.get(Prenda.CAMISETA);
        assertEquals(numCamisetas,camisetas);
    }

    @Test
    public void getStockAfterVenta(){
        clearStock();
        int numCamisetas = 5, camisetasVenta = 2;
        DAOFactory.getInstance().getDaoStock().update(Prenda.CAMISETA,numCamisetas);
        Venta venta = new Venta();
        HashMap<Prenda,Integer> carrito = new HashMap<>();
        carrito.put(Prenda.CAMISETA,camisetasVenta);
        carrito.put(Prenda.PANTALON,0);
        carrito.put(Prenda.ZAPATO,0);
        venta.setPrendas(carrito);
        DAOFactory.getInstance().getDaoVentas().add(venta);
        HashMap<Prenda,Integer> stock = DAOFactory.getInstance().getDaoStock().getStock();
        int camisetas = stock.get(Prenda.CAMISETA);
        assertEquals(numCamisetas-camisetasVenta,camisetas);
    }

}