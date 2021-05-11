package com.sergiojavierre.ui;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.entities.Prenda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

public class GUIStock extends JPanel {

    JLabel labelCamisetas, labelPantalones, labelZapatos;
    JTextField textCamisetas, textPantalones, textZapatos;
    HashMap<Prenda,Integer>stock;

    public GUIStock(HashMap<Prenda,Integer>stock){
        this.stock = stock;

        setVisible(true);
        setLayout(new GridLayout(3,2));
        setSize(400,600);
        setComponents();
    }

    public void updateStock(HashMap<Prenda,Integer> stock){
        this.stock = stock;
        setData();
    }

    private void setData(){
        textCamisetas.setText(String.valueOf(stock.get(Prenda.CAMISETA)));
        textPantalones.setText(String.valueOf(stock.get(Prenda.PANTALON)));
        textZapatos.setText(String.valueOf(stock.get(Prenda.ZAPATO)));
    }

    private void setComponents(){
        labelCamisetas = new JLabel(String.valueOf(Prenda.CAMISETA));
        textCamisetas = new JTextField();
        labelPantalones = new JLabel(String.valueOf(Prenda.PANTALON));
        textPantalones = new JTextField();
        labelZapatos = new JLabel(String.valueOf(Prenda.ZAPATO));
        textZapatos = new JTextField();
        setData();
        this.add(labelCamisetas);
        this.add(textCamisetas);
        this.add(labelPantalones);
        this.add(textPantalones);
        this.add(labelZapatos);
        this.add(textZapatos);
        textCamisetas.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                update(Prenda.CAMISETA);
            }
        });
        textPantalones.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                update(Prenda.PANTALON);
            }
        });
        textZapatos.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                update(Prenda.ZAPATO);
            }
        });
    }

    private void update(Prenda prenda){
        try {
            int valor = 0;
            if (prenda == Prenda.CAMISETA) {
                valor = Integer.parseInt(textCamisetas.getText());
            } else if (prenda == Prenda.PANTALON) {
                valor = Integer.parseInt(textPantalones.getText());
            } else if (prenda == Prenda.ZAPATO) {
                valor = Integer.parseInt(textZapatos.getText());
            }
            if(valor != stock.get(prenda)) {
                valor -= stock.get(prenda) ;
                System.out.println("Aplicando update "+prenda);
                DAOFactory.getInstance().getDaoStock().update(prenda, valor);
            }
        }
        catch (Exception e){
            if (prenda == Prenda.CAMISETA) {
                textCamisetas.setText("0");
            } else if (prenda == Prenda.PANTALON) {
                textPantalones.setText("0");
            } else if (prenda == Prenda.ZAPATO) {
                textZapatos.setText("0");
            }
        }
    }


}
