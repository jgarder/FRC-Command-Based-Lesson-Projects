//package is where this is. its the type of thing in 2024 that the computer almost knows on its own. 
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
//imports are other libraries that we are going to use from around our code library. 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyFirstSubsystem;

public class cmdPrintToRioLog extends Command{

    
    private String whatToPrint;
    private final Timer m_Timer = new Timer();
    private double TimeoutSeconds = 0;

    public cmdPrintToRioLog(String whatToPrint)
    {
        this.whatToPrint = whatToPrint;
        System.out.println("cmdPrintToRioLog object created");
    }
    //THIS IS A SECOND/ OPTIONAL CONSTRUCTOR! NOTICE THAT IS HAS A DIFFERENT SIGNATURE (arguments to use the method)
    public cmdPrintToRioLog(String whatToPrint,double timeout)
    {
        this.whatToPrint = whatToPrint;
        TimeoutSeconds = timeout;
        System.out.println("cmdPrintToRioLog object created with a timeout of " + timeout);
    }

    @Override
  public void initialize() {
    m_Timer.restart();
    //this runs after the constructor argument! and anytime the command is scheduled
    System.out.println("cmdPrintToRioLog object intialized");

    //this is where we do many of our first step actions that are not repeated (like motor and goal setup)
    System.out.println("PRINT Msg --> " + whatToPrint);
  }

  @Override
  public void execute() {
   System.out.println("cmdPrintToRioLog object Executing");
  }

      // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("cmdPrintToRioLog object Ending!");
    //super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    System.out.println("cmdPrintToRioLog object Checking If Finished!");
    if(TimeoutSeconds == 0)
    {
      //RETURN TRUE! THAT MEANS THIS WHOLE COMMAND WILL BE OVER! THIS WILL STOP ALL THIS COMMANDS LOGIC FROM PROCESSING! 
      return true;
    }
    else
    {
      //if we have a timeout lets check if we are beyond it.
      if(m_Timer.get() > TimeoutSeconds){
        return true;
      }
      // it will get here after all other logic that does NOT return;
      //we have a timeout, but its not reached yet, so we are NOT finished yet, return false;
      return false;
    }
  }
}
