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
                    System.out.println(part);
                    partsList.add(part);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(partsList);


        int states = Integer.parseInt(partsList.get(0));

        for (int i = 0; i < states; i++){
            tm.addState(String.valueOf(i));
        }
        LinkedHashSet<TMState> idk = tm.getAllStates();
        System.out.println("sttates" + idk);

        int alphabet = Integer.parseInt(partsList.get(1));

        for (int i = 0; i < alphabet+1; i++){
            tm.addSigma(i);
        }
        System.out.println("sigma is " + tm.getSigma());
        // things to check what state we are in
        int index = 0;
        int sigmaIndex = 0;
        // if state has all sigmas then do stuff idk
        tm.setStart("0");
        tm.setFinal(String.valueOf(states - 1));
        for (int i = 2; i < partsList.size()-1; i++){
            if (sigmaIndex == tm.getMaxSigma()+1){
                sigmaIndex = 0;
                index++;
            }
            if (tm.isFinal(String.valueOf(index))){
                System.out.println("the index is " + index);
                break;
            }
            String[] parts = partsList.get(i).split(",");
//            System.out.println("parts " + Arrays.toString(parts));
//            System.out.println("parts 1 " + parts[0]);

            tm.addTransition(String.valueOf(index),parts[0],parts[2].charAt(0), (char) sigmaIndex,parts[1].charAt(0));
            sigmaIndex++;
            System.out.println("sigma " + sigmaIndex);




        }

        tm.printTransitionTable();


        System.out.println(tm.getAllStates());
        System.out.println(tm.getSigma());
        System.out.println(partsList.get(2));
        System.out.println("last part " + partsList.get(partsList.size()-1));

//        ArrayList<Character> tape = tm.step(partsList.get(partsList.size()-1));
        ArrayList<Character> tape = tm.step("");
        System.out.println(tape);


    }
}



