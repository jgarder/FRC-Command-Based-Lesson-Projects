package frc.robot.RevLibExamples;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

// by extending subsystem we can properly use the command scheduler requirements systems
// aswell the subsystem will automatically subscribe to the command scheduler
// and have a periodic method called every loop
public class RevClosedLoop extends SubsystemBase {
  private final SparkMax motor;
  private final SparkMaxSim motorSim;
  private SparkMaxConfig motorConfig;
  private final SparkClosedLoopController closedLoopController;
  private final RelativeEncoder encoder;

  String className = this.getClass().getSimpleName();
  private double PGain = 0.1;
  private double IGain = 0.0;
  private double DGain = 0.0;

  private double VelocityPGain = 0.0001;
  private double VelocityIGain = 0.0;
  private double VelocityDGain = 0.0;

  // these positions are for the Soft limits. these are used by the motor controller to attempt to
  // control the movement range of the motor
  // These are often found by cold booting the mechanism to a known location (like a hard stop) and
  // setting that position as 0 (min position), then moving to the other end of travel and reading
  // the position (maxposition).
  private double MinPostiion = 0.0; // rotations
  private double MaxPostition = 10.0; // rotations

  // if the position lowers while moving away from start position (which should be the min position)
  // then you may need to invert the motor direction
  private boolean MotorInverted = false;

  public RevClosedLoop(int deviceId) {

    /*
     * Initialize the SPARK MAX and get its encoder and closed loop controller
     * objects for later use.
     */
    motor = new SparkMax(1, MotorType.kBrushless);
    motorSim = new SparkMaxSim(motor, DCMotor.getNEO(1)); // THIS IS FOR SIMULATOR
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();

    SetupMotorConfig();

    // Initialize dashboard values
    SmartDashboard.setDefaultNumber("Target Position", 0);
    SmartDashboard.setDefaultNumber("Target Velocity", 0);
    SmartDashboard.setDefaultBoolean("Control Mode", false);
    SmartDashboard.setDefaultBoolean("Reset Encoder", false);

    SmartDashboard.setDefaultNumber(className + " P Gain", PGain);
    SmartDashboard.setDefaultNumber(className + " I Gain", IGain);
    SmartDashboard.setDefaultNumber(className + " D Gain", DGain);

    // Display encoder position and velocity
    SmartDashboard.setDefaultNumber(className + " Actual Position", 0);
    SmartDashboard.setDefaultNumber(className + " Actual Velocity", 0);
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
        .outputRange(-1, 1)
        // Set PID values for velocity control in slot 1
        .p(VelocityPGain, ClosedLoopSlot.kSlot1)
        .i(VelocityIGain, ClosedLoopSlot.kSlot1)
        .d(VelocityDGain, ClosedLoopSlot.kSlot1)
        .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1);

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
    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void setPosition(double rotations) {
    closedLoopController.setReference(rotations, ControlType.kPosition, ClosedLoopSlot.kSlot0);
  }

  public void setVelocity(double rpm) {
    closedLoopController.setReference(rpm, ControlType.kVelocity, ClosedLoopSlot.kSlot1);
  }

  public double getPosition() {
    return motor.getEncoder().getPosition();
  }

  public double getVelocity() {
    return motor.getEncoder().getVelocity();
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
      motorConfig.closedLoop.apply(motorConfig.closedLoop.p(p, ClosedLoopSlot.kSlot0));
    }
    if ((i != IGain)) {
      IGain = i;
      motorConfig.closedLoop.apply(motorConfig.closedLoop.i(i, ClosedLoopSlot.kSlot0));
    }
    if ((d != DGain)) {
      DGain = d;
      motorConfig.closedLoop.apply(motorConfig.closedLoop.d(d, ClosedLoopSlot.kSlot0));
    }
  }

  public void MovePid() {
    // this runs every control loop (20ms)
    if (SmartDashboard.getBoolean("Control Mode", true)) {
      /*
       * Get the target velocity from SmartDashboard and set it as the setpoint
       * for the closed loop controller.
       */
      double targetVelocity = SmartDashboard.getNumber("Target Velocity", 0);
      setVelocity(targetVelocity);
    } else {
      /*
       * Get the target position from SmartDashboard and set it as the setpoint
       * for the closed loop controller.
       */
      double targetPosition = SmartDashboard.getNumber("Target Position", 0);
      setPosition(targetPosition);
    }
  }

  @Override
  public void periodic() {
    // take user input on the dashboard and update motor controller with new pid
    pidtune();
    // if the setpoint or request has changed, change motor controller setpoint
    MovePid();

    // OTHER periodic tasks

    // Display encoder position and velocity
    SmartDashboard.putNumber(className + " Actual Position", encoder.getPosition());
    SmartDashboard.putNumber(className + " Actual Velocity", encoder.getVelocity());

    // simulator periodic
    if (Robot.isSimulation()) {
      Spark_simulationPeriodic();
      // SmartDashboard.putNumber(className + " Sim Position", motorSim.getPosition());
      // SmartDashboard.putNumber(className + " Sim Velocity", motorSim.getVelocity());
    }

    if (SmartDashboard.getBoolean("Reset Encoder", false)) {
      SmartDashboard.putBoolean("Reset Encoder", false);
      // Reset the encoder position to 0
      encoder.setPosition(0);
    }
  }

  public void Spark_simulationPeriodic() {
    // motorSim.setBusVoltage(RoboRioSim.getVInVoltage());
    // Now, we update the Spark Flex
    motorSim.iterate(
        motorSim.getVelocity(),
        RoboRioSim.getVInVoltage(), // Simulated battery voltage, in Volts
        0.02); // Time interval, in Seconds

    // SimBattery estimates loaded battery voltages
    // This should include all motors being simulated
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(motorSim.getBusVoltage()));
  }
}
