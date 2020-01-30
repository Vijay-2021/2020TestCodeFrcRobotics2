/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  Talon leftDrive;
  Talon rightDrive;
  double turn = 0;
  double speed=0;
  public DriveTrain() {
    leftDrive = new Talon(0);
    rightDrive = new Talon(1);
    Notifier notif;
  }
  public void arcadeDrive(){
    leftDrive.set(speed + turn);
    rightDrive.set(-speed + turn);
    SmartDashboard.putNumber("left setpoint " , speed + turn);
    SmartDashboard.putNumber("right setpoint", -speed + turn);
    SmartDashboard.putNumber("speed", speed);
    
  }
  public void setSpeed(double speedVal){
    speed = speedVal;
  }
  public void setTurn(double turnVal){
    turn = turnVal;
    SmartDashboard.putNumber("turn set to", turnVal);
  }
  @Override
  public void periodic() {
   
  }
}
