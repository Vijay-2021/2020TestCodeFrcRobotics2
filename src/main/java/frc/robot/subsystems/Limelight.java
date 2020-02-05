/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  NetworkTable limes = NetworkTableInstance.getDefault().getTable("limelight"); 
  NetworkTableEntry tx = limes.getEntry("tx");
  NetworkTableEntry ty = limes.getEntry("ty");
  NetworkTableEntry thor = limes.getEntry("thor");
  NetworkTableEntry tvert = limes.getEntry("tvert");
  NetworkTableEntry tv = limes.getEntry("tv");
  public Limelight() {
    
  }
  public double getTvert(){
    return tvert.getDouble(0.0);

  }
  
  public double getThor(){
    return thor.getDouble(0.0);
  }
  public double getTx(){
    return tx.getDouble(0.0);
  }
  public boolean hasTarget(){
    return (tv.getDouble(0.0)==1);
  }
  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
}
