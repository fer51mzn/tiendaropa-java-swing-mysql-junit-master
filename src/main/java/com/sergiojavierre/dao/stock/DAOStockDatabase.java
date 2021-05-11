package com.sergiojavierre.dao.stock;

import com.sergiojavierre.db.DBConnection;
import com.sergiojavierre.entities.Prenda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DAOStockDatabase implements DAOStock{
    @Override
    public HashMap<Prenda, Integer> getStock() {
        HashMap<Prenda,Integer> stock = new HashMap<>();
        try{
            Statement statement = DBConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from stock");
            while (resultSet.next()){
                Prenda prenda = null;
                String nombrePrenda = resultSet.getString("prenda");
                switch (nombrePrenda){
                    case "camiseta" -> prenda = Prenda.CAMISETA;
                    case "pantalon" -> prenda = Prenda.PANTALON;
                    case "zapato"   -> prenda = Prenda.ZAPATO;
                }
                int cantidad = resultSet.getInt("cantidad");
                stock.put(prenda,cantidad);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stock;
    }

    @Override
    public void update(Prenda prenda, Integer value) {
        try {
            Statement statement = DBConnection.getInstance().createStatement();
            String valueText = "";
            if(value >= 0){
                valueText = "+"+value;
            }
            else{
                valueText = String.valueOf(value);
            }
            String sql = "update stock set cantidad = cantidad "+valueText+" where prenda = '"+prenda.name().toLowerCase()+"'";
            System.out.println("Executing:\n"+sql);
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Integer getStock(Prenda prenda) {
        try{
            Statement statement = DBConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from stock where prenda = "+prenda.name().toLowerCase());
            resultSet.next();
            return resultSet.getInt("cantidad");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
