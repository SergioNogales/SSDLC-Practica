package com.mycompany.ssdlc.p1;

public class Rol {
    protected int[] permisos = new int[10];
    
    public int getPermiso(int pos)
    {
        return permisos[pos - 1];
    }
}
