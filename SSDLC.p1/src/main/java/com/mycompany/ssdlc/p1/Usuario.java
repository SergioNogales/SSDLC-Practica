package com.mycompany.ssdlc.p1;

public class Usuario {
    private String name;
    private Rol rol;
    
    public String getNombre()
    {
        return name;
    }
    public Rol getRol()
    {
        return rol;
    }
    public void setRol(String rol)
    {
        switch(rol)
        {
            case "admin":
                this.rol = new Admin();
                break;
            case "vendedor":
                this.rol = new Vendedor();
                break;
            case "colaborador":
                this.rol = new Colaborador();
                break;
            case "asistente":
                this.rol = new Asistente();
                break;
        }
    }
    public Usuario(String name)
    {
        this.name = name;
    }
    public Usuario(String name, String rol)
    {
        this.name = name;
        setRol(rol);
    }
}
