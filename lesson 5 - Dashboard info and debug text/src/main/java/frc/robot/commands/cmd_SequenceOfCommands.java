package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class cmd_SequenceOfCommands extends SequentialCommandGroup {

    public cmd_SequenceOfCommands()
    {
        //constructor code goes here
        //add commands to be ran sequentially
        this.addCommands(Commands.print("Firing cmd_SequenceOfCommands"));
        this.addCommands(
            new InstantCommand(()->{System.out.println("Instant command that executes a print command added to the sequence");}),
            thisFunctionReturnsACommand(),// <-- this is a local method that returns a command! its ran one time at the start and adds the returning command object to the list and holds it in memory. 
            new WaitCommand(.25),
            new ParallelCommandGroup(new WaitCommand(1),Commands.print("Printing in parallel but this will still get paused as a group!")),
            Commands.print("last print in sequence. has to wait for parallel group to ALL finish.")
            );
    }

    //This returns a command but could return a sequential or parallel or any other class that is a class extending "command" class
    public Command thisFunctionReturnsACommand()
    {
        return new cmdPrintToRioLog("printing under the order of class cmd_SequenceOfCommands");
    }
    
}
