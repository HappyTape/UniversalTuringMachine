package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class UniversalTuringMachine {
    private int stepCounter = 0;
    private boolean isInStepMode;


    private final int blanksBeforeInput = 15;
    private final int blanksAfterInput = 300;

    private static final char BLANK = '_';
    private static final char ZERO = '0';

    private int state;
    private int readerPosition;
    private ArrayList<Transition> transitions;
    private char[] tape = new char[1000];

    public UniversalTuringMachine(ArrayList<Transition> transitions, boolean stepMode){
        this.transitions = transitions;
        this.isInStepMode = stepMode;
        state = 1;
    }

    private void initializeTape(String input){
        for (int i = 0; i < blanksBeforeInput; i++){
            this.tape[i]=BLANK;
        }
        for(int i = 0; i < input.length(); i++){
            this.tape[i + blanksBeforeInput] = input.charAt(i);
        }
        for(int i = 0; i < blanksAfterInput; i++ ){
            this.tape[i + blanksBeforeInput + input.length()] = BLANK;
        }
    }

    public void readTape(){
        String printTape = new String(tape);
        System.out.println(printTape);
    }

    public void calculate(String input){
        initializeTape(input);
        readerPosition = blanksBeforeInput;
        int numberOfTransitions = transitions.size();
        while (state != 2){
            int transitionsChecked = 0;
            for (Transition transition : transitions) {
                if (state == transition.currentState && tape[readerPosition] == transition.currentCharacter){
                    stepCounter += 1;
                    tape[readerPosition] = transition.nextCharacter;
                    readerPosition += transition.movement;
                    state = transition.nextState;
                    printStep();
                    break;
                }
                transitionsChecked += 1;
                if (transitionsChecked == numberOfTransitions){
                    throw new IllegalStateException(
                            "Now Matching transition Found.\n Current State: " + state + "\nCurrent character: " + tape[readerPosition]);
                }
            }
        }
        String finalTape = new String(tape);
        System.out.println("Result tape state:\n"+ finalTape);
    }

    private void printStep(){
        System.out.println("Current state: " + this.state);
        String subTape = getSubTape();
        System.out.println("Current tape +/-15 from readerPosition:\n" + subTape );
        System.out.println("Current reader position: " + readerPosition);
        System.out.println("Character at reader position: " + tape[readerPosition]);
        System.out.println("Steps taken: " + stepCounter);
        System.out.println("\n");
        if (isInStepMode){
            stepMode();
        }
    }

    private void stepMode(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private String getSubTape(){
        int start = readerPosition - 15;
        if (start < 0){
            start = 0;
        }
        int end = readerPosition + 15;
        char[] markedArray = new char[tape.length];
        System.arraycopy(tape,0,markedArray,0, tape.length);
        markedArray[readerPosition] = 'X';

        char[] subArray = new char[end - start + 1];
        for (int i = 0; i < subArray.length; i++) {
            subArray[i] = markedArray[start + i];
        }
        String subTape = new String(subArray);
        return subTape;
    }
}