/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.basanShooter;

public class TurretCommand extends CommandBase {
  /**
   * Creates a new TurretCommand.
   */
  basanShooter shoot;
  double setpoint =0.0;
  Limelight lime;
  public TurretCommand(basanShooter shoots, Limelight limes) {
    shoot = shoots;
    addRequirements(shoots);
    lime = limes;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoot.resetNavx();
    SmartDashboard.putNumber("setpoint",setpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    setpoint = SmartDashboard.getNumber("setpoint" ,0.0);

    shoot.setSpeed(setpoint);
    shoot.set();
    shoot.shoot();
    shoot.turretStop();
    double navxAndTx = shoot.angle() - lime.getTx();
    SmartDashboard.putNumber("nav angle" , shoot.angle());
    SmartDashboard.putNumber("tx ", lime.getTx());
    SmartDashboard.putNumber("tx + navx" , navxAndTx );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
