// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;

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

public class Arm implements AutoCloseable {
  //name of this subsystem for dashboard labeling
  String className = this.getClass().getSimpleName();
  //default pids to be tuned from dashboard
  private double PGain = 0.1;
  private double IGain = 0.0;
  private double DGain = 0.0;

  // The arm gearbox represents a gearbox containing a single neo vortex.
  private final DCMotor m_armGearbox = DCMotor.getNeoVortex(2);
  private double gearboxReduction = 25;

  // Standard classes for controlling our arm
  private final SparkMax m_motor = new SparkMax(Constants.kMotorPort, SparkMax.MotorType.kBrushless);
  private final SparkMaxSim m_Simmotor = new SparkMaxSim(m_motor, m_armGearbox);
  private SparkMaxConfig motorConfig;
  private SparkClosedLoopController SparkMaxBuiltInPidController = m_motor.getClosedLoopController();
  
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
  }

  /** Update the simulation model. */
  public void simulationPeriodic() {
    
    // In this method, we update our simulation of what our arm is doing
    // First, we set our "inputs" (voltages)
    m_armSim.setInput(m_Simmotor.getAppliedOutput() * RobotController.getBatteryVoltage());

    // Next, we update it. The standard loop time is 20ms.
    m_armSim.update(0.020);

    // Update the Mechanism 2d arm angle
     // Now, we update the Spark Flex
     m_Simmotor.iterate(
      Units.radiansPerSecondToRotationsPerMinute( // motor velocity, in RPM
          m_armSim.getVelocityRadPerSec()),
      RoboRioSim.getVInVoltage(), // Simulated battery voltage, in Volts
      0.02); // Time interval, in Seconds

    // SimBattery estimates loaded battery voltages
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(m_armSim.getCurrentDrawAmps()));

    // Update the Mechanism Arm angle based on the simulated arm angle
    m_arm.setAngle(Units.radiansToDegrees(m_armSim.getAngleRads()));
    pidtune();

    SmartDashboard.putNumber(className + " Motor Rotations", getPosition());
    SmartDashboard.putNumber(className + " Motor Amps", m_Simmotor.getMotorCurrent());
    SmartDashboard.putNumber(className + " Gearbox Amps", m_armSim.getCurrentDrawAmps());
  }

  public void setPosition(double rotations) {
    SmartDashboard.putNumber(className + " Setpoint", rotations);
    SparkMaxBuiltInPidController.setReference(rotations, ControlType.kPosition, ClosedLoopSlot.kSlot0);
  }

  public double getPosition() {
    return m_motor.getEncoder().getPosition();
  }

  //disabled motors output entirely. Arm will go limp. heat will stop. but encoder will still work.
  public void stop() {
    SparkMaxBuiltInPidController.setReference(0.0, ControlType.kDutyCycle); // set motor output to 0% by overriding any control mode like pid and going to duty cycle mode
  }

  public void pidtune() {
    // get the latest user written values from the dashboard for the PID values.
    // these are local and are created/destroyed each time this function is called
    double p = SmartDashboard.getNumber(className + " P Gain", 0);
    double i = SmartDashboard.getNumber(className + " I Gain", 0);
    double d = SmartDashboard.getNumber(className + " D Gain", 0);

    // if the value has changed, update the local variable AND controller with the new value.
    if ((p != PGain)) {
      PGain = p;
      motorConfig.closedLoop.p(p, ClosedLoopSlot.kSlot0);
      m_motor.configureAsync(motorConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    if ((i != IGain)) {
      IGain = i;
      motorConfig.closedLoop.i(i, ClosedLoopSlot.kSlot0);
      m_motor.configureAsync(motorConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    if ((d != DGain)) {
      DGain = d;
      motorConfig.closedLoop.d(d, ClosedLoopSlot.kSlot0);
      m_motor.configureAsync(motorConfig,ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
    /*
     * Create a new SPARK MAX configuration object. This will store the
     * configuration parameters for the SPARK MAX that we will set below.
     */
    motorConfig = new SparkMaxConfig();
    /*
     * Configure soft limits. These limits will prevent the motor from moving
     * beyond the specified positions.
     */
    motorConfig.softLimit.forwardSoftLimit(MaxPostition).reverseSoftLimit(MinPostiion);

    // Invert Motor Direction (if needed)
    motorConfig.inverted(MotorInverted);
    /*
     * Configure the encoder. For this specific example, we are using the
     * integrated encoder of the NEO, and we don't need to configure it. If
     * needed, we can adjust values like the position or velocity conversion
     * factors.
     */
    motorConfig.encoder.positionConversionFactor(1).velocityConversionFactor(1);

    /*
     * Configure the closed loop controller. We want to make sure we set the
     * feedback sensor as the primary encoder.
     */
    motorConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(PGain)
        .i(IGain)
        .d(DGain)
        .outputRange(-1, 1);

    /*
     * Apply the configuration to the SPARK MAX.
     *
     * kResetSafeParameters is used to get the SPARK MAX to a known state. This
     * is useful in case the SPARK MAX is replaced.
     *
     * kPersistParameters is used to ensure the configuration is not lost when
     * the SPARK MAX loses power. This is useful for power cycles that may occur
     * mid-operation.
     */
    m_motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
