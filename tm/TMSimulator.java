package tm;

import java.util.*;

import java.util.stream.Collectors;


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

    public void printTransitionTable() {
        System.out.println("Transition Table:");
        TMState test = getState(startState);
//        System.out.println(test);
        for (Map.Entry<TMState, Map<Character, Transition>> stateEntry : transitionTable.entrySet()) {
            TMState currentState = stateEntry.getKey();
//            System.out.println(stateEntry);
            System.out.println("\nFrom State: " + currentState.getName());

            Map<Character, Transition> transitions = stateEntry.getValue();
            for (Map.Entry<Character, Transition> transitionEntry : transitions.entrySet()) {
                char readSymbol = transitionEntry.getKey();
                Transition transition = transitionEntry.getValue();
                System.out.printf("  Read: (%d) → State: %s, Write: %c, Move: %c%n",
                        (int)readSymbol,
                        transition.nextState.getName(),
                        transition.writeSymbol,
                        transition.direction);
            }
        }
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
        System.out.println("from state " + fromState);
        TMState tS = getState(toState);

        if (fS == null || tS == null){
            System.out.println("null while adding trans");
            return;
        }

        transitionTable.putIfAbsent(fS, new HashMap<>());

        Transition transition = new Transition(tS, write, direction);
        transitionTable.get(fS).put(read, transition);
        System.out.println("Keys in transition table: " +
                transitionTable.get(fS).keySet().stream()
                        .map(c -> String.valueOf((int)c))
                        .collect(Collectors.joining(", ")));



    }

    public ArrayList<Character> step(String input) {

        int headPosition = 1;
        ArrayList<Character> tape = new ArrayList<>();
        tape.add('0');

        for (char c : input.toCharArray()) {
            tape.add(c);
        }

        tape.add('0');

        TMState currentState = getState(startState);

        while (!finalStates.contains(currentState)) {
            char currentSymbol = tape.get(headPosition);
            char sym =(char)(currentSymbol - '0');
            System.out.println("Current State: " + currentState.getName());
            System.out.println("Current Symbol: " + currentSymbol);
            System.out.println("Current Symbol: after changing  " + sym);
            System.out.println("sym: " + tape.get(headPosition));

            Map<Character, Transition> stateTransitions = transitionTable.get(currentState);

            if (stateTransitions == null) {
                System.out.println("No valid transition found");
                break;
            }
//            for (Map.Entry<Character, Transition> transitionEntry : stateTransitions.entrySet()) {
//                char readSymbol = transitionEntry.getKey();
//                Transition transition = transitionEntry.getValue();
//                System.out.printf(" 123 Read: (%d) → State: %s, Write: %c, Move: %c%n",
//                        (int)readSymbol,
//                        transition.nextState.getName(),
//                        transition.writeSymbol,
//                        transition.direction);
//            }

            Transition transition = stateTransitions.get(sym);
            System.out.println(transition);
            System.out.println("tape before " + tape);
            System.out.println("write symbol..... " + transition.writeSymbol);
            System.out.println("direction.... " + transition.direction);
            System.out.println("head pos " + headPosition);
            tape.set(headPosition, transition.writeSymbol);

            switch (transition.direction) {
                case 'L' -> {
                    headPosition--;
                    System.out.println("switch for L worked");
                    if (headPosition < 0) {
                        tape.add(0, '0');
                        headPosition = 0;
                        System.out.println("tape moved left " + tape);
                    }
                }
                case 'R' -> {
                    headPosition++;
                    System.out.println("switch for R worked");
                    if (headPosition >= tape.size()) {
                        tape.add('0');
                        System.out.println("tape moved right " + tape);
                    }
                }
            }

            currentState = transition.nextState;
            System.out.println(" tape now " + tape);
        }
        return tape;
    }




    public void addSigma(int sym) {
        for (int i = 0; i < sym; i++) {
            sigma.add(sym);
        }


    }


    public TMState getNextState(TMState currentState, char symbol) {
        Map<Character, Transition> stateTransitions = transitionTable.get(currentState);

        if (stateTransitions != null && stateTransitions.containsKey(symbol)) {
            return stateTransitions.get(symbol).nextState;
        }

        return null;
    }
    public Set<Integer> getSigma() {
        return sigma;
    }

    public int getMaxSigma(){
        return Collections.max(sigma);
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