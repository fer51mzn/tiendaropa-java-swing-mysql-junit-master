package com.sergiojavierre.ui;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.entities.Prenda;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GUIMenu extends JFrame{

    GUIStock guiStock;
    GUIVentas guiVentas;

    HashMap<Prenda,Integer> stock;

    public GUIMenu(){
        setTitle("Menú");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1900,600);
        getData();
        addCustomChilds();
    }
    private void getData(){
        stock = DAOFactory.getInstance().getDaoStock().getStock();
    }

    private void addCustomChilds(){
        this.getContentPane().setLayout(new FlowLayout());
        guiStock = new GUIStock(stock);
        guiVentas = new GUIVentas(stock, this);
        getContentPane().add(guiStock);
        getContentPane().add(guiVentas);
    }

    public void observer(){
        getData();
        guiStock.updateStock(stock);
    }

}
