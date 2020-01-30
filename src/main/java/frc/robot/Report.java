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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report{
     String vars[];
    
    public Report(String[] vars) {
        super();
        this.vars = vars;
    }
    /**
     * @return the id
     */
    public String returnCsvReport(FileWriter writer){
        String reported ="";
        for(int i =0; i < vars.length;i++){
            try {
                writer.append(vars[i]);
                reported += vars[i];
                if(i<vars.length-1){
                    writer.append(",");
                    reported += ",";
                }
            } catch (IOException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return reported;
    }
}

