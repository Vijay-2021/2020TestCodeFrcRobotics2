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

public class centerArbWithTx extends CommandBase {
  /**
   * Creates a new centerArbWithTx.
   */
  basanShooter shooty;
  Limelight limes;
  DriveTrain trains;
  boolean txNavSame = false;
  int center = 0;
  boolean centered = false;
  public centerArbWithTx(basanShooter shoot, Limelight lime, DriveTrain train, int center) {
    shooty = shoot; 
    trains = train;
    limes=lime;
    SmartDashboard.putNumber("p value",0.02);
    this.center = side(center);
    // Use addRequirements() here to declare subsystem dependencies.
  }
  public int side(int center){

   if(shooty.angle()>0){
     return -center;
   }
  return +center;
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    


  }
  public int keepArbSign(int center, double input){
    if(input>center){
      return 1;
    }
    return -1;

  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    trains.arcadeDrive();
    double p = SmartDashboard.getNumber("p value", 0.0);
    double p2= SmartDashboard.getNumber("p2",0.0);
    double tx = limes.getTx();
    SmartDashboard.putNumber("tx", tx);
    if(Math.abs(Math.abs(tx)-center)>1){
      
      SmartDashboard.putString("State","centering tx to " + center);
      SmartDashboard.putNumber("value of arb p1 ",-keepArbSign(center,tx)*Math.max(0.2,p*Math.abs(Math.abs(tx)-center)));
      SmartDashboard.putNumber("value arb keepsign", keepArbSign(center,tx));
      trains.setTurn(-keepArbSign(center,tx)*Math.max(0.3,p*Math.abs(Math.abs(tx)-center)));

    }else if(Math.abs(Math.abs(tx)-center)>0.2){
      DriverStation.reportError("P command " + (keepArbSign(center,tx)*0.15) ,false);
      trains.setTurn(keepArbSign(center,tx)*p2*tx);
   }else{
    if(shooty.angle()==tx){
      txNavSame = true;
    }  
      centered = true;
   }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(center!=0){
    return txNavSame;
    }else{
      return centered; 
    }
  }
}
