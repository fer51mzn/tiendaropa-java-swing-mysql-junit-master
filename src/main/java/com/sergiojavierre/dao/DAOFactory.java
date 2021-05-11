package com.sergiojavierre.dao;

import com.sergiojavierre.dao.stock.DAOStock;
import com.sergiojavierre.dao.stock.DAOStockDatabase;
import com.sergiojavierre.dao.stock.DAOStockSerializable;
import com.sergiojavierre.dao.ventas.DAOVentas;
import com.sergiojavierre.dao.ventas.DAOVentasSerializable;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOStock daoStock;
    private DAOVentas daoVentas;

    private DAOFactory(){}

    public static DAOFactory getInstance() {
        if(daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public DAOStock getDaoStock() {
        if(daoStock == null){
           // daoStock = new DAOStockSerializable();
            daoStock = new DAOStockDatabase();
        }
        return daoStock;
    }

    public DAOVentas getDaoVentas() {
        if(daoVentas == null){
            daoVentas = new DAOVentasSerializable();
        }
        return daoVentas;
    }
}
