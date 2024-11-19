package test;

import tm.TMSimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test{



    public static void main(String[]args) {

        TMSimulator tm = new TMSimulator();

        String filePath = "test/file0.txt";

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

        int alphabet = Integer.parseInt(partsList.get(1));

        for (int i = 0; i < alphabet+1; i++){
            tm.addSigma(i);
        }
        tm.setStart("0");
        tm.setFinal(String.valueOf(alphabet));

        System.out.println(tm.getAllStates());
        System.out.println(tm.getSigma());


    }
}



