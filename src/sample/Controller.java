package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    // Window Items
    @FXML
    private TextArea textArea;
    @FXML
    private MenuItem runButton;

    //Upper table
    @FXML
    private TableView<Pipeline> upperTable;
    @FXML
    private TableColumn<Pipeline, String> instruction_upper;
    @FXML
    private TableColumn<Pipeline, String> c1_upper;
    @FXML
    private TableColumn<Pipeline, String> c2_upper;
    @FXML
    private TableColumn<Pipeline, String> c3_upper;
    @FXML
    private TableColumn<Pipeline, String> c4_upper;
    @FXML
    private TableColumn<Pipeline, String> c5_upper;
    @FXML
    private TableColumn<Pipeline, String> c6_upper;
    @FXML
    private TableColumn<Pipeline, String> c7_upper;
    @FXML
    private TableColumn<Pipeline, String> c8_upper;
    @FXML
    private TableColumn<Pipeline, String> c9_upper;
    @FXML
    private TableColumn<Pipeline, String> c10_upper;
    @FXML
    private TableColumn<Pipeline, String> c11_upper;
    @FXML
    private TableColumn<Pipeline, String> c12_upper;
    @FXML
    private TableColumn<Pipeline, String> c13_upper;
    @FXML
    private TableColumn<Pipeline, String> c14_upper;
    @FXML
    private TableColumn<Pipeline, String> c15_upper;

    //Lower Table
    @FXML
    private TableView<Pipeline> lowerTable;
    @FXML
    private TableColumn<Pipeline, String> instruction_lower;
    @FXML
    private TableColumn<Pipeline, String> c1_lower;
    @FXML
    private TableColumn<Pipeline, String> c2_lower;
    @FXML
    private TableColumn<Pipeline, String> c3_lower;
    @FXML
    private TableColumn<Pipeline, String> c4_lower;
    @FXML
    private TableColumn<Pipeline, String> c5_lower;
    @FXML
    private TableColumn<Pipeline, String> c6_lower;
    @FXML
    private TableColumn<Pipeline, String> c7_lower;
    @FXML
    private TableColumn<Pipeline, String> c8_lower;
    @FXML
    private TableColumn<Pipeline, String> c9_lower;
    @FXML
    private TableColumn<Pipeline, String> c10_lower;
    @FXML
    private TableColumn<Pipeline, String> c11_lower;
    @FXML
    private TableColumn<Pipeline, String> c12_lower;
    @FXML
    private TableColumn<Pipeline, String> c13_lower;
    @FXML
    private TableColumn<Pipeline, String> c14_lower;
    @FXML
    private TableColumn<Pipeline, String> c15_lower;

    //TextFlow
    @FXML
    private TextFlow textFlow;
    @FXML
    private TextFlow forwardingText;

    //Array for instructions
    private ArrayList<Instruction> instructionArrayList;

    private ArrayList<Pipeline> upperPipelineArrayList;

    private ArrayList<Pipeline> lowerPipelineArrayList;

    //HazardMatch class
    public class HazardMatch{
        private int upperRegPosition;
        private int upperRegNum;
        private int lowerRegPosition;
        private int lowerRegNum;
        private int relationship;

        public HazardMatch(int up, int un, int lp, int ln, int r){
            upperRegPosition = up;
            upperRegNum = un;
            lowerRegPosition = lp;
            lowerRegNum = ln;
            relationship = r;
        }

        public int getLowerRegNum() {
            return lowerRegNum;
        }

        public int getLowerRegPosition() {
            return lowerRegPosition;
        }

        public int getRelationship() {
            return relationship;
        }

        public int getUpperRegNum() {
            return upperRegNum;
        }

        public int getUpperRegPosition() {
            return upperRegPosition;
        }
    }

    // Register class
    public class Register{
        private String name;
        private int regNum;
        public List<Integer> hazard;

        public Register(){
            this.name = "";
            this.regNum = -1;
            this.hazard = new ArrayList<>();
        }

        public Register(String name, int regNum){
            this.name = name;
            this.regNum = regNum;
            hazard = new ArrayList<>();
        }

        public void setHazard(int num){
            hazard.add(num);
        }
        public String getName() {
            return name;
        }

        public int getRegNum() {
            return regNum;
        }
    }
    private ArrayList<Register> registerArrayList;
    private ArrayList<Register> forwardRegList;
    private ArrayList<HazardMatch> hazardMatchArrayList;
    private Register previousReg;
    private Register prepreReg;



    // OnClick to run the program
    public void run(){
        // Initialized all Arraylist
        instructionArrayList = new ArrayList<>();
        upperPipelineArrayList = new ArrayList<>();
        lowerPipelineArrayList = new ArrayList<>();
        registerArrayList = new ArrayList<>();
        forwardRegList = new ArrayList<>();
        hazardMatchArrayList = new ArrayList<>();
        System.out.println("Running...");

        // Get each line from text area
        String text = textArea.getText();
        String[] lines = text.split("\n");

        // Get instructions into array
        for(int i=0; i < lines.length; i++) {
            instructionArrayList.add(new Instruction(lines[i], i));
        }

        // Checking for all Hazards
        for(int i=0; i < instructionArrayList.size(); i++){
            Instruction currentInst = instructionArrayList.get(i);
            if(i==0) {

                //first instruction
                registerArrayList.add(new Register(currentInst.getFirstReg(), 1));
            } else {
                // all sw
                if(currentInst.getFunction().equals("sw")){

                    // Function: SW
                    // Check for no forwarding
                    registerArrayList.add(new Register());
                    String reg1 = currentInst.getFirstReg();
                    String reg2 = currentInst.getSecondReg();
                    previousReg = registerArrayList.get(i-1);

                    // Check for repeating registers
                    if(reg1.equals(previousReg.getName())){

                        // first register == previous register
                        previousReg.setHazard(previousReg.getRegNum());
                        registerArrayList.get(i).setHazard(1);
                        currentInst.setOnlyStall(2);
                    } else if(reg2.equals(previousReg.getName())){

                        // second register == previous register
                        previousReg.setHazard(previousReg.getRegNum());
                        registerArrayList.get(i).setHazard(2);
                        currentInst.setOnlyStall(2);
                    }

                    // Checking instructions after the second
                    if(i > 1){

                        // get the one before previous register
                        prepreReg = registerArrayList.get(i-2);
                        if(reg1.equals(prepreReg.getName())){

                            // first register == previous register
                            prepreReg.setHazard(prepreReg.getRegNum());
                            registerArrayList.get(i).setHazard(1);

                            // Check if the the previous register has already been stalled
                            if(instructionArrayList.get(i-1).getOnlyStall() < 2 ){
                                currentInst.setOnlyStall(1);
                            }
                        } else if(reg2.equals(prepreReg.getName())){

                            // second register == previous register
                            prepreReg.setHazard(prepreReg.getRegNum());
                            registerArrayList.get(i).setHazard(2);

                            // Check if the the previous register has already been stalled
                            if(instructionArrayList.get(i-1).getOnlyStall() < 2 ){
                                currentInst.setOnlyStall(1);
                            }
                        }
                        if(reg1.equals(previousReg.getName()) || reg2.equals(previousReg.getName())){
                            currentInst.setOnlyStall(2);
                        }
                        // Check for forwarding

                    }
                } else {

                    // All other Functions: lw, add, sub
                    registerArrayList.add(new Register(currentInst.getFirstReg(), 1));
                    if(currentInst.getFunction().equals("lw")){

                        // Function: LW
                        //Checking for second register == previous register
                        String tempreg = currentInst.getSecondReg();
                        previousReg = registerArrayList.get(i-1);
                        if(tempreg.equals(previousReg.getName())){

                            // second register == previousReg
                            previousReg.setHazard(previousReg.getRegNum());
                            registerArrayList.get(i).setHazard(2);
                            currentInst.setOnlyStall(2);
                        }
                        if(i > 1 && tempreg.equals(registerArrayList.get(i-2))){

                            // second register == prepreReg && i > 1
                            prepreReg = registerArrayList.get(i-2);
                            prepreReg.setHazard(prepreReg.getRegNum());
                            registerArrayList.get(i).setHazard(2);

                            // Check if previous register has stalled
                            if(instructionArrayList.get(i-1).getOnlyStall() < 2){

                                // If stalled < 2 add one stall
                                currentInst.setOnlyStall(1);
                            }
                        }
                    } else {

                        // Fucntion: ADD, SUB
                        String tempreg1 = currentInst.getSecondReg();
                        String tempreg2 = currentInst.getThirdReg();
                        previousReg = registerArrayList.get(i-1);

                        // Checking for previous register
                        if(tempreg1.equals(previousReg.getName())){

                            // if second register == previousReg
                            previousReg.setHazard(previousReg.getRegNum());
                            registerArrayList.get(i).setHazard(2);
                            currentInst.setOnlyStall(2);

                        } else if(tempreg2.equals(previousReg.getName())){

                            // if second register == previousReg
                            previousReg.setHazard(previousReg.getRegNum());
                            registerArrayList.get(i).setHazard(3);
                            currentInst.setOnlyStall(2);
                        }
                        if(i > 1){

                            // Checking for one before previous register && i > 1
                            prepreReg = registerArrayList.get(i-2);
                            if(tempreg1.equals(prepreReg.getName())){

                                // if second register == prepreReg
                                prepreReg.setHazard(prepreReg.getRegNum());
                                registerArrayList.get(i).setHazard(2);

                                // Check if the previous register has already have stalled
                                if(instructionArrayList.get(i-1).getOnlyStall() < 2){
                                    currentInst.setOnlyStall(1);
                                }
                            } else if(tempreg2.equals(prepreReg.getName())){

                                // if third register == prepreReg
                                prepreReg.setHazard(prepreReg.getRegNum());
                                registerArrayList.get(i).setHazard(3);

                                // Check if the previous register has already have stalled
                                if(instructionArrayList.get(i-1).getOnlyStall() < 2){
                                    currentInst.setOnlyStall(1);
                                }
                            }
                        }
                    }
                }
            }
            // Check for forwarding unit
            if(i==0){
                if(!currentInst.getFunction().equals("sw")){
                    forwardRegList.add(new Register(currentInst.getFirstReg(), 1));
                } else {
                    forwardRegList.add(new Register());
                }
            } else if(i==1){
                previousReg = forwardRegList.get(i-1);
                switch (currentInst.getFunction()){
                    case "sw":
                        forwardRegList.add(new Register());
                        if(currentInst.getFirstReg().equals(previousReg.getName())){
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.MTOM));
                            } else {
                                // function: add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.MTOEX));
                            }
                        } else if(currentInst.getSecondReg().equals(previousReg.getName())){
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOM));
                            } else {
                                // function: add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOEX));
                            }
                        }
                        break;
                    case "lw":
                        forwardRegList.add(new Register(currentInst.getFirstReg(), 1));
                        if(currentInst.getFirstReg().equals(previousReg.getName())){
                            if(!instructionArrayList.get(i-1).getFunction().equals("lw")){
                                // we do not care about the first register of lw
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.MTOEX));
                            }
                        } else if(currentInst.getSecondReg().equals(previousReg.getName())){
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOM));
                            } else {
                                // function: add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOEX));
                            }
                        }
                        break;
                    case "add":
                    case "sub":
                        forwardRegList.add(new Register(currentInst.getFirstReg(), 1));
                        if(currentInst.getFirstReg().equals(previousReg.getName())){
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.MTOEX));
                            } else {
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.EXTOEX));
                            }
                        } else if(currentInst.getSecondReg().equals(previousReg.getName())){
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOEX));
                            } else {
                                // function: add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOEX));
                            }
                        }
                        break;
                    default:
                        System.out.println("Function not avaliable: " + currentInst.getFunction());
                        break;
                }
            } else {
                previousReg = forwardRegList.get(i-1);
                prepreReg = forwardRegList.get(i-2);
                switch (currentInst.getFunction()){
                    case "sw":
                        forwardRegList.add(new Register());
                        if(currentInst.getFirstReg().equals(previousReg.getName())){
                            // Previous with 1st reg
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.MTOM));
                            } else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 1, Instruction.EXTOM));
                            }

                        } else if(currentInst.getSecondReg().equals(previousReg.getName())){    // Current second register

                            //Previous with 2nd reg
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOM));
                            } else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.EXTOM));
                            }
                        } else if(currentInst.getFirstReg().equals(prepreReg.getName())){
                            // Pre Pre with 1st reg
                            if(instructionArrayList.get(i-2).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 1, Instruction.MTOM));
                            }else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 1, Instruction.EXTOM));
                            }
                        } else if(currentInst.getSecondReg().equals(prepreReg.getName())){
                            // Pre Pre with 2nd reg
                            if(instructionArrayList.get(i-2).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 2, Instruction.MTOM));
                            }else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 2, Instruction.EXTOM));
                            }
                        }
                        break;
                    case "lw":
                        // Only need to check second Reg for lw
                        forwardRegList.add(new Register(currentInst.getFirstReg(), 1));
                        if(currentInst.getSecondReg().equals(previousReg.getName())){
                            //Previous
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOM));
                            } else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.EXTOM));
                            }
                            // Pre Pre
                            if(instructionArrayList.get(i-2).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 2, Instruction.MTOM));
                            }else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, prepreReg.getRegNum(), i, 2, Instruction.EXTOM));
                            }
                        }
                        break;
                    case "add":
                    case "sub":
                        previousReg = forwardRegList.get(i-1);
                        prepreReg = forwardRegList.get(i-2);
                        forwardRegList.add(new Register(currentInst.getFirstReg(), 1));

                        if(currentInst.getSecondReg().equals(previousReg.getName())){
                            // Previous with 2nd reg
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOEX));
                            } else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.EXTOEX));
                            }
                        } else if(currentInst.getThirdReg().equals(previousReg.getName())){    // Current second register

                            //Previous with 3rd reg
                            if(instructionArrayList.get(i-1).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.MTOM));
                            } else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, previousReg.getRegNum(), i, 2, Instruction.EXTOM));
                            }
                        } else if(currentInst.getSecondReg().equals(prepreReg.getName())){
                            // Pre Pre with 2nd reg
                            if(instructionArrayList.get(i-2).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 3, Instruction.MTOEX));
                            }else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 3, Instruction.EXTOEX));
                            }
                        } else if(currentInst.getThirdReg().equals(prepreReg.getName())){
                            // Pre Pre with 3rd reg
                            if(instructionArrayList.get(i-2).getFunction().equals("lw")){
                                hazardMatchArrayList.add(new HazardMatch(i-2, prepreReg.getRegNum(), i, 3, Instruction.MTOM));
                            }else {
                                // add, sub
                                hazardMatchArrayList.add(new HazardMatch(i-1, prepreReg.getRegNum(), i, 3, Instruction.EXTOM));
                            }
                        }
                        break;
                    default:
                        System.out.println("Function not avaliable: " + currentInst.getFunction());
                        break;
                }
            }

        }
        for(int ind=0; ind < hazardMatchArrayList.size(); ind++){
            System.out.println("Hazard Relationhsip" + hazardMatchArrayList.get(ind).getRelationship());
        }
        // Display Hazards
        textFlow.getChildren().clear();
        for(int index=0; index < instructionArrayList.size(); index++){
            Instruction currInst = instructionArrayList.get(index);
            Text text1 = new Text(currInst.getFunction() + " ");
            Text text2 = new Text(currInst.getFirstReg());
            if(registerArrayList.get(index).hazard.contains(1)){
                text2.setFill(Color.RED);
            }
            Text text3;
            Text text4;
            Text text5;
            Text text6;

            if(currInst.getFunction().equals("lw") || currInst.getFunction().equals("sw")){
                text3 = new Text(", " + currInst.getOffset()+ "(");
                text4 = new Text(currInst.getSecondReg());
                if(registerArrayList.get(index).hazard.contains(2)){
                    text4.setFill(Color.RED);
                }
                text5 = new Text(")\n");
                textFlow.getChildren().addAll(text1, text2, text3, text4, text5);
            } else {
                text3 = new Text(", ");
                text4 = new Text(currInst.getSecondReg());
                if(registerArrayList.get(index).hazard.contains(2)){
                    text4.setFill(Color.RED);
                }
                text5 = new Text(", ");
                text6 = new Text(currInst.getThirdReg());
                if(registerArrayList.get(index).hazard.contains(3)){
                    text6.setFill(Color.RED);
                }
                Text text7 = new Text("\n");
                textFlow.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7);
            }
        }

        //Display pipeline
        String pipelineText = "FDXMW";
        String pipelineText2 = "FDXMW";
        int dPosition = 0;
        for(int i=0; i < instructionArrayList.size(); i++){
            Instruction item = instructionArrayList.get(i);
            Pipeline pipeline;
            if(i==0){
                pipeline = new Pipeline(item.getInstruction(), "FDXMW");
                upperPipelineArrayList.add(pipeline);
                dPosition++;
            } else {
                if(i == 1){
                    pipelineText = " " + pipelineText;
                    if(item.getOnlyStall() == 2){
                        pipelineText = pipelineText.replace("F", "FSS");
                        dPosition =3;
                    } else if(item.getOnlyStall() == 1){
                        pipelineText = pipelineText.replace("F", "FS");
                        dPosition=2;
                    } else {
                        dPosition = 1;
                    }
                    pipeline = new Pipeline(item.getInstruction(), pipelineText);
                } else if(i == 2){
                    pipelineText = pipelineText.replaceAll("S", "");
                    for(int j=0; j < dPosition; j++){
                        pipelineText = " "+pipelineText;
                    }
                    if(item.getOnlyStall() == 2){
                        pipelineText = pipelineText.replace("F", "FSS");
                        dPosition=3;
                    } else if(item.getOnlyStall() == 1){
                        pipelineText = pipelineText.replace("F", "FS");
                        dPosition=2;
                    } else {
                        dPosition = 1;
                    }
                    pipeline = new Pipeline(item.getInstruction(), pipelineText);
                } else {
                    pipelineText = pipelineText.replaceAll("S", "");
                    for(int j=0; j < dPosition; j++){
                        pipelineText = " "+pipelineText;
                    }
                    if(item.getOnlyStall() == 2){
                        pipelineText = pipelineText.replace("F", "FSS");
                        dPosition=3;
                    } else if(item.getOnlyStall() == 1){
                        pipelineText = pipelineText.replace("F", "FS");
                        dPosition=2;
                    } else {
                        dPosition=1;
                    }
                    pipeline = new Pipeline(item.getInstruction(), pipelineText);
                }
                upperPipelineArrayList.add(pipeline);
            }
            // Adding lower table pipeline
            int stall = 1;
            pipelineText2 = pipelineText2.replaceAll("S", "");
            if(i <= hazardMatchArrayList.size() && i > 0) {
                HazardMatch hazardMatch = hazardMatchArrayList.get(i-1);
                System.out.println("Hazard Match posittion: " + i + " upper: " + hazardMatch.getUpperRegPosition() + " lower: " + hazardMatch.getLowerRegPosition() + " relationship: " + hazardMatch.getRelationship());
                if (hazardMatch.getLowerRegPosition() == i && hazardMatch.getLowerRegPosition() - hazardMatch.getUpperRegPosition() < 2) {
                    switch (hazardMatch.getRelationship()) {
                        case Instruction.MTOEX:
                        case Instruction.MTOM:
                            pipelineText2 = pipelineText2.replace("F", "FS");
                            stall++;
                            break;
                        case Instruction.EXTOEX:
                        case Instruction.EXTOM:
                            // No stalls
                            break;
                        default:
                    }
                }
            }
            lowerPipelineArrayList.add(new Pipeline(item.getInstruction(), pipelineText2));
            for(int j=0; j < stall; j++){
                pipelineText2= " " + pipelineText2;
            }
        }
        updateUpperTable();
        updateLowerTable();

        forwardingText.getChildren().clear();
        Text titleText = new Text("Forward Path\n");
        titleText.setUnderline(true);
        forwardingText.getChildren().add(titleText);
        // Update forwarding text
        for(HazardMatch match : hazardMatchArrayList){
            String relationText = "";
            switch (match.getRelationship()){
                case 0:
                    relationText = " M -> X ";
                    break;
                case 1:
                    relationText = " X -> X ";
                    break;
                case 2:
                    relationText = " X -> M ";
                    break;
                case 3:
                    relationText = " M -> M ";
                    break;
            }
            Text temptext = new Text("Inst" + (match.getUpperRegPosition()+1) + relationText + "Inst"+ (match.getLowerRegPosition()+1) + "\n");
            forwardingText.getChildren().add(temptext);
        }
    }

    public void updateUpperTable(){
        // Clear all values
        upperTable.getItems().clear();
        instruction_upper.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        c1_upper.setCellValueFactory(new PropertyValueFactory<>("c1"));
        c2_upper.setCellValueFactory(new PropertyValueFactory<>("c2"));
        c3_upper.setCellValueFactory(new PropertyValueFactory<>("c3"));
        c4_upper.setCellValueFactory(new PropertyValueFactory<>("c4"));
        c5_upper.setCellValueFactory(new PropertyValueFactory<>("c5"));
        c6_upper.setCellValueFactory(new PropertyValueFactory<>("c6"));
        c7_upper.setCellValueFactory(new PropertyValueFactory<>("c7"));
        c8_upper.setCellValueFactory(new PropertyValueFactory<>("c8"));
        c9_upper.setCellValueFactory(new PropertyValueFactory<>("c9"));
        c10_upper.setCellValueFactory(new PropertyValueFactory<>("c10"));
        c11_upper.setCellValueFactory(new PropertyValueFactory<>("c11"));
        c12_upper.setCellValueFactory(new PropertyValueFactory<>("c12"));
        c13_upper.setCellValueFactory(new PropertyValueFactory<>("c13"));
        c14_upper.setCellValueFactory(new PropertyValueFactory<>("c14"));
        c15_upper.setCellValueFactory(new PropertyValueFactory<>("c15"));
        ObservableList<Pipeline> observableList = FXCollections.observableArrayList();
        for(Pipeline item : upperPipelineArrayList){
            observableList.add(item);
        }
        upperTable.setItems(observableList);

    }

    public void updateLowerTable(){
        // Clear all values
        lowerTable.getItems().clear();
        instruction_lower.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        c1_lower.setCellValueFactory(new PropertyValueFactory<>("c1"));
        c2_lower.setCellValueFactory(new PropertyValueFactory<>("c2"));
        c3_lower.setCellValueFactory(new PropertyValueFactory<>("c3"));
        c4_lower.setCellValueFactory(new PropertyValueFactory<>("c4"));
        c5_lower.setCellValueFactory(new PropertyValueFactory<>("c5"));
        c6_lower.setCellValueFactory(new PropertyValueFactory<>("c6"));
        c7_lower.setCellValueFactory(new PropertyValueFactory<>("c7"));
        c8_lower.setCellValueFactory(new PropertyValueFactory<>("c8"));
        c9_lower.setCellValueFactory(new PropertyValueFactory<>("c9"));
        c10_lower.setCellValueFactory(new PropertyValueFactory<>("c10"));
        c11_lower.setCellValueFactory(new PropertyValueFactory<>("c11"));
        c12_lower.setCellValueFactory(new PropertyValueFactory<>("c12"));
        c13_lower.setCellValueFactory(new PropertyValueFactory<>("c13"));
        c14_lower.setCellValueFactory(new PropertyValueFactory<>("c14"));
        c15_lower.setCellValueFactory(new PropertyValueFactory<>("c15"));
        ObservableList<Pipeline> observableList = FXCollections.observableArrayList();
        for(Pipeline item : lowerPipelineArrayList){
            observableList.add(item);
        }
        lowerTable.setItems(observableList);
    }

}
