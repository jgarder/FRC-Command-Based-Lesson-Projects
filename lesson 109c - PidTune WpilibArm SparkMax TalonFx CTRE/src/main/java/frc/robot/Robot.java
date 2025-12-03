// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Arm;

/** This is a sample program to demonstrate the use of arm simulation with existing code. */
public class Robot extends TimedRobot {
  private final Arm m_arm = new Arm();
  private final XboxController m_joystick = new XboxController(Constants.kJoystickPort);

  public Robot() {}

  @Override
  public void simulationPeriodic() {
    m_arm.simulationPeriodic();
  }

  @Override
  public void teleopInit() {}

  public final double kArmSetpoint = 90;
  public final double kArmSetpoint2 = 45;
  public final double kArmSetpoint3 = -90;
  public final double kArmParkPoint = 0;

  public final double joystickmultiplier = 2;
  public final double joystickdeadband = 0.2;
  @Override
  public void teleopPeriodic() {
    if (m_joystick.getAButton()) {
      // Here, we run PID control like normal.
      m_arm.setPosition(kArmSetpoint);
    } 
    else if (m_joystick.getBButton()) {
      // Here, we run PID control like normal.
      m_arm.setPosition(kArmSetpoint2);
    } 
    else if (m_joystick.getXButton()) {
      // Here, we run PID control like normal.
      m_arm.setPosition(kArmSetpoint3);
    } 
    else if (m_joystick.getYButton()) {
      // Here, we run PID control like normal.
       m_arm.setPosition(kArmParkPoint);
    } 
    else if (m_joystick.getRightBumperButton()) {
      //disable the motor.
      m_arm.stop();
    }
    else if(m_joystick.getRightY() > joystickdeadband || m_joystick.getRightY() < -joystickdeadband) {
      m_arm.setPosition(m_arm.currentSetpointdegrees + m_joystick.getRightY()*joystickmultiplier);
    }
  }

  @Override
  public void close() {
    m_arm.close();
    super.close();
  }

  @Override
  public void disabledInit() {
    // This just makes sure that our simulation code knows that the motor's off.
    m_arm.stop();
  }
}
