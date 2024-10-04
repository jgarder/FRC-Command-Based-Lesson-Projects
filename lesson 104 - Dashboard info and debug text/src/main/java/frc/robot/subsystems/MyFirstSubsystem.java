package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MyFirstSubsystem extends SubsystemBase{
    //at the top of the class we alost always declare our variables and objects and anything we are using here. 
    
    private boolean doPrintPeriodic = true;//this is a flag we check, then we take action if it is true or false. 





    // this function has the SAME NAME and NO return type (since the return is the class object) as the class. 
    //it the constructor and will ALWAYS RUN on creation/instantiation of an object of this class
    //this will often be placed right below all the variables and objects declarations. after this method we place overridden methods. 
    public MyFirstSubsystem()
    {
        //code here will run once when an object of this class is created/instantiated.
        System.out.println("My subsystem that can do anything is booting up now!");
        //add a command that comes with the subsystem. it is searchable by the key so it shouldnt get lost.
        SmartDashboard.putData("TogglePeriodic", new InstantCommand(()->{doPrintPeriodic=!doPrintPeriodic;}));

    }
    int periodicCounter = 1;
    //after the constructor we often place overridden methods from base classes/super classes etc. 
    @Override
    public void periodic() 
    {
        periodicCounter++;
        //this code will run periodically (trying to run atleast every .02 of a second.)
       SmartDashboard.putBoolean("PutBoolean Print Periodic", doPrintPeriodic);

       if (doPrintPeriodic & (periodicCounter%100==0)) {
         System.out.println("Printing periodic is running! Counter: " + periodicCounter);
       }
    }

        
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.setSmartDashboardType("myFirstSubSys");

        builder.addBooleanProperty("Property Print Periodic", ()->{return doPrintPeriodic;},(catchABoolhere)->{doPrintPeriodic = catchABoolhere;});
    }


    //after our contructor method/function we place our utility methods/functions that do the bulk of the work on objects and variables.
    //Notice the return type here is void because when this function is called it does not return anything. even though it is directly modifying fields/variables inside our class! 
    public void setPrintPeriodic(boolean setToThis)
    {
        //set the main flag to what out local flag is. 
        // if someone calls setPrintPeriodic(false) then setToThis equals false. So  doPrintPeriodic = (False)
        doPrintPeriodic = setToThis;
        System.out.println("My subsystem method setPrintPeriodic to " + setToThis);
    }

    //this will return a command that is able to be scheduled and ran buy our command scheduler inside wpilib.
    //the method will just wrap the method "setPrintPeriodic" in a command and return it. and do nothing else. 
    public InstantCommand cmd_setPrintPeriodic(boolean setToThis)
    {
        //this will return a command that is able to be scheduled and ran buy our command scheduler inside wpilib.
        return new InstantCommand(()->{setPrintPeriodic(setToThis);});
    }
}
