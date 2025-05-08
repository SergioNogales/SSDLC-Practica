package com.mycompany.ssdlc.p1;


public class Cliente {
    
    private String name;
    private String surname;
    private String adress;
    private int telef;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setTelef(int telef) {
        this.telef = telef;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAdress() {
        return adress;
    }

    public int getTelef() {
        return telef;
    }
    
    public Cliente(String data) 
    {
        String[] parts = data.split(";");
        this.name = parts[0];
        this.surname = parts[1];
        this.adress = parts[2];
        this.telef = Integer.parseInt(parts[3]);
    }
    
    public Cliente()
    {
        
    }
    
    @Override
    public String toString()
    {
        return name+";"+surname+";"+adress+";"+telef+";";
    }
}
