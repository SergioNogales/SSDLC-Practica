package com.mycompany.ssdlc.p1;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditManager {
    
    /*
        El sistema de auditoría debería ser compatible con el de cifrado para evitar mostrar los datos de los clientes en un ejemplo real
        En este caso no he tenido eso en cuenta
    */
    
    private String filePath="C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\auditoria.txt";
    
    
    public void save(String text, Usuario user) 
        throws Exception
    {
        LocalDateTime now = LocalDateTime.now();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " " + user.getNombre() + " " + user.getRol() +" "+ text);
        writer.newLine();
        writer.close();
    }
    
    public void save(String text) 
        throws Exception
    {
        LocalDateTime now = LocalDateTime.now();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " " + "Sistema" + " " + text);
        writer.newLine();
        writer.close();
    }
}
