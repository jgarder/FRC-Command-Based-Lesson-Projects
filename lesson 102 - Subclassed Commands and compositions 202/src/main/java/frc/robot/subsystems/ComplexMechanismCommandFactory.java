package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.commands.cmdPrintToRioLog;

public class ComplexMechanismCommandFactory {
    // here we collect all the references to our subsystems that we will build all of our commands out of. 
    // we are also including our "static" method commands that just print becuase the printing is just a fill in for the subsystems you WOULD use in a real bot. 
    CommandXboxController joystick;
    CommandSwerveDrivetrain drivetrain; 
    MyFirstSubsystem mFSS;

    //this is called complex but its a misnomer, its really just needs to be complex enough need its own class like this
    public ComplexMechanismCommandFactory(CommandXboxController joystick,CommandSwerveDrivetrain drivetrain, MyFirstSubsystem mFSS) {
        this.joystick = joystick;
        this.drivetrain = drivetrain;
        this.mFSS = mFSS;
    }

    /////////////////DRIVETRAIN PARTS ////////////////////////
    //this is used by the drivetrain and should maybe be moved to a place that makes more sense?
    public void defaultDriveCommand() {
        drivetrain.applyRequest(-joystick.getLeftY() ,-joystick.getLeftX(),-joystick.getRightX());
    }
    ////////////////////////////////////////////////////////

    public SequentialCommandGroup sequenceOfPrints() {
        return new InstantCommand(()->{System.out.println("B button on true");})
            .andThen(new cmdPrintToRioLog("button B While True 2"))
            .andThen(Commands.runOnce(()->{System.out.println("button b true 3");}));
    }

    public SequentialCommandGroup sequenceWithparallel() {
        return new cmdPrintToRioLog("pressing right POV and Right stick button together! sequence soon!")
            .alongWith(new WaitCommand(1))
                .andThen(new cmdPrintToRioLog("Sequence that fires after the 1 second - 1 "),
                new cmdPrintToRioLog("Sequence that fires after the 1 second - 2 "),
                new cmdPrintToRioLog("Sequence that fires after the 1 second - 3 "),
                new cmdPrintToRioLog("Sequence that fires after the 1 second - 4 ")
                    .alongWith(new cmdPrintToRioLog("Sequence that fires after the 1 second - 4 parallel! ")),
                new cmdPrintToRioLog("Sequence that fires after the 1 second - 5 "),
                new cmdPrintToRioLog("Sequence that fires after the 1 second - 6 "),
                new WaitCommand(1)
                    )
                    .andThen(Commands.print("This fires after the sequence before it. its another andthen outside the last alongwith or andthen"));
    }


    public ParallelRaceGroup printUntilTrigger() {
        return Commands.run(()->{System.out.println("button Y onTrue Run until press of left Trigger!!");})
            .until(()->joystick.leftTrigger().getAsBoolean());
    }


    public Command sequenceWithTimeouts() {
        return Commands.sequence(
        new cmdPrintToRioLog("button B While True 1"),//notice the LACK OF an extra parameter for timeout!  
        new cmdPrintToRioLog("button B While True 2",.2),//notice the number for timeouts! its using a different constructor!
        new cmdPrintToRioLog("button B While True 3",.3),
        new cmdPrintToRioLog("button B While True 4",.001),
        new cmdPrintToRioLog("button B While True 5",.5),
        new cmdPrintToRioLog("button B While True 6",.6)
        );
    }

}
