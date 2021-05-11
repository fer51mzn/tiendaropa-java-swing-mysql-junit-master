package com.sergiojavierre.dao.ventas;

import com.sergiojavierre.dao.DAOFactory;
import com.sergiojavierre.dao.DAOSerializable;
import com.sergiojavierre.entities.Prenda;
import com.sergiojavierre.entities.Venta;

import java.util.ArrayList;
import java.util.List;

public class DAOVentasSerializable extends DAOSerializable implements DAOVentas {

    private final List<Venta> ventas;

    public DAOVentasSerializable() {
        super("ventas");
        Object obj = read();
        if(obj == null) {
            this.ventas = new ArrayList<>();
        }
        else this.ventas = (List<Venta>) obj;
    }

    @Override
    public void add(Venta venta) {
        //elimino del stock los artículos que se venden
        DAOFactory.getInstance().getDaoStock().update(Prenda.CAMISETA,-venta.getPrendas().get(Prenda.CAMISETA));
        DAOFactory.getInstance().getDaoStock().update(Prenda.PANTALON,-venta.getPrendas().get(Prenda.PANTALON));
        DAOFactory.getInstance().getDaoStock().update(Prenda.ZAPATO,-venta.getPrendas().get(Prenda.ZAPATO));
        this.ventas.add(venta);
        this.save(this.ventas);
    }

    @Override
    public List<Venta> getAll() {
        return ventas;
    }
}
