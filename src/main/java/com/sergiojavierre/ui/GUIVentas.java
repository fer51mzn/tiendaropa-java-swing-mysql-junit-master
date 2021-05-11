package com.sergiojavierre.ui;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.entities.Prenda;
import com.sergiojavierre.entities.Venta;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class GUIVentas extends JPanel {

    GUIMenu menu;

    int numCamisetas = 0, numPantalones = 0, numZapatos = 0;
    JLabel labelCamisetas, labelPantalones, labelZapatos;
    JButton addCamisetas, subCamisetas, addPantalones, subPantalones, addZapatos, subZapatos;
    JButton buttonVenta;

    JList listVentas;

    HashMap<Prenda,Integer> stock;
    List<Venta> ventas;

    public GUIVentas(HashMap<Prenda,Integer>stock, GUIMenu menu){
        this.stock = stock;
        this.menu = menu;

        setVisible(true);
        setSize(400,600);
        getData();
        setComponents();
    }

    private void getData(){
        ventas = DAOFactory.getInstance().getDaoVentas().getAll();
    }

    private void setComponents(){
        setVentaUI();
        setListaUI();

    }

    private void setVentaUI(){
        labelCamisetas = new JLabel(Prenda.CAMISETA+": "+String.valueOf(numCamisetas));
        labelPantalones = new JLabel(Prenda.PANTALON+": "+String.valueOf(numPantalones));
        labelZapatos = new JLabel(Prenda.ZAPATO+": "+String.valueOf(numZapatos));
        addCamisetas = new JButton("+");
        subCamisetas = new JButton("-");
        addPantalones = new JButton("+");
        subPantalones = new JButton("-");
        addZapatos = new JButton("+");
        subZapatos = new JButton("-");
        buttonVenta = new JButton("Nueva venta");

        this.add(addCamisetas);
        this.add(labelCamisetas);
        this.add(subCamisetas);

        this.add(addPantalones);
        this.add(labelPantalones);
        this.add(subPantalones);

        this.add(addZapatos);
        this.add(labelZapatos);
        this.add(subZapatos);

        this.add(buttonVenta);

        addCamisetas.addActionListener(setAction(Prenda.CAMISETA,1));
        subCamisetas.addActionListener(setAction(Prenda.CAMISETA,-1));
        addPantalones.addActionListener(setAction(Prenda.PANTALON,1));
        subPantalones.addActionListener(setAction(Prenda.PANTALON,-1));
        addZapatos.addActionListener(setAction(Prenda.ZAPATO,1));
        subZapatos.addActionListener(setAction(Prenda.ZAPATO,-1));

        buttonVenta.addActionListener(e -> performVenta());
    }

    private ActionListener setAction(Prenda prenda,int change){
        return e -> clickedButton(prenda,change);
    }

    private void clickedButton(Prenda prenda, int change){
        switch (prenda){
            case CAMISETA -> {
                numCamisetas = applyChanges(prenda,numCamisetas, stock.get(Prenda.CAMISETA), change,labelCamisetas);
            }
            case PANTALON -> {
                numPantalones = applyChanges(prenda,numPantalones, stock.get(Prenda.PANTALON), change,labelPantalones);
            }
            case ZAPATO -> {
                numZapatos = applyChanges(prenda,numZapatos, stock.get(Prenda.ZAPATO), change,labelZapatos);
            }
        }
    }

    private int  applyChanges(Prenda prenda, int numPrendas, int maxStock, int change, JLabel label){
        if(maxStock>=(numPrendas+change) && (change == 1 || numPrendas > 0)) {
            numPrendas += change;
            label.setText(prenda + ": " + numPrendas);
        }
        return numPrendas;
    }

    private void performVenta(){
        if(numCamisetas > 0 || numPantalones > 0 || numZapatos > 0) {
            Venta venta = new Venta();
            HashMap<Prenda, Integer> prendas = new HashMap<>();
            prendas.put(Prenda.CAMISETA, numCamisetas);
            prendas.put(Prenda.PANTALON, numPantalones);
            prendas.put(Prenda.ZAPATO, numZapatos);
            venta.setPrendas(prendas);
            DAOFactory.getInstance().getDaoVentas().add(venta);
            numCamisetas = applyChanges(Prenda.CAMISETA, numCamisetas, numCamisetas, -numCamisetas, labelCamisetas);
            numPantalones = applyChanges(Prenda.PANTALON, numPantalones, numPantalones, -numPantalones, labelPantalones);
            numZapatos = applyChanges(Prenda.ZAPATO, numZapatos, numZapatos, -numZapatos, labelZapatos);
            getData();
            setListaUI();
            menu.observer();
        }
    }

    private void setListaUI(){
        if(listVentas!=null){
            remove(listVentas);
        }
        listVentas = new JList(ventas.toArray());
        add(listVentas);
    }
}
