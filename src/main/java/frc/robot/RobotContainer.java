/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.CenterTrainWithTx;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ResetNavx;
//import frc.robot.commands.LimeLight;
import frc.robot.commands.TurretCommand;
import frc.robot.commands.centerArbWithTx;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.basanShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final basanShooter shooty = new basanShooter();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Limelight lime = new Limelight();
  private final DriveTrain trains = new DriveTrain();
  
  Joystick leftJoystick;
  JoystickButton button1;
  JoystickButton button2;
  JoystickButton button3;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    double p = 0.03;
    SmartDashboard.putNumber("p value",p);  
    SmartDashboard.putNumber("p2",0.15);
    leftJoystick = new Joystick(0);
    button1 = new JoystickButton(leftJoystick,1);
    button2 = new JoystickButton(leftJoystick,2);
    button3 = new JoystickButton(leftJoystick,3);
    configureButtonBindings();
  }
  public double leftJoystickX(){
    SmartDashboard.putNumber("left x",leftJoystick.getRawAxis(1));
    return leftJoystick.getRawAxis(1);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  
    button1.toggleWhenPressed(new SequentialCommandGroup(new centerArbWithTx(shooty,lime,trains,0), new ParallelRaceGroup(new centerArbWithTx(shooty,lime,trains,20), new ArcadeDrive(this,trains)),new centerArbWithTx(shooty,lime,trains,0)));
    button3.toggleWhenPressed(new ArcadeDrive(this,trains));
    button2.whenPressed(new ResetNavx(shooty));
    
    
  }
  public void setCoast(){
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
