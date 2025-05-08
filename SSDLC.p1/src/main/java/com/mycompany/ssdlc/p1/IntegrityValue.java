package com.mycompany.ssdlc.p1;

public class IntegrityValue {
    
    public String name;
    public String checksum;
    
    public IntegrityValue(String name, String checksum){
        this.name = name;
        this.checksum = checksum;
    }
    
    @Override
    public String toString()
    {
        return name + checksum;
    }
}
