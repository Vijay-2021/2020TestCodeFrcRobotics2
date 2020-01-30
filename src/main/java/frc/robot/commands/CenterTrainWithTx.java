/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.basanShooter;

public class CenterTrainWithTx extends CommandBase {
  /**
   * Creates a new CenterTrainWithTx.
   */
  DriveTrain trains;
  Limelight limes;
  boolean centered = false; 
  boolean navCalced = false;
  boolean txNavSame = false;
  public int center = 0;
  basanShooter shooty;
  public CenterTrainWithTx(DriveTrain train, Limelight lime, basanShooter shooties) {
    shooty = shooties;
    trains = train;
    limes = lime;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    trains.arcadeDrive();
  }
  public double keepSign(double input){
    if(input>0){
      return 1;
    }
    return -1;
  }
  
  
  public void center(){
    double p = SmartDashboard.getNumber("p value", 0.0);
    double p2= SmartDashboard.getNumber("p2",0.0);
    double tx = limes.getTx();
    SmartDashboard.putNumber("tx", tx);
    if(Math.abs(Math.abs(tx))>1){
      SmartDashboard.putNumber("fuck you ",-keepSign(tx)*Math.max(0.3,p*Math.abs(Math.abs(tx))));
      SmartDashboard.putNumber("fuck", keepSign(tx));
      trains.setTurn(-keepSign(tx)*Math.max(0.3,p*Math.abs(Math.abs(tx))));
      trains.arcadeDrive();
      DriverStation.reportError("WE are in one" ,false);

    }else if(Math.abs(Math.abs(tx))>0.2){
      DriverStation.reportError("WE are in .2",false);
      trains.setTurn(keepSign(tx)*p2*tx);
      trains.arcadeDrive();
   }else{
     centered = true;
     //trains.arcadeDrive();
     trains.setSpeed(0);
     trains.setTurn(0);
   }

  }
  public double keepArbSign(int center, double input){
    if(input>center){
      return 1;
    }
    return -1;
  }
  public int side(int center){
       navCalced=true;
      if(shooty.angle()>0){
        return -center;
      }
     return +center;
  }
  public boolean navCenter(double d) {
    if (Math.abs(-shooty.angle() - d) < 1) {
      return true;
    }return false;
  }
  public void centerArb(int center){
    
   
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      center();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    trains.setSpeed(0);
    trains.setTurn(0);
    trains.arcadeDrive();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return centered;
  }
}
