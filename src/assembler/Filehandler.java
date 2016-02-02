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
public class Filehandler {

    public ArrayList<String> readlines(String filename) {
        ArrayList<String> code = new ArrayList<>();

        try {
            File f = new File(filename);

            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                code.add(s.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filehandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return code;
    }

    public void genelistfile(String filename, ArrayList<assembly> lines) throws FileNotFoundException {

        PrintWriter printer = new PrintWriter(new File(filename));
        for (int i = 0; i < lines.size(); i++) {
            printer.println(String.format("%10s%10s%12s%10s%10s%20s",
                    Integer.toHexString(lines.get(i).getLoccr()),
                    lines.get(i).getLabel(),
                    lines.get(i).getInstruction(),
                    lines.get(i).getOperand(),
                    lines.get(i).getObjectcode(),
                    lines.get(i).getErrors()));
        }
        printer.close();
    }

}
