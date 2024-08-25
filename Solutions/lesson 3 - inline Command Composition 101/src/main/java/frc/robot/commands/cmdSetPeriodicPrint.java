//package is where this is. its the type of thing in 2024 that the computer almost knows on its own. 
package frc.robot.commands;

//imports are other libraries that we are going to use from around our code library. 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyFirstSubsystem;

public class cmdSetPeriodicPrint extends Command{

    private MyFirstSubsystem thisIsTheSubSystem;
    private boolean whatToSetthePeriodicPrintTo;
    public cmdSetPeriodicPrint(MyFirstSubsystem thisIsTheSubSystem, boolean whatToSetthePeriodicPrintTo)
    {
        this.thisIsTheSubSystem = thisIsTheSubSystem;
        this.whatToSetthePeriodicPrintTo = whatToSetthePeriodicPrintTo;
        addRequirements(thisIsTheSubSystem);
        System.out.println("cmdSetPeriodicPrint object created to set print to " + whatToSetthePeriodicPrintTo);
    }

    @Override
  public void initialize() {
    //this runs after the constructor argument! and anytime the command is scheduled
    System.out.println("cmdSetPeriodicPrint object intialized");

    //this is where we do many of our first step actions that are not repeated (like motor and goal setup)
    thisIsTheSubSystem.setPrintPeriodic(whatToSetthePeriodicPrintTo);
  }

  @Override
  public void execute() {
   System.out.println("cmdSetPeriodicPrint object Executing");
  }

      // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("cmdSetPeriodicPrint object Ending!");
    super.end(interrupted);
  }

  double TimeoutSeconds = .05;
  @Override
  public boolean isFinished() {
    
    System.out.println("cmdSetPeriodicPrint object Checking If Finished!");
    return true;
  }
}
