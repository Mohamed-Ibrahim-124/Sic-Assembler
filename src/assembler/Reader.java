/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Ibrahim
 */
public class Reader {

    public ArrayList<String> readLines(String fileName) {
        ArrayList<String> code = new ArrayList<>();
        try {

            File f = new File(fileName);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                code.add(s.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return code;
    }

    public void GenListFile(String fileName, ArrayList<assembly> lines) throws FileNotFoundException {
        /*  String locctr= "LOCCTR";
         String label= "LABEL";
         String instruction="INSTRUCTION";
         String operand= "OPERAND";
         String objCode= "OBJECT";
         String error= "ERROR MESSAGE";
         printer.println(String.format("%10s%10s%10s%10s%11s%20s", locctr, label, instruction, operand, objCode, error));
         printer.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
         */
        try (PrintWriter printer = new PrintWriter(new File(fileName))) {
            /*  String locctr= "LOCCTR";
             String label= "LABEL";
             String instruction="INSTRUCTION";
             String operand= "OPERAND";
             String objCode= "OBJECT";
             String error= "ERROR MESSAGE";
             printer.println(String.format("%10s%10s%10s%10s%11s%20s", locctr, label, instruction, operand, objCode, error));
            
             printer.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
             */
            String locctr = "";
            String label = "";
            String instruction = "";
            String operand = "";
            String objCode = "";
            String error = "";
            for (int i = 0; i < lines.size(); i++) {
                printer.println(String.format("%10s%10s%10s%10s%11s%20s",
                        Integer.toHexString(lines.get(i).getLoccr()),
                        lines.get(i).getLabel(),
                        lines.get(i).getInstruction(),
                        lines.get(i).getOperand(),
                        lines.get(i).getObjectcode(),
                        lines.get(i).getErrors()));
            }
        }
    }
}
