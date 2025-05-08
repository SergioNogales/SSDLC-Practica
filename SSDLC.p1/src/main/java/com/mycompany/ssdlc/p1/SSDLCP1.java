package com.mycompany.ssdlc.p1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SSDLCP1 {
    
    static IntegrityChecker checker = new IntegrityChecker();
    static CipherManager cipher = new CipherManager();
    static AuditManager audit = new AuditManager();
    static String cipherKey = "ABBABKAR";
    
    public static void main(String[] args) 
        throws Exception
    {
        Scanner sc = new Scanner(System.in);
        String usuario = null;        
        System.out.println("Quién intenta iniciar sesión?");
        usuario = sc.nextLine();
        Usuario usuario_= buscarUsuario(usuario);
        if(usuario_ != null)
        {
            System.out.println("Inicio de sesión correcto");
            usuario = null;
        }
        else
        {
            System.out.println("No se ha podido iniciar sesión, no existe tal usuario");
            return;
        }
        while(true)
        {
            try 
            {
                Thread.sleep(1000);
            } 
            catch (InterruptedException ex)
            {
                Logger.getLogger(SSDLCP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            checker.checkAll();
            checker.updateAll();
            System.out.println("|Menu de interaccion|");
            System.out.println(" [1]Crear Venta \n [2]Modificar Venta \n [3]Eliminar Venta");
            System.out.println(" [4]Listar Productos \n [5]Agregar Productos \n [6]Desactivar productos");
            System.out.println(" [7]Añadir usuario \n [8]Gestion de roles \n [9]Añadir cliente");
            System.out.println(" [10]Modificar Cliente \n [11]Eliminar Cliente \n [0]Salir");
            int seleccion = sc.nextInt();
            switch(seleccion)
            {
                case 1:
                    crearVenta(usuario_);
                    break;
                case 2:
                    modVenta(usuario_);
                    break;
                case 3:
                    elimVenta(usuario_);
                    break;
                case 4:
                    listarProd(usuario_);
                    break;
                case 5:
                    añadirProd(usuario_);
                    break;
                case 6:
                    quitarProd(usuario_);
                    break;
                case 7:
                    crearUser(usuario_);
                    break;
                case 8:
                    añadirRol(usuario_);
                    break;
                case 9:
                    crearCliente(usuario_);
                    break;
                case 10:
                    modificarCliente(usuario_);
                    break;
                case 11:
                    eliminarCliente(usuario_);
                    break;
                case 0:
                    return; 
                default :
                    System.out.println("No existe tal selección");
                    break;
            }
        }
    }
    
    public static Usuario buscarUsuario(String usuario)
    {
        int i = 0;
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\usuarios.txt";
         
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) 
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                if(line.split(";")[0].compareTo(usuario) == 0)
                {
                    return new Usuario(line.split(";")[0],line.split(";")[1]);
                }
            }
            fileReader.close();
            bufferedReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
    
        public static Cliente buscarCliente(String usuario)
    {
        int i = 0;
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\clientes.txt";
         
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) 
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                if(line.split(";")[0].compareTo(usuario) == 0)
                {
                    return new Cliente(line);
                }
            }
            fileReader.close();
            bufferedReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
    
    public static void crearVenta(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(1) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\ventas.txt";
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduzca el número de productos a vendidos");
        int numPro = sc.nextInt();
        
        
        //al final de la funcion
        audit.save("Se ha añadido la venta: " ,usuario_);
        checker.updateFile("ventas.txt");
    }
    
    public static void modVenta(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(2) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\ventas.txt";

        
        //al final de la funcion
        checker.updateFile("ventas.txt");
    }
    
    public static void elimVenta(Usuario usuario_)
        throws Exception
    {
        if(usuario_.getRol().getPermiso(3) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\ventas.txt";

                Scanner sc = new Scanner(System.in);
        List<String> ventas = Files.readAllLines(Paths.get(filePath));
        int i = 0;
        
        System.out.println("Listado de productos:");
        for(String venta : ventas)
        {

        }
        System.out.println("Introduzca su selección:");
        int seleccion = sc.nextInt();
        sc.nextLine();
        
        String venta=ventas.get(seleccion-1);
        ventas.remove(seleccion-1);
        
        audit.save("Se ha eliminado la venta:" + venta,usuario_);
        checker.updateFile("ventas.txt");
    }
    
    public static void listarProd(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(4) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\productos.txt";
        
        List<String> productos = Files.readAllLines(Paths.get(filePath));
        int i = 0;
        
        System.out.println("Listado de productos:");
        for(String producto : productos)
        {
            System.out.println(i+1 +". Producto: "+producto.split(";")[0]+", Precio: " + producto.split(";")[1]);
            i=i+1;
        }
        audit.save("Se listaron los productos", usuario_);
    }
    
    public static void añadirProd(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(5) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\productos.txt";
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduzca el nombre del producto a añadir");
        String product=sc.nextLine();
        System.out.println("Introduzca el precio del producto a añadir");
        product+=";"+sc.nextLine();
        
        System.out.println("Ha añadido el producto: " + product.split(";")[0] + " con precio: " + product.split(";")[1]);
        fileAdder(filePath, product);
        
        //al final de la funcion
        audit.save("Se ha añadido el producto:" + product,usuario_);
        checker.updateFile("productos.txt");
    }
    
    public static void quitarProd(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(6) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\productos.txt";
        
        Scanner sc = new Scanner(System.in);
        List<String> productos = Files.readAllLines(Paths.get(filePath));
        int i = 0;
        
        System.out.println("Listado de productos:");
        for(String producto : productos)
        {
            System.out.println(i+1 +". Producto: "+producto.split(";")[0]+", Precio: " + producto.split(";")[1]);
            i=i+1;
        }
        System.out.println("Introduzca su selección:");
        int seleccion = sc.nextInt();
        sc.nextLine();
        
        String product=productos.get(seleccion-1);
        productos.remove(seleccion-1);
        Files.write(Paths.get(filePath),productos);
        
        //al final de la funcion
        audit.save("Se ha eliminado el producto:" + product,usuario_);
        checker.updateFile("productos.txt");
    }
        
    public static void crearUser(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(7) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\usuarios.txt";
        Scanner sc =new Scanner(System.in);
        
        System.out.println("Introduzca el nombre del usuario a añadir");
        String user=sc.nextLine();
        System.out.println("Introduzca el rol del usuario a añadir");
        user+=";"+sc.nextLine();
        
        fileAdder(filePath,user);
        
        //al final de la funcion
        audit.save("Se añadió un nuevo usuario " + user,usuario_);
        checker.updateFile("usuarios.txt");
    }
    
    public static void añadirRol(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(7) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\usuarios.txt";
        Scanner sc =new Scanner(System.in);
        List<String> usuarios = Files.readAllLines(Paths.get(filePath));
        int i = 0;
        
        System.out.println("Listado de usuarios:");
        for(String user : usuarios)
        {
            System.out.println(i+1 +". "+user.split(";")[0]);
            i=i+1;
        }
        int seleccion=sc.nextInt()-1;
        sc.nextLine();
        
        String nombre = usuarios.get(seleccion).split(";")[0];
        String oldRol = usuarios.get(seleccion).split(";")[1];
        
        System.out.println("Usted ha seleccionado a " + nombre);
        System.out.println("Introduzca el nuevo rol del usuario");
        String rol=sc.nextLine();

        
        usuarios.set(seleccion, nombre+";"+rol);
        Files.write(Paths.get(filePath), usuarios);
        
        //al final de la funcion
        audit.save("Se modificó el rol del usuario: " + nombre + ", " + oldRol + "  -> " + rol,usuario_);
        checker.updateFile("usuarios.txt");
    }
    
    public static void crearCliente(Usuario usuario_) 
        throws Exception
    {
        if(usuario_.getRol().getPermiso(8) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\clientes.txt";

        Scanner sc =new Scanner(System.in);
        System.out.println("Introduzca el nombre del cliente a añadir");
        String frase=sc.nextLine();
        System.out.println("Introduzca el apellido del cliente a añadir");
        frase+=";"+sc.nextLine();
        System.out.println("Introduzca el código postal del cliente a añadir");
        frase+=";"+sc.nextLine();
        System.out.println("Introduzca el teléfono del cliente a añadir");
        frase+=";"+sc.nextLine()+";";
        
        String cifrado = Base64.getEncoder().encodeToString(cipher.cipher(frase, cipherKey));
        fileAdder(filePath, cifrado.toString());
        
        //al final de la funcion
        checker.updateFile("clientes.txt");
        audit.save("Se creó el cliente: " + frase, usuario_);
    }
    
    public static void modificarCliente(Usuario usuario_) 
        throws Exception
    {
        if (usuario_.getRol().getPermiso(9) != 1) 
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }

        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\clientes.txt";
        Scanner sc = new Scanner(System.in);
        List<String> clientes = Files.readAllLines(Paths.get(filePath));
        List<String> clientesDecifrados = new ArrayList<>();

        for (String cliente : clientes) 
        {
            byte[] cifradoBytes = Base64.getDecoder().decode(cliente);
            clientesDecifrados.add(cipher.uncipher(cifradoBytes, cipherKey));
        }

        System.out.println("Lista de clientes:");
        for (int i = 0; i < clientesDecifrados.size(); i++) 
        {
            System.out.println((i + 1) + ". " + clientesDecifrados.get(i));
        }

        System.out.println("Introduzca el número del cliente que desea modificar:");
        int seleccion = sc.nextInt();
        sc.nextLine();

        String[] datosCliente = clientesDecifrados.get(seleccion - 1).split(";");

        System.out.println("Cliente seleccionado: " + clientesDecifrados.get(seleccion - 1));
        
        System.out.println("Introduzca el nuevo nombre del cliente (actual: " + datosCliente[0] + "):");
        datosCliente[0] = sc.nextLine();
        System.out.println("Introduzca el nuevo apellido del cliente (actual: " + datosCliente[1] + "):");
        datosCliente[1] = sc.nextLine();
        System.out.println("Introduzca el nuevo código postal del cliente (actual: " + datosCliente[2] + "):");
        datosCliente[2] = sc.nextLine();
        System.out.println("Introduzca el nuevo teléfono del cliente (actual: " + datosCliente[3] + "):");
        datosCliente[3] = sc.nextLine();

        clientes.set(seleccion - 1, Base64.getEncoder().encodeToString(cipher.cipher(datosCliente[0]+";"+datosCliente[1]+";"+datosCliente[2]+";"+datosCliente[3]+";", cipherKey)));
        Files.write(Paths.get(filePath), clientes);

        checker.updateFile("clientes.txt");
        audit.save("Se modificó el cliente: " + clientesDecifrados.get(seleccion - 1).toString() + " -> " + datosCliente[0]+";"+datosCliente[1]+";"+datosCliente[2]+";"+datosCliente[3]+";", usuario_);
    }
    
    public static void eliminarCliente(Usuario usuario_) 
            throws Exception
    {
        if(usuario_.getRol().getPermiso(10) != 1)
        {
            System.out.println("No tiene permisos de acceso a esta seccion");
            return;
        }
        String filePath = "C:\\Users\\Sergio\\Documents\\Clase\\SSDLC\\SSDLC.p1\\src\\main\\java\\com\\mycompany\\ssdlc\\p1\\documents\\clientes.txt";
        Scanner sc = new Scanner(System.in);
        List<String> clientes = Files.readAllLines(Paths.get(filePath));
        List<String> clientesDecifrados = new ArrayList<>();

        for (String cliente : clientes) 
        {
            byte[] cifradoBytes = Base64.getDecoder().decode(cliente);
            clientesDecifrados.add(cipher.uncipher(cifradoBytes, cipherKey));
        }

        System.out.println("Lista de clientes:");
        for (int i = 0; i < clientesDecifrados.size(); i++) 
        {
            System.out.println((i + 1) + ". " + clientesDecifrados.get(i));
        }

        System.out.println("Introduzca el número del cliente que desea modificar:");
        int seleccion = sc.nextInt();
        sc.nextLine();

        clientes.remove(seleccion - 1);
        Files.write(Paths.get(filePath), clientes);

        checker.updateFile("clientes.txt");
        audit.save("Se eliminó el cliente: " + clientesDecifrados.get(seleccion - 1).toString(), usuario_);
    }
    
    private static void fileAdder(String filepath, String text) 
        throws Exception
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
        writer.write(text);
        writer.newLine();
        writer.close();
    }
}
