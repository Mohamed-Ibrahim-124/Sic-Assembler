/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 *
 * @author Mohamed Ibrahim
 */
public class Pass1 {

    HashMap<String, Integer> symtab;
    HashMap<String, Literals> littab;
    String prograname;
    ArrayList<assembly> lines;

    public void run(ArrayList<String> code) {
        int loccr = 0;
        Literals lit;
        String label;
        String instruction;
        String operand;
        int oldcounter = 0;
        lines = new ArrayList<>();
        symtab = new HashMap<>();
        littab = new HashMap<>();

        assembly currentLine = null;
        for (int i = 0; i < code.size(); i++) {
            label = "";
            instruction = "";
            operand = "";
            try {

                if (code.get(i).startsWith(".")) {
                    continue;
                }
                if (code.get(i).length() >= 8) {
                    label = code.get(i).substring(0, 8);
                }
                if (code.get(i).length() >= 15) {
                    instruction = code.get(i).substring(9, 15);
                } else {
                    instruction = code.get(i).substring(9);
                }
                if (code.get(i).length() >= 35) {
                    operand = code.get(i).substring(17, 35);

                } else {
                    operand = code.get(i).substring(17);
                }
                label = label.trim();
                instruction = instruction.trim();
                operand = operand.trim();
                if (instruction.equalsIgnoreCase("start")) {
                    loccr = Integer.parseInt(operand, 16);
                    prograname = label;
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    if (label.isEmpty()) {
                        currentLine.setErrors("  no name for the program");
                    }
                    if (!operand.matches(".*\\d+.*")) {
                        currentLine.setErrors("  operand is not found");
                    }
                } else if (instruction.equalsIgnoreCase("end")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    if (operand.isEmpty()) {
                        currentLine.setErrors("  operand is not found");
                    }
 //////// definition of literals after End
                    for (Map.Entry<String, Literals> entry : littab.entrySet()) {
                        lit = entry.getValue();
                        if (lit.getAddress() == -1) {
                            instruction = lit.getName();
                            label = "*";
                            operand = "";
                            lines.add(new assembly(operand, label, instruction, loccr, "", false, ""));
                            lit.setAddress(loccr);
                            loccr += lit.getLength();
                        }
                        continue;
                    }
                }
 //////// definition of literals after LtOrg
                else if (instruction.equalsIgnoreCase("ltorg")) {
                    lines.add(new assembly(operand, label, instruction, loccr, "", false, ""));
                    // definition of literals after Ltorg
                    for (Map.Entry<String, Literals> entry : littab.entrySet()) {
                        lit = entry.getValue();
                        if (lit.getAddress() == -1) {
                            instruction = lit.getName();
                            label = "*";
                            operand = "";
                            lines.add(new assembly(operand, label, instruction, loccr, "", false, ""));
                            lit.setAddress(loccr);
                            loccr += lit.getLength();
                        }
                    }
                    continue;
                } else if (instruction.equalsIgnoreCase("resw")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    if (!label.isEmpty()) {
                        symtab.put(label, new Integer(loccr));
                    } else {
                        currentLine.setErrors("  label is not found,Exit");
                        currentLine.setFlag(false);

                    }
                    if ((!operand.isEmpty()) && operand.matches(".*\\d+.*")) {
                        loccr = loccr + 3 * Integer.parseInt(operand);
                    } else {
                        currentLine.setErrors("  invalid format or format isn't foind");
                        currentLine.setFlag(false);
                    }
                } else if (instruction.equalsIgnoreCase("resb")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    if (!label.isEmpty()) {
                        symtab.put(label, new Integer(loccr));
                    }
                    loccr = loccr + Integer.parseInt(operand);
                } else if (instruction.equalsIgnoreCase("word")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", true, "");
                    if (!label.isEmpty()) {
                        symtab.put(label, new Integer(loccr));
                    }
                    loccr = loccr + 3;
                } ////// ORG adress modification
                else if (instruction.equalsIgnoreCase("org")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    if (!operand.isEmpty()) {
                        oldcounter = loccr;
                        loccr = symtab.get(operand.trim());
                    } else {
                        loccr = oldcounter;
                    }
                } ////// EQU directive
                else if (instruction.trim().equalsIgnoreCase("equ")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", false, "");
                    ArrayList<String> expoperands = inToPost(operand);
                    int f = postFixCalc(expoperands);
                    symtab.put(label, Integer.parseInt(operand, 16));
                    currentLine.setLoccr(Integer.parseInt(operand, 16));
                } else if (instruction.equalsIgnoreCase("byte")) {
                    currentLine = new assembly(operand, label, instruction, loccr, "", true, "");
                    if (!label.isEmpty()) {
                        symtab.put(label, new Integer(loccr));
                    }
                    if (operand.contains("C'") || operand.contains("c'")) {
                        loccr = loccr + operand.length() - 3;
                    } else {
                        loccr = loccr + 1;
                    }

                } else {
                    if (!label.isEmpty()) {
                        symtab.put(label, new Integer(loccr));
                    }
                    currentLine = new assembly(operand, label, instruction, loccr, "", true, "");

                }
            } catch (Exception e) {
                if (!instruction.trim().equalsIgnoreCase("equ")) {
                    currentLine.setErrors("ERROR!");
                }
            }
//////Literal operand extraction and calculation of it's value WITHOUT Address
            if (operand.startsWith("=")) {
                String value = "";
                if (operand.contains("C'") || operand.contains("c'")) {
                    String val = operand.substring(3, operand.length() - 1);
                    for (int k = 0; k < val.length(); k++) {
                        value += Integer.toHexString((int) (val.charAt(k)));
                    }

                } else if (operand.contains("X'") || operand.contains("x'")) {
                    value += operand.substring(3, operand.length() - 1);
                }
                if (!littab.containsKey(value)) {
                    lit = new Literals(operand, value, -1, value.length() / 2);
                    littab.put(value, lit);
                }
            }
            lines.add(currentLine);
            if (!(instruction.equalsIgnoreCase("org") || instruction.equalsIgnoreCase("equ") || instruction.equalsIgnoreCase("word") || instruction.equalsIgnoreCase("byte") || instruction.equalsIgnoreCase("resw") || instruction.equalsIgnoreCase("resb"))) {
                loccr = loccr + 3;
            }
        }
    }
///////// Expression Handling

    private ArrayList<String> inToPost(String exp) {
        ArrayList<String> operands = new ArrayList<>();
        Stack<String> st = new Stack<>();
        while (!exp.isEmpty()) {
            if (exp.startsWith("+") || exp.startsWith("-")) {
                st.push(exp.substring(0, 1));
                exp = exp.substring(1);
            } else {
                if (exp.contains("+") || exp.contains("-")) {
                    int a = exp.indexOf('+');
                    int b = exp.indexOf('-');

                    if (a < b && a > 0) {
                        operands.add(exp.substring(0, a));
                        exp = exp.substring(a);

                    } else {
                        operands.add(exp.substring(0, b));
                        exp = exp.substring(b);

                    }
                } else {
                    operands.add(exp);
                    break;
                }
            }
        }
        while (!st.isEmpty()) {
            operands.add(st.pop());
        }
        return operands;
    }

    private int postFixCalc(ArrayList<String> operands) {
        if (operands.size() == 1) {
            return symtab.get(operands.get(0));
        } else {
            Stack<Integer> st = new Stack<>();
            for (int i = 0; i < operands.size(); i++) {
                if (operands.get(i).equalsIgnoreCase("+")) {
                    st.push(st.pop() + st.pop());
                    
                } else if (operands.get(i).equalsIgnoreCase("-")) {
                    st.push(-st.pop() + st.pop());

                } else {
                    if (operands.get(i).matches("[0-9]+")) {
                        st.push(Integer.parseInt(operands.get(i)));
                    } else {
                        st.push(symtab.get(operands.get(i)));
                    }
                }
            }
            return st.pop();
        }
    }
}
