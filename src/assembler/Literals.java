/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

/**
 *
 * @author Mohamed Ibrahim
 */
public class Literals {

    private String name;
    private String value;
    private int address;
    private int length;

    public Literals(String name, String value, int address, int length) {
        this.address = address;
        this.length = length;
        this.name = name;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
