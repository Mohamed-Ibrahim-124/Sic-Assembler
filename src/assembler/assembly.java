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
public class assembly {

    private String operand;
    private String label;
    private String instruction;
    private int loccr;
    private String objectcode;
    private boolean flag;
    private String errors;

    public assembly(String operand, String label, String instruction, int loccr, String objectcode, boolean flag, String errors) {
        this.operand = operand;
        this.label = label;
        this.instruction = instruction;
        this.loccr = loccr;
        this.objectcode = objectcode;
        this.flag = flag;
        this.errors = errors;
    }

    public assembly() {

    }

    public String getErrors() {
        return errors;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getLabel() {
        return label;
    }

    public int getLoccr() {
        return loccr;
    }

    public String getObjectcode() {
        return objectcode;
    }

    public String getOperand() {
        return operand;
    }

    public boolean isflag() {
        return flag;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLoccr(int loccr) {
        this.loccr = loccr;
    }

    public void setObjectcode(String objectcode) {
        this.objectcode = objectcode;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }
}
