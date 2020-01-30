
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 package frc.robot;
/**
 *
 * @author vshah-21
 */
import java.io.FileWriter;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CSVClient{
    Thread thats; 
    Socket firstSocket;
    Scanner in;
    ArrayList<Report> report = new ArrayList();
    public CSVClient(String addr, int portNumber){
        try {
            firstSocket = new Socket(addr, portNumber);
            in = new Scanner(firstSocket.getInputStream());
            dumpOutput();
        } catch (IOException ex) {
            Logger.getLogger(CSVClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public void dumpOutput(){ 
        
        String lineFromServer;
        do{
           lineFromServer = in.nextLine();
           ArrayList<String> strings = new ArrayList<String>();
           char[] cs = lineFromServer.toCharArray();
           int lastIndex = 0;
           int saveI =0;
        System.out.println(lineFromServer.length());
        boolean firstfound = false;
        for(int i =0; i < lineFromServer.length();i++){
            saveI = i;
            try{
                if(i>0&&lineFromServer.charAt(i)==','){
                    if(!firstfound){
                        strings.add(lineFromServer.substring(lastIndex,i));
                        firstfound = true;
                        lastIndex =i;
                    }else{
                    System.out.println("hit" + lineFromServer.substring(lastIndex+1,i));
                    strings.add(lineFromServer.substring(lastIndex+1,i));
                    lastIndex=i;
                    }
                }
                
            }catch(NullPointerException e){
              System.out.println("exception at line " + i);  
            }
        }
        System.out.println("String sie" + strings.size());
        String[] s = new String[strings.size()];
        for(int i =0; i < s.length;i++){
            s[i] = strings.get(i);
        }
        report.add(new Report(s));
        }while(in.hasNext());{
        
       
    }
         String name = "Lrvgwpf"+ System.currentTimeMillis();
        writeCsvFile(name+".csv");
    }
    String filename = "";
    public String fileName(){
        System.out.println("D:\\MyProfile\\Documents\\NetBeansProjects\\GraphsForCSV\\"+filename);
        return "D:\\MyProfile\\Documents\\NetBeansProjects\\GraphsForCSV\\"+filename;
    }
    public void writeCsvFile(String fileName) {
       
        
        FileWriter fileWriter = null;
        FileWriter defaultWriter = null;
                 
        try {
            fileWriter = new FileWriter(fileName);
            filename = fileName;
             defaultWriter = new FileWriter("DefaultGraph.csv");//create a default and a logged graph 
             defaultWriter.append("\n");
            //Add a new line separator after the header
            fileWriter.append("\n");
             
            //Write ur report to csv
            for (Report reportss : report) {
                reportss.returnCsvReport(defaultWriter);
                defaultWriter.append("\n");
                reportss.returnCsvReport(fileWriter);
                fileWriter.append("\n");
            }
 
             
             
            System.out.println("CSV file was created successfully fasdlfjkasd");
             
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter ughhhhhhhhhhhhhhhhh we fail again");
            e.printStackTrace();
        } finally {//being responsible and closing stuff down like good programmer
             
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }
    }
    public static void main(String[] args) throws IOException {
      new CSVClient("10.58.95.2",5800);//LoCaL hoST
    }
}















