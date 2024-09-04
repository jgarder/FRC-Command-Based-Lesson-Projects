// Open Robotcontainer.java for the core of your code! and most task!
/*
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  instant command/RunOnce -> https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
 *  subsystems -> https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html#subsystems
 * 
 * lesson 2 - 1st subsystem and basic command compositions
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to a mentor unless asking a question during developement!
 * requirements to pass this lesson:
 *
 * Task 0 : Open the Subsystem folder then open MyFirstSubsystem.java and try to observe -> 
 *  -> in the subsystem you will see that when the object is created (at robot container bootup) the subsystem prints some text. find that in your terminal during bootup. struggling? read on.
 *  -> notice every subsystem has a public void periodic()  that also runs every 20 milliseconds. here we can check sensors or update dashboard items to alert the driver
 *  -> this periodic runs some code doesnt it? it only runs when the if doPrintPeriodic is "true".. how do we set it to false?
 *  -> there is methods at the bottom, one returns void and one returns a command. whats the difference? we need to know! 

 * Task 1 (viewed by Mentor): inline an instant command/RunOnce command to disable the periodic printing wehn the B buttons is pressed.
 *  -> this can be complex but we are skipping everything and just running code. to run a delegate we use this piece of code ()->{}
 *  -> code inside codeblocks {} will run when an instant command is called.
 *  -> code is not a command, so using Instantcommand(()->{}) we are just "wrapping" our code in a command so the scheduler will see it as a command.
 *  -> notice you need to wrap the method setPrintPeriodic(boolean setToThis) not the method that returns a command!
 * 
 * Task 2 (viewed by Mentor): Call a command from our first subsystem to set that periodic to false.
 *  -> find the method in the subsystem that we can use to disable the periodic printing! make it run using the A button
 *  -> you will need to pass a parameter of true or false to this method! its expecting a boolean called setToThis
 *  -> Run the command of the subsystem named objectOfMyFirstClass.  use the method and argument/parameter cmd_setPrintPeriodic(false)
 * 
 *  you can view the solutions folder if you give up, but the best option is to look at the WPIlib resources above 
 *    and look at the topic at hand.
 *   
 * 
*/