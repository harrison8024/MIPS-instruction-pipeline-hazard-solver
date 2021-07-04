package sample;

import java.nio.charset.StandardCharsets;

public class Pipeline {
    private String instruction;
    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
    private String c6;
    private String c7;
    private String c8;
    private String c9;
    private String c10;
    private String c11;
    private String c12;
    private String c13;
    private String c14;
    private String c15;

    public Pipeline(String instruction, String c1, String c2, String c3, String c4, String c5,
                    String c6, String c7, String c8, String c9, String c10, String c11, String c12,
                    String c13, String c14, String c15){

        this.instruction = instruction;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c6 = c6;
        this.c7 = c7;
        this.c8 = c8;
        this.c9 = c9;
        this.c10 = c10;
        this.c11 = c11;
        this.c12 = c12;
        this.c13 = c13;
        this.c14 = c14;
        this.c15 = c15;
    }
    public Pipeline(String instruction, String entrieString){
        this.instruction = instruction;
        for(int i=0; i < entrieString.length(); i++){
            switch(i){
                case 0:
                    this.c1 = ""+entrieString.charAt(i);
                    break;
                case 1:
                    this.c2 = ""+entrieString.charAt(i);
                    break;
                case 2:
                    this.c3 = ""+entrieString.charAt(i);
                    break;
                case 3:
                    this.c4 = ""+entrieString.charAt(i);
                    break;
                case 4:
                    this.c5 = ""+entrieString.charAt(i);
                    break;
                case 5:
                    this.c6 = ""+entrieString.charAt(i);
                    break;
                case 6:
                    this.c7 = ""+entrieString.charAt(i);
                    break;
                case 7:
                    this.c8 = ""+entrieString.charAt(i);
                    break;
                case 8:
                    this.c9 = ""+entrieString.charAt(i);
                    break;
                case 9:
                    this.c10 = ""+entrieString.charAt(i);
                    break;
                case 10:
                    this.c11 = ""+entrieString.charAt(i);
                    break;
                case 11:
                    this.c12 = ""+entrieString.charAt(i);
                    break;
                case 12:
                    this.c13 = ""+entrieString.charAt(i);
                    break;
                case 13:
                    this.c14 = ""+entrieString.charAt(i);
                    break;
                case 14:
                    this.c15 = ""+entrieString.charAt(i);
                    break;
            }
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public String getC1() {
        return c1;
    }

    public String getC2() {
        return c2;
    }

    public String getC3() {
        return c3;
    }

    public String getC4() {
        return c4;
    }

    public String getC5() {
        return c5;
    }

    public String getC6() {
        return c6;
    }

    public String getC7() {
        return c7;
    }

    public String getC8() {
        return c8;
    }

    public String getC9() {
        return c9;
    }

    public String getC10() {
        return c10;
    }

    public String getC11() {
        return c11;
    }

    public String getC12() {
        return c12;
    }

    public String getC13() {
        return c13;
    }

    public String getC14() {
        return c14;
    }

    public String getC15() {
        return c15;
    }
}
