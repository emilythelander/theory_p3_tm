package test;

import tm.TMSimulator;
import tm.TMState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class test{



    public static void main(String[]args) {

        TMSimulator tm = new TMSimulator();

        String filePath = "test/file5.txt";

        List<String> partsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\s+");


                for (String part : parts) {
                    partsList.add(part);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        int states = Integer.parseInt(partsList.get(0));

        for (int i = 0; i < states; i++){
            tm.addState(String.valueOf(i));
        }
        LinkedHashSet<TMState> idk = tm.getAllStates();

        int alphabet = Integer.parseInt(partsList.get(1));

        for (int i = 0; i < alphabet+1; i++){
            tm.addSigma(i);
        }
        // things to check what state we are in
        int index = 0;
        int sigmaIndex = 0;
        // if state has all sigmas then do stuff idk
        tm.setStart("0");
        tm.setFinal(String.valueOf(states - 1));
        for (int i = 2; i < partsList.size(); i++){
            if (sigmaIndex == tm.getMaxSigma()+1){
                sigmaIndex = 0;
                index++;
            }

            if (tm.isFinal(String.valueOf(index))){
                break;
            }

            String[] parts = partsList.get(i).split(",");

            tm.addTransition(String.valueOf(index), parts[0], parts[2].charAt(0), (char) sigmaIndex, parts[1].charAt(0));
            sigmaIndex++;
        }
//        tm.printTransitionTable();


//        System.out.println(tm.getAllStates());
//        System.out.println(tm.getSigma());
//        System.out.println(partsList.get(2));
//        System.out.println("last part " + partsList.get(partsList.size()-1));

//        ArrayList<Character> tape = tm.step(partsList.get(partsList.size()-1));
        ArrayList<Character> tape = tm.step("");
        StringBuilder tapeString = new StringBuilder();
        for (char c : tape) {
            tapeString.append(c);
        }
        System.out.println(tapeString.toString());


    }
}


