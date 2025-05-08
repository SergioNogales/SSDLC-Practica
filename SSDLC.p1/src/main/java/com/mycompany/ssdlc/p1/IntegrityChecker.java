package com.mycompany.ssdlc.p1;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

public class IntegrityChecker {
    public File path = new File("C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\");
    public File pathBackups = new File("C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\backup\\");
    public File[] files = path.listFiles();
    public File[] backups = pathBackups.listFiles();
    public ArrayList<IntegrityValue> integrityList;
    public AuditManager audit = new AuditManager();
    
    public void updateAll() 
        throws Exception
    {
        File csvFile = new File("C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\integridad.csv");
        IntegrityValue valor;
        try (FileWriter writer = new FileWriter(csvFile)) 
        {  
            integrityList = new ArrayList<IntegrityValue>();
            String nameFile;
            for(int i = 0; i<files.length; i++)
            {
                nameFile = files[i].getName();
                valor = new IntegrityValue(nameFile, checksumCalculator(files[i].getAbsolutePath()));
                integrityList.add(valor);
                writer.write(valor.name + ";" + valor.checksum + "\n");
                File backup = new File(pathBackups + "\\" + nameFile);
                Files.copy(files[i].toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
                backups = pathBackups.listFiles();
            }
        }catch(Exception e)
        {
        }
    }
    
    public void updateFile(String nameFile) 
        throws Exception
    {
        File csvFile = new File("C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\integridad.csv");
        IntegrityValue valor;
        try (FileReader reader = new FileReader(csvFile)) 
        {  
            for(int i = 0; i<integrityList.size(); i++)
            {
                if(integrityList.get(i).name.equals(nameFile))
                {
                    integrityList.set(i, new IntegrityValue(nameFile, checksumCalculator(files[i].getAbsolutePath())));
                    break;
                }
            }
        }catch(Exception e)
        {
        }
    }
    
    public void checkFile(String nameFile) 
        throws Exception
    {
        int cont = 0;
        
        for(int i = 0; i<files.length; i++)
        {
            if (files[i].getName().compareTo(nameFile)==0)
            {
               if(checksumCalculator(files[i].getAbsolutePath()).compareTo(integrityList.get(i).checksum)!=0)
               {
                   dataManagement(nameFile);
               }
            }
        }
    }
    
    public void checkAll() 
            throws Exception
    {
        for(int i = 0; i<files.length; i++)
        {
            checkFile(files[i].getName());
        }
    }
    
    private void dataManagement(String nameFile) 
        throws Exception
    {
        Scanner sc = new Scanner(System.in);
        Path bakcup = null;
        Path original = null;
        while(true)
        {
            System.out.println("\nSe ha detectado un fallo en la lectura de datos");
            System.out.println("\nEsto se debe a la modificación externa de los archivos usados en el programa");
            System.out.println(" [1] Restaurar los datos a su último valor seguro");
            System.out.println(" [2] Los cambios fueron intencionalmente realizados de manera externa");
            System.out.println(" [3] Resolver más tarde");
            int seleccion = sc.nextInt();
            
            for (File file : backups) 
            {
                if (file.getName().equals(nameFile)) {
                    bakcup=file.toPath();
                }
            }
            for (File file : files) 
            {
                if (file.getName().equals(nameFile)) {
                    original=file.toPath();
                }
            }
            for(IntegrityValue il : integrityList)
            {
                
            }
            
            switch(seleccion)
            {
                case 1:
                    Files.copy(bakcup, original, StandardCopyOption.REPLACE_EXISTING);
                    updateFile(nameFile);
                    audit.save("Se solventó un problema de integridad en el fichero " + nameFile + ", se importaron los datos de la copia de seguridad");
                    return;
                case 2:
                    Files.copy(original, bakcup, StandardCopyOption.REPLACE_EXISTING);
                    updateFile(nameFile);
                    audit.save("Se solventó un problema de integridad en el fichero " + nameFile + ", se determinó que el cambio era intencionado y se sobreescribió la copia de seguridad");
                    return;
                case 3:
                    audit.save("Se notificó un problema de integridad en el fichero " + nameFile + ", pero se ignoró el problema");
                    return;
                default :
                    System.out.println("No existe tal selección");
                    break;
            }
        }
    }
    
    private String checksumCalculator(String nameFile)
        throws Exception
    {
        //utilizo una estructura similar a la dada en el ejemplo
        MessageDigest sha =  MessageDigest.getInstance("SHA256");
        FileInputStream fis = new FileInputStream(new File(nameFile));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int nb ;
        do
        {
            nb = fis.read(buffer);
            if (nb>0)
                baos.write(buffer, 0, nb);
        }while (nb>0);
        sha.update(baos.toByteArray());
        byte resultado[] = sha.digest();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i< resultado.length;i++)
        {
          sb.append(String.format("%02x", resultado[i]));
        }
        fis.close();
        return sb.toString();
    }
    
    private void loadIntegrityValues() 
    {
        File csvFile = new File("C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\integridad.csv");
        integrityList = new ArrayList<IntegrityValue>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) 
        {
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(";");
                integrityList.add(new IntegrityValue(parts[0], parts[1]));
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }
    
    public IntegrityChecker(){
        loadIntegrityValues();
    }
}