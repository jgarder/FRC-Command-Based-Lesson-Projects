package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//simple subsystem 
public class FakeMotorClass extends SubsystemBase{
    
    private double MotorSpeed;// this should be between -1.0 and 1.0. this represents full reverse to full foward with stop (nuetral) being 0.0

    public FakeMotorClass()
    {
        MotorSpeed = 0.0;
    }

    

    public void MotorForward()
    {
        MotorSpeed = 1.0;
    }
    public void MotorBackward()
    {
        MotorSpeed = -1.0;
    }
    public void MotorStop()
    {
        MotorSpeed = 0.0;
    }
    public void MotorCustomSpeed(double thisCustomSpeed)
    {
        MotorSpeed = thisCustomSpeed;
    }



    @Override
    public void periodic() {
        //check your dashboard to check the diagnostics at anytime and debug the robot while its running!
        SmartDashboard.putNumber("MotorSpeed", MotorSpeed);
    }



    @Override
    public void simulationPeriodic() {
    }
}
