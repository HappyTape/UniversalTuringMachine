package com.company;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //String additionConfig = "01010101001101001000101011000101000101011000100010000100010011000010100100010";
        String multiplicationConfig =
                "0101000100010011010001000000000001000101100010100010100110001000100001000100110000101000001000100" +
                "110000100010000000001000101100000101000001010011000001000100000010001001100000010100000010100" +
                "1100000010001000000010101100000001010000000101011000000010001000000001000101100000000101000000001010" +
                "11000000001000100001010011000000000101000000000101011000000000100010000000000100010" +
                "11000000000010100000000001010110000000000100010101001100000000000101000000000001010" +
                "11000000000001000100100010011";

        System.out.println("Current TM configuration:\n" + multiplicationConfig);
        System.out.println("Enter the input: ");
        String testInput = scanner.nextLine();
        ArrayList<Transition> transitions = readConfiguration(multiplicationConfig);
        UniversalTuringMachine UTM = new UniversalTuringMachine(transitions, false);
        UTM.calculate(testInput);
    }

  public static ArrayList<Transition> readConfiguration(String rawInput){
        ArrayList<Transition> transitions = new ArrayList<>();
        String[] rawTransitions = rawInput.split("11");

      for (String singleTransition : rawTransitions) {
          int currentState;
          char currentCharacter;
          int nextState;
          char nextCharacter;
          int movement;

          String[] rawArguments = singleTransition.split("1");
          if (rawArguments.length > 5){
              throw new IllegalStateException("More arguments in transition than expected " + rawArguments.length);
          }
          currentState = rawArguments[0].length();
          nextState = rawArguments[2].length();
          int currentCharacterLength = rawArguments[1].length();
          int nextCharacterLength = rawArguments[3].length();

          currentCharacter = setCharacter(currentCharacterLength);
          nextCharacter = setCharacter(nextCharacterLength);

          int movementLength = rawArguments[4].length();
          if (movementLength > 2 || movementLength < 1){
              throw new IllegalArgumentException("Length of movement Argument invalid: " + movementLength);
          }
          if (movementLength == 1){
              movement = -1;
          }
          else {
              movement = 1;
          }
          Transition newTransition = new Transition(currentState, currentCharacter, nextState, nextCharacter, movement);
          transitions.add(newTransition);

      }
      return transitions;
    }

    public static char setCharacter(int inputLength){
        char result = switch (inputLength) {
            case 1 -> '0';
            case 2 -> '1';
            case 3 -> '_';
            default -> throw new IllegalArgumentException("Unknown Character detected. Not configured for length: " + inputLength);
        };
        return result;
    }
}
