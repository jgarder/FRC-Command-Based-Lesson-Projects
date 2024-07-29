//Read the WPIlib to see how we added and are structuring this example project!
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants
//https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html#constants

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import frc.robot.generated.TunerConstants;

public class constants {

    public static final class CANivoreBus{
        public static final String kCANbusName = "8608ChassisCan";
        public static final int kPigeonCanId = 3;
    }

    public static class Rio{
        public static final String canBusName = "rio";
        public static final int CanID_motorController1 = 0;
    }

    public static class LimelightVision {
            public static final String LimelightName = "limelight";
            public static final String LimelightFrontName = "limelight-front";
    }

    public static class drivetrain{

        public static final double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
        public static final double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

         //this is used by the drivetrain and should maybe be moved to a place that makes more sense?
         public static final  SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric


           public static final  Telemetry logger = new Telemetry(MaxSpeed);       
        }
    
}
