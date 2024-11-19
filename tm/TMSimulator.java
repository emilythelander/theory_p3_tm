package tm;

import java.util.*;

public class TMSimulator {

    public class Transition{
        TMState nextState;
        char writeSymbol;
        char direction; // 'L' for left, 'R' for right, 'S' for stay

        Transition(TMState nextState, char writeSymbol, char direction) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.direction = direction;
        }
    }
    LinkedHashSet<Integer> sigma;
    LinkedHashSet<TMState> states;
    String startState;
    LinkedHashSet<TMState> finalStates;
    Map<TMState, Map<Character, Transition>> transitionTable;

    public TMSimulator() {

        sigma = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        startState = "";
        finalStates = new LinkedHashSet<>();
        transitionTable = new HashMap<>();
        sigma.add(0);

    }


    public boolean addState(String name) {
        for (TMState state : states) {
            if (state.getName().equals(name)) {
                return false;
            }
        }

        return states.add(new TMState(name));
    }


    public boolean setFinal(String name) {
        for (TMState state : states) {
            if (state.getName().equals(name)) {
                return finalStates.add(state);
            }
        }

        return false;
    }


    public boolean setStart(String name) {
        for (TMState state : states) {
            if (state.getName().equals(name)) {
                startState = name;
                return true;
            }
        }
        return false;
    }

    public void addTransition (String fromState, String toState, Character direction,
                               Character read, Character write){


        TMState fS = getState(fromState);
        TMState tS = getState(toState);

        transitionTable.putIfAbsent(fS, new HashMap<>());
        
        Transition transition = new Transition(tS, write, direction);
        transitionTable.get(fS).put(read, transition);


    }


    public void addSigma(int sym) {
        for (int i = 0; i < sym; i++) {
            sigma.add(sym);
        }


    }
    public Set<Integer> getSigma() {
        return sigma;
    }

    public boolean isFinal(String name) {
        for (TMState state : finalStates) {
            if (state.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStart(String name) {
        if (name.equals(startState)) {
            return true;
        }
        return false;
    }

    public TMState getState(String name) {
        for (TMState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        for (TMState state : finalStates) {
            if (state.getName().equals(name)) {
                return state;
            }
        }

        return null;
    }

    public LinkedHashSet<TMState> getAllStates(){
        return states;
    }

}
