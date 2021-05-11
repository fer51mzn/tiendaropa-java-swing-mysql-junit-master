package com.sergiojavierre.dao;


import java.io.*;

public abstract class DAOSerializable {

    protected final String file;

    public DAOSerializable(String file){
        this.file = file;
    }

    protected void save(Object obj){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Object read(){
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Todavía no existe el fichero que intentas leer");
        }
        return obj;
    }

}
