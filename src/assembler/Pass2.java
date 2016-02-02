/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mohamed Ibrahim
 */
public class Pass2 {

    public void run(HashMap<String, Integer> symtab, ArrayList<assembly> lines, HashMap<String, Literals> littable) {
        Optab optab = new Optab();
        String instCode;
        String opCode;
        for (int i = 0; i < lines.size(); i++) {
            opCode = "";
            instCode = "";
            if (lines.get(i).isflag() == false) {
                continue;
            }
            if (lines.get(i).getInstruction().equalsIgnoreCase("Word")) {
                opCode = Integer.toHexString(Integer.parseInt(lines.get(i).getOperand()));
                lines.get(i).setObjectcode(formatZeros(opCode, 6));

            } else if (lines.get(i).getInstruction().equalsIgnoreCase("Byte")) {
                if (lines.get(i).getOperand().startsWith("C'") || lines.get(i).getOperand().startsWith("c'")) {
                    String byteVal = lines.get(i).getOperand().substring(2,lines.get(i).getOperand().length()-1);
                    for(int j=0; j<byteVal.length();j++){
                        opCode += Integer.toHexString((int)(byteVal.charAt(j)));
                    }
                
                }
                if(lines.get(i).getOperand().startsWith("X'")|| lines.get(i).getOperand().startsWith("x'") ){
                    opCode= lines.get(i).getOperand().substring(2,lines.get(i).getOperand().length()-1);
                }
                lines.get(i).setObjectcode(opCode.toUpperCase());
                    
                }
            else if(lines.get(i).getInstruction().startsWith("=")){
                lines.get(i).setObjectcode(calculateliteralvalue(lines.get(i).getInstruction().toUpperCase()));
            }
            else{
                instCode=optab.optab.get(lines.get(i).getInstruction().toUpperCase());
            
            if(lines.get(i).getOperand().endsWith(",x")|| lines.get(i).getOperand().endsWith(",X")){
                if(lines.get(i).getOperand().startsWith("=")){
                    String a= lines.get(i).getOperand().substring(0, lines.get(i).getOperand().length()- 2);
                    String b= calculateliteralvalue(a);
                    Literals lit= littable.get(b);
                    opCode= Integer.toHexString(lit.getAddress()+ 32768);
                }
                else {
                    if(symtab.containsKey(lines.get(i).getOperand().substring(0, lines.get(i).getOperand().length()-2))){
                         opCode=Integer.toHexString(symtab.get(lines.get(i).getOperand().substring(0, lines.get(i).getOperand().length()- 2))+ 32768);
                }
                    else{
                        lines.get(i).setErrors("Operand is undefined");
                    }
                }
               
            }
            else if(lines.get(i).getInstruction().compareToIgnoreCase("Rsub")==0){
                opCode="0000";
            }
//////////////// Get value of Literal from littab for objcode
            else {
                if(lines.get(i).getOperand().startsWith("=")){
                    String a= lines.get(i).getOperand();
                    String b= calculateliteralvalue(a);
                    Literals lit= littable.get(b);
                    opCode= Integer.toHexString(lit.getAddress());
                }
            
            else{
                    if(symtab.containsKey(lines.get(i).getOperand())){
                opCode=Integer.toHexString(symtab.get(lines.get(i).getOperand()));
            }
                  else{
                        lines.get(i).setErrors("Operand is undefined");
                    }   
                }
            }
            opCode=formatZeros(opCode, 4);
            lines.get(i).setObjectcode((instCode+ opCode).toUpperCase());
            }
            
            
            }
    }
    String formatZeros(String string, int reqDigits) {
        while (string.length() < reqDigits) {
            string = "0" + string;
        }
        return string.toUpperCase();
    }
     public String calculateliteralvalue(String name){
        String value = "";
        if(name.contains("C'") || name.contains("c'")){
            String val= name.substring(3, name.length()-1);
            for(int k=0; k< val.length(); k++){
                value += Integer.toHexString((int) (val.charAt(k)));
            }
            
        }
        else if(name.contains("X'") || name.contains("x'") ){
            value += name.substring(3, name.length()-1);
        }  
         
       return value;
}

    void run(HashMap<String, Integer> symtab, ArrayList<assembly> lines) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
