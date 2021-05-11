package com.sergiojavierre.ui;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.entities.Prenda;
import com.sergiojavierre.entities.Venta;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;

public class GUIMain extends JFrame{

    //ui elements
    private JPanel panelPrincipal;
    private JTextField textCamisetas;
    private JTextField textPantalones;
    private JTextField textZapatos;
    private JButton subCamisetas;
    private JButton addCamisetas;
    private JButton subPantalones;
    private JButton subZapatos;
    private JButton addPantalones;
    private JButton addZapatos;
    private JButton guardarVentaButton;
    private JList listVentas;
    private JLabel labelCamisetas;
    private JLabel labelPantalones;
    private JLabel labelZapatos;
    private JTextPane infoPane;

    //data
    private HashMap<Prenda,Integer> stock;
    private List<Venta> ventas;

    int numCamisetas = 0, numPantalones = 0, numZapatos = 0;

    public GUIMain(){

        getData();
        setView();
    }

    private void getData(){
        stock = DAOFactory.getInstance().getDaoStock().getStock();
        ventas = DAOFactory.getInstance().getDaoVentas().getAll();
    }

    private void setView(){
        setDefaultView();
        setStock();
        setVentasButtons();
        setVentas();
    }

    private void setDefaultView(){
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,600);
        add(panelPrincipal);
    }

    private void setStock(){
        textCamisetas.setText(String.valueOf(stock.get(Prenda.CAMISETA)));
        textPantalones.setText(String.valueOf(stock.get(Prenda.PANTALON)));
        textZapatos.setText(String.valueOf(stock.get(Prenda.ZAPATO)));
        textCamisetas.addFocusListener(updateStock(Prenda.CAMISETA));
        textPantalones.addFocusListener(updateStock(Prenda.PANTALON));
        textZapatos.addFocusListener(updateStock(Prenda.ZAPATO));
    }

    private FocusListener updateStock(Prenda prenda){
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(prenda);
                update(prenda);
            }
        };
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

    private void setVentasButtons(){
        addCamisetas.addActionListener(setAction(Prenda.CAMISETA,1));
        subCamisetas.addActionListener(setAction(Prenda.CAMISETA,-1));
        addPantalones.addActionListener(setAction(Prenda.PANTALON,1));
        subPantalones.addActionListener(setAction(Prenda.PANTALON,-1));
        addZapatos.addActionListener(setAction(Prenda.ZAPATO,1));
        subZapatos.addActionListener(setAction(Prenda.ZAPATO,-1));

        guardarVentaButton.addActionListener(e -> performVenta());
    }

    private ActionListener setAction(Prenda prenda, int change){
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

    private int applyChanges(Prenda prenda, int numPrendas, int maxStock, int change, JLabel label){
        if(maxStock>=(numPrendas+change) && (change == 1 || numPrendas > 0)) {
            numPrendas += change;
            label.setText(prenda + ": " + numPrendas);
        }
        return numPrendas;
    }

    private void performVenta(){
        if(stock.get(Prenda.CAMISETA) > 0 || stock.get(Prenda.PANTALON) > 0 || stock.get(Prenda.ZAPATO) > 0) {
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
            setStock();
            setVentas();
        }
    }

    private void setVentas(){
        listVentas.setListData(ventas.toArray());
    }

}
