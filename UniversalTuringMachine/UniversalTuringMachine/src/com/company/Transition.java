package com.company;

public class Transition {
    public int currentState;
    public char currentCharacter;
    public int nextState;
    public char nextCharacter;
    public int movement;

    public Transition(int currentState, char currentCharacter, int nextState, char nextCharacter, int movement){
        this.currentState = currentState;
        this.currentCharacter = currentCharacter;
        this.nextState = nextState;
        this.nextCharacter = nextCharacter;
        this.movement = movement;
    }
}
