package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NavX;
import frc.robot.PID;
//import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;

public class basanShooter extends SubsystemBase{

	private Talon flywheelMotor;
	private Talon conveyorMotor;
	private Talon tornadoMotor;
	private Talon turret;
	private Solenoid hopperSolenoid;
	private double conveyorSpeed;
	private double tornadoSpeed;
	private boolean noSpeed;
	private boolean hopperState = false;
	private double lastFlip = 0;
	PowerDistributionPanel pdp;
	PID PID;
	Counter Counter;
	NavX nav;
	double Kp = 0.55;
	double Ki = 0.0006;
	double Kd = 0.00000005;
	double dV = 1;

	public basanShooter()
	{
		pdp = new PowerDistributionPanel();
		turret = new Talon(5);
		flywheelMotor = new Talon(2);
		conveyorMotor = new Talon(3);
		tornadoMotor = new Talon(4);
		hopperSolenoid = new Solenoid(4);

		PID = new PID(Kp, Ki, Kd, dV, false, 1, false);
		Counter = new Counter(4);
		Counter.setDistancePerPulse(1);
		nav = new NavX();
		noSpeed = false;
	}
	
	/**
	 * Shoot continously
	 */
	public void shoot(){
		conveyorSpeed = 1;
		tornadoSpeed = 1;
	}
	public double angle(){
		return nav.getAngle();
	}
	public void turretStop(){
		turret.set(0);	
	}
	
	/**
	 * Stop shooting
	 */
	public void stopShoot() {
		conveyorSpeed = 0;
		tornadoSpeed = 0;
		hopperState = false; 
	}
	public void resetNavx(){
		nav.reset();
	}
	ADIS16470_IMU im;
	/**
	 * Conveyor goes in reverse
	 */
	public void reverse() {
		conveyorSpeed = -1;
		tornadoSpeed = -1;
	}
	
	/**
	 * Return the speed of the fly wheel in RPM
	 * @return The speed, in RPM, of the fly wheel
	 */
	public double getSpeed() {
		double flywheelSpeed = Counter.getRate()*60;
		return flywheelSpeed;
	}
	

	/**
	 * Sets the target RPM of the flywheel
	 * @param Setpoint angular speed set in RPM
	 */
	public void setSpeed(double setpoint) {
		if(setpoint == 0) {
			noSpeed = true;
		} else
			noSpeed = false;
		PID.set(setpoint/60);
	}
	
	/**
	 * Tells whether flywheel speed is close to the setpoint
	 * @return Whether it's close or not
	 */
	
	public boolean atSpeed()
	{
		return ( getSpeed() < PID.getSetpoint()*60 + 60 && getSpeed() > PID.getSetpoint()*60 - 60 );	
	}
	
	
	public void set() {
		
		double output = -PID.getOutput(Counter.getRate());
		if(output > 0) {
			output = 0;
		}
		
		if(output < -0.75) {
			output = -0.75;
		}
		
		if(noSpeed == true) {
			flywheelMotor.set(0);
		} else
			flywheelMotor.set(output);
		
		if(conveyorSpeed > 0 && ((Timer.getFPGATimestamp() - lastFlip) > 1.0)) {
			hopperState = !hopperState;
			lastFlip = Timer.getFPGATimestamp();
		}
		
		if(hopperSolenoid.get() != hopperState) {
			hopperSolenoid.set(hopperState);
		}
		
		conveyorMotor.set(conveyorSpeed);
		tornadoMotor.set(tornadoSpeed);

	}

	
}