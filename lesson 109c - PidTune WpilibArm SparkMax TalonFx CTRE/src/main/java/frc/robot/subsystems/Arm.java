// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.CoastOut;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/*
 * THIS IS CURRENTLY BROKEN THE CALCULATIONS ARE WRONG IN THE SIM. its 95% complete but needs some fixes.
 */
public class Arm implements AutoCloseable {
  //name of this subsystem for dashboard labeling
  String className = this.getClass().getSimpleName();
  //default pids to be tuned from dashboard
  private double PGain = 1.0;
  private double IGain = 0.0;
  private double DGain = 0.0;
  private double MaxAccel = 0.0;
  private double MaxVelocity = 0.0;

  // The arm gearbox represents a gearbox containing a single neo vortex.
  private final DCMotor m_armGearbox = DCMotor.getKrakenX60Foc(1);
  private double gearboxReduction = 25;


  // Standard classes for controlling our arm
  private final TalonFX m_motor = new TalonFX(Constants.kMotorPort);
  private final TalonFXSimState m_Simmotor = m_motor.getSimState();
  private TalonFXConfiguration motorConfig = new TalonFXConfiguration();
  //control Request are used to set the type of control being used on the motor via the talonfx 
  private ControlRequest positionControlType = new TorqueCurrentFOC(0); //any Direct Known Subclasses found at : https://api.ctr-electronics.com/phoenix6/release/java/com/ctre/phoenix6/controls/ControlRequest.html

  //the built in PID controller on the Spark(Max Or Flex) motor controller, these will not take processing power from the roborio because they are running on the motor controller itself at a much higher loop rate (this is good for fast response and precision)
  
  // these positions are for the Soft limits. these are used by the motor controller to attempt to
  // control the movement range of the motor
  // These are often found by cold booting the mechanism to a known location (like a hard stop) and
  // setting that position as 0 (min position), then moving to the other end of travel and reading
  // the position (maxposition).
  private double MinPostiion = -0.1; // rotations
  private double MaxPostition = .8; // rotations

  // if the position lowers while moving away from start position (which should be the min position)
  // then you may need to invert the motor direction
  private boolean MotorInverted = false;

  //public static final double kArmReduction = 200;
  public static final double kArmMass = Units.lbsToKilograms(17.637); // Kilograms
  public static final double kArmLength = Units.inchesToMeters(30);
  public static final double kMinAngleRads = Units.degreesToRadians(-75);
  public static final double kMaxAngleRads = Units.degreesToRadians(255);

  // Simulation classes help us simulate what's going on, including gravity.
  // This arm sim represents an arm that can travel from -75 degrees (rotated down front)
  // to 255 degrees (rotated down in the back).
  private final SingleJointedArmSim m_armSim =
      new SingleJointedArmSim(
          m_armGearbox,
          gearboxReduction,
          SingleJointedArmSim.estimateMOI(kArmLength, kArmMass),
          kArmLength,
          kMinAngleRads,
          kMaxAngleRads,
          true,
          0,
          Constants.kArmEncoderDistPerPulse,
          0.0 // Add noise with a std-dev of 1 tick
          );

  // Create a Mechanism2d display of an Arm with a fixed ArmTower and moving Arm.
  private final Mechanism2d m_mech2d = new Mechanism2d(60, 60);// size of the mechanism 2d display
  private final MechanismRoot2d m_armPivot = m_mech2d.getRoot("ArmPivot", 30, 30); // position of the root of the tower
  private final MechanismLigament2d m_armTower = m_armPivot.append(new MechanismLigament2d("ArmTower", 30, -90)); // tower is fixed at -90 degrees (straight up)
  // The arm is appended to the top of the tower and moves based on the simulated arm angle
  private final MechanismLigament2d m_arm =
      m_armPivot.append(
          new MechanismLigament2d(
              "Arm",
              30,
              Units.radiansToDegrees(m_armSim.getAngleRads()),
              6,
              new Color8Bit(Color.kYellow)));

  /** Subsystem constructor. */
  public Arm() {

    // Put Mechanism 2d to SmartDashboard
    SmartDashboard.putData("Arm Sim", m_mech2d);
    m_armTower.setColor(new Color8Bit(Color.kBlue));

    SetupMotorConfig();
    setupTuningDashboardDefaults();
  }

  public void setupTuningDashboardDefaults()
  {
    SmartDashboard.setDefaultNumber(className + " P Gain", PGain);
    SmartDashboard.setDefaultNumber(className + " I Gain", IGain);
    SmartDashboard.setDefaultNumber(className + " D Gain", DGain);
    SmartDashboard.setDefaultNumber(className + " Max Accel", MaxAccel);
    SmartDashboard.setDefaultNumber(className + " Max Velocity", MaxVelocity);
  }

  /** Update the simulation model. */
  public void simulationPeriodic() {
    
    
 

    /* Pass the robot battery voltage to the simulated devices */
    m_Simmotor.setSupplyVoltage(RobotController.getBatteryVoltage());
    
    // In this method, we update our simulation of what our arm is doing
    // First, we set our "inputs" (voltages)
    /*
    * CTRE simulation is low-level, so SimState inputs
    * and outputs are not affected by user-level inversion.
    * However, inputs and outputs *are* affected by the mechanical
    * orientation of the device relative to the robot chassis,
    * as specified by the `orientation` field.
    *
    * WPILib expects +V to be forward. We have already configured
    * our orientations to match this behavior.
    */
    m_armSim.setInput(
      m_Simmotor.getMotorVoltage()
    );

    /*
      * Advance the model by 20 ms. Note that if you are running this
      * subsystem in a separate thread or have changed the nominal
      * timestep of TimedRobot, this value needs to match it.
      */
      // Next, we update it. The standard loop time is 20ms.
      m_armSim.update(0.020);

    /* Update all of our sensors. */
    final var leftPos = m_armSim.getAngleRads();
    // This is OK, since the time base is the same
    final var leftVel = Units.radiansPerSecondToRotationsPerMinute( // motor velocity, in RPM
    m_armSim.getVelocityRadPerSec());
    /*
     * update sim motor positions and velocities based on the mechanism
     */

    m_Simmotor.setRawRotorPosition(leftPos / (2.0 * Math.PI) * gearboxReduction);// convert radians to rotations and account for gearbox
    m_Simmotor.setRotorVelocity(leftVel * gearboxReduction); // convert to motor RPM and account for gearbox

    // SimBattery estimates loaded battery voltages
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(m_armSim.getCurrentDrawAmps()));

    // Update the Mechanism Arm angle based on the simulated arm angle
    m_arm.setAngle(Units.radiansToDegrees(m_armSim.getAngleRads()));
    pidtune();

    SmartDashboard.putNumber(className + " Motor Rotations", getPosition());
    SmartDashboard.putNumber(className + " Motor Amps", m_Simmotor.getTorqueCurrent());
    SmartDashboard.putNumber(className + " Gearbox Amps", m_armSim.getCurrentDrawAmps());
  }

  public void setPosition(double rotations) {
    SmartDashboard.putNumber(className + " Setpoint", rotations);
    m_motor.setControl(new PositionDutyCycle(rotations));
  }

  public double getPosition() {
    return m_motor.getPosition().getValueAsDouble();
  }

  //disabled motors output entirely. Arm will go limp. heat will stop. but encoder will still work.
  public void stop() {
    m_motor.setControl(new CoastOut()); // set motor output to 0% by overriding any control mode like pid and going to duty cycle mode
  }

  public void pidtune() {
    // get the latest user written values from the dashboard for the PID values.
    // these are local and are created/destroyed each time this function is called
    double p = SmartDashboard.getNumber(className + " P Gain", 0);
    double i = SmartDashboard.getNumber(className + " I Gain", 0);
    double d = SmartDashboard.getNumber(className + " D Gain", 0);
    double A = SmartDashboard.getNumber(className + " Max Accel", 0);
    double V = SmartDashboard.getNumber(className + " Max Velocity", 0);
    // if the value has changed, update the local variable AND controller with the new value.
    if ((p != PGain)) {
      PGain = p;
      motorConfig.Slot0.kP = PGain;
      m_motor.getConfigurator().apply(motorConfig);
    }
    if ((i != IGain)) {
      IGain = i;
      motorConfig.Slot0.kI = IGain;
      m_motor.getConfigurator().apply(motorConfig);
    }
    if ((d != DGain)) {
      DGain = d;
      motorConfig.Slot0.kD = DGain;
      m_motor.getConfigurator().apply(motorConfig);
    }
    if ((A != MaxAccel)) {
      MaxAccel = A;
      motorConfig.MotionMagic.MotionMagicAcceleration = MaxAccel;
      m_motor.getConfigurator().apply(motorConfig);
    }
    if ((V != MaxVelocity)) {
      MaxVelocity = V;
      motorConfig.MotionMagic.MotionMagicCruiseVelocity = MaxVelocity;
      m_motor.getConfigurator().apply(motorConfig);
    }
  }

  @Override
  public void close() {
    m_motor.close();
    m_mech2d.close();
    m_armPivot.close();
    m_arm.close();
  }

  // Configure the motor controller
  private void SetupMotorConfig() {
    // set motor inversion
    motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    // set soft limits
    motorConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    motorConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    motorConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = MaxPostition;
    motorConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = MinPostiion;

    // set pid values
    motorConfig.Slot0.kP = PGain;
    motorConfig.Slot0.kI = IGain;
    motorConfig.Slot0.kD = DGain;

    // set motion magic values
    motorConfig.MotionMagic.MotionMagicAcceleration = MaxAccel;
    motorConfig.MotionMagic.MotionMagicCruiseVelocity = MaxVelocity;

    // apply the configuration to the motor
    m_motor.getConfigurator().apply(motorConfig);
  }
}
