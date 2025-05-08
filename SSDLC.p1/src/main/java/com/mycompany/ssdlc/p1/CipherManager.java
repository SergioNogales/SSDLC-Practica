package com.mycompany.ssdlc.p1;

import java.io.File;

public class CipherManager {
    
    private SymetricCipher cipher = new SymetricCipher();
    
    public CipherManager()
    {
        
    }
    
    public byte[] cipher(String text, String key)
        throws Exception
    {
        return cipher.cipher(text, key);
    }
    
    public String uncipher(byte[] text, String key)
        throws Exception
    {
        return cipher.uncipher(text, key);
    }
}
