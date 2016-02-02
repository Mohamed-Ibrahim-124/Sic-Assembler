/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Mohamed Ibrahim
 */
public class Assembler {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        Filehandler x = new Filehandler();
        Pass1 pass1 = new Pass1();
        Pass2 pass2 = new Pass2();
        HTE gen = new HTE();
        ArrayList<String> a = x.readlines("srcfile");
        pass1.run(a);
        pass2.run(pass1.symtab, pass1.lines, pass1.littab);
        x.genelistfile("LISTFILE", pass1.lines);
        gen.run("OBJECTFILE", pass1.prograname, pass1.lines);

    }

}
