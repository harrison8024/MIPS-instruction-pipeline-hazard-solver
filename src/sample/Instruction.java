package sample;

public class Instruction {
    final private String inst;
    private String function;
    private String firstReg;
    private String secondReg;
    private String thirdReg;
    private String offset;
    public int onlyStall;
    final public static int MTOEX = 0;
    final public static int EXTOEX = 1;
    final public static int EXTOM = 2;
    final public static int MTOM = 3;

    public Instruction(String inst, int index){
        this.inst = inst;
        function = "";
        firstReg = "";
        secondReg = "";
        thirdReg = "";
        offset = "";
        onlyStall = 0;
        translate();
    }

    public void translate(){
        String copyInst = String.valueOf(this.inst);
        try{
            //get function
            function = copyInst.substring(0, copyInst.indexOf(" "));

            // cut function from copyInst
            copyInst = copyInst.substring(copyInst.indexOf(" ")+1, copyInst.length());

            //get first register
            firstReg = copyInst.substring(0, copyInst.indexOf(","));

            // cut first register from copyInst
            copyInst = copyInst.substring(copyInst.indexOf(" ")+1, copyInst.length());

            if(function.equals("sw") || function.equals("lw")){
                //get offset
                offset = copyInst.substring(0, copyInst.indexOf("("));
                //get second register
                secondReg = copyInst.substring(copyInst.indexOf("(")+1, copyInst.indexOf(")"));

            } else if(function.equals("add") || function.equals("sub")      ) {
                //get second register
                secondReg = copyInst.substring(0, copyInst.indexOf(","));

                thirdReg = copyInst.substring(copyInst.indexOf(" ")+1, copyInst.length());
            }
        }catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

    public String getInstruction(){ return this.inst;}

    public String getFunction(){ return this.function;}

    public String getFirstReg(){ return this.firstReg;}

    public String getSecondReg() { return secondReg; }

    public String getThirdReg() { return thirdReg; }

    public String getOffset(){ return offset; }

    public void setOnlyStall(int num){
        this.onlyStall = num;
    }

    public int getOnlyStall(){
        return this.onlyStall;
    }
}
