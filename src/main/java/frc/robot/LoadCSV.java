/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author vshah-21
 */
public class LoadCSV {
    ArrayList<String> headings = new ArrayList<String>();
    ArrayList<String> variables = new ArrayList<String>();
    public double[][] loadCSV(String fileName){
        ArrayList<String[]> points = new ArrayList<String[]>();
        
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int lines = 0;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] dataFromCSV = null;
                // use comma as separator
                try{
                    dataFromCSV = line.split(cvsSplitBy);
                    Double.parseDouble(dataFromCSV[1]);
                    points.add(dataFromCSV);
                }catch(Exception e){
                    if(dataFromCSV!=null){
                    if(dataFromCSV[0].contains("variables")){
                        System.out.println("hi variables are found ");
                        ArrayList<String> dummyString = new ArrayList<String>();
                        for(int i =0; i < dataFromCSV.length;i++){
                            if(dataFromCSV[i].length()>1){
                           
                                variables.add(dataFromCSV[i]);
                                System.out.println("additor adding");
                            }else{
                                System.out.println("additor sad");
                            }
                        }
                    }
                    else{
                    ArrayList<String> dummyString = new ArrayList<String>();
                        for(int i =0; i < dataFromCSV.length;i++){
                            if(dataFromCSV[i].length()>1){
                                headings.add(dataFromCSV[i]);
                                System.out.println("additor adding");
                            }else{
                                System.out.println("additor sad");
                            }
                        }
                    
                        }
                    }
                }
               
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
   
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return createPoints(points);
    }
    public String[] getVariables(){
        String[] headingss = new String[variables.size()];
         for(int i =0; i < headingss.length;i++){
            headingss[i] = headings.get(i);
            System.out.println("heading " + i + " equals " + headingss[i]);
        }
          
      
        return headingss;

    }
    public String[] getHeading(){
        String[] headingss = new String[headings.size()];
         for(int i =0; i < headingss.length;i++){
            headingss[i] = headings.get(i);
            System.out.println("heading " + i + " equals " + headingss[i]);
        }
          
      
        return headingss;
    }
    public double[][] createPoints(ArrayList<String[]> points){
        
        double[][] pointArrays = new double[points.size()][points.get(0).length];
        for(int i =0; i <points.size();i++){
            for(int j =0; j < points.get(i).length;j++){
                
                pointArrays[i][j] = Double.parseDouble(points.get(i)[j]);
            }
           
        }
        return pointArrays;
    }
}
