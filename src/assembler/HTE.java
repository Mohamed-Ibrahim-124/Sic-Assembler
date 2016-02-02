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

/**
 *
 * @author Mohamed Ibrahim
 */
public class HTE {

    public void run(String filename, String prograname, ArrayList<assembly> lines) throws FileNotFoundException {
        String H = headerrecord(prograname, lines.get(0).getLoccr(), lines.get(lines.size() - 1).getLoccr() - lines.get(0).getLoccr());
        String T = Textrecord(lines.get(0).getLoccr(), lines.get(lines.size() - 1).getLoccr() - lines.get(0).getLoccr(), lines);
        String E = Endrecord(lines.get(0).getLoccr());
        File file = new File(filename);
        PrintWriter out = new PrintWriter(file);
        out.write(H);
        out.write(T);
        out.write(E);
        out.close();

    }

    public String headerrecord(String prograname, Integer startaddress, Integer progralength) {
        return "H" + formatspaces(prograname, 6) + formatzeros(Integer.toHexString(startaddress), 6)
                + formatzeros(Integer.toHexString(progralength), 6) + "\n";
    }

    public String Endrecord(Integer startaddress) {
        return "E" + formatzeros(Integer.toHexString(startaddress), 6);
    }

    public String Textrecord(Integer startaddress, Integer progralength, ArrayList<assembly> lines) {
        int startT = startaddress;
        int countlines = 0;
        String textrec = "";

        while (true) {
            String codeline = "";
            while (countlines < lines.size()) {
                if (lines.get(countlines).getInstruction().equalsIgnoreCase("resw") || lines.get(countlines).getInstruction().equalsIgnoreCase("resb")) {
                    break;
                }
                if (lines.get(countlines).isflag() == false) {
                    countlines++;
                    continue;
                }
                if ((codeline.length() + lines.get(countlines).getObjectcode().length()) > 60) {
                    break;
                } else {
                    codeline += lines.get(countlines).getObjectcode();
                }
                countlines++;
            }
            String linelength = Integer.toHexString(codeline.length() / 2);
            textrec += "T" + formatzeros(Integer.toHexString(startT), 6) + formatzeros(linelength, 2) + codeline + "\n";
            if (countlines >= lines.size()) {
                break;
            }
            while (!lines.get(countlines).isflag()) {
                countlines++;
            }
            startT = lines.get(countlines).getLoccr();

        }
        return textrec;
    }

    String formatzeros(String string, int reqdig) {
        while (string.length() < reqdig) {
            string = "0" + string;
        }
        return string.toUpperCase();
    }

    String formatspaces(String string, int reqdig) {
        while (string.length() < reqdig) {
            string += " ";
        }
        return string;
    }

}
