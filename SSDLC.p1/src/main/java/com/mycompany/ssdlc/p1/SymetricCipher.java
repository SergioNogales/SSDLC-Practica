package com.mycompany.ssdlc.p1;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SymetricCipher {
    
    private String key;
    
    public SymetricCipher(){
        /*
        Me decanto por un sistema de cifrado simétrico dado que estamos trabajando en un entorno de datos en reposo
        y de administradores controlados con la facilidad de tener que entregar pocas o ninguna clave debido a la automatización
        del sistema, los datos estarán cifrados en el documento de cara a atacantes pero el sistema internamente los 
        tendrá sin cifrar
        */
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    public byte[] cipher(String text, String key)
        throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
    {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec(key.getBytes());
        SecretKey ks = skf.generateSecret(kspec);
        Cipher cifrado = Cipher.getInstance("DES");

        cifrado.init(Cipher.ENCRYPT_MODE, ks);

        java.io.BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(text.getBytes()));
        byte[] aux = null;
        byte[] bloqueCifrado = null;
        int nb = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        do{
          aux = new byte[16];
          nb = bis.read(aux);
          if (nb>0)
          {
            bloqueCifrado = cifrado.update(aux, 0, nb);
            bos.write(bloqueCifrado, 0, bloqueCifrado.length);
          }
        }while (nb>0);
        bloqueCifrado = cifrado.doFinal();
        if (bloqueCifrado != null)
          bos.write(bloqueCifrado, 0, bloqueCifrado.length);

        StringBuilder sb = new StringBuilder();
        return bos.toByteArray();
    }
    
    public String uncipher(byte[] cipherText, String key)
        throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException
    {
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    DESKeySpec kspec = new DESKeySpec(key.getBytes());
    SecretKey ks = skf.generateSecret(kspec);
    Cipher cifrado = Cipher.getInstance("DES");

    cifrado.init(Cipher.DECRYPT_MODE, ks);
    java.io.BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(cipherText));
    byte[] aux = new byte[16];
    byte[] bloqueDescifrado = null;
    int nb = 0;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    do {
        aux = new byte[16];
        nb = bis.read(aux);
        if (nb > 0) {
            bloqueDescifrado = cifrado.update(aux, 0, nb);
            if (bloqueDescifrado != null) {
                bos.write(bloqueDescifrado, 0, bloqueDescifrado.length);
            }
        }
    } while (nb > 0);
    bloqueDescifrado = cifrado.doFinal();
    if (bloqueDescifrado != null) {
        bos.write(bloqueDescifrado, 0, bloqueDescifrado.length);
    }

    return new String(bos.toByteArray(), "UTF-8");
    }
    
}
