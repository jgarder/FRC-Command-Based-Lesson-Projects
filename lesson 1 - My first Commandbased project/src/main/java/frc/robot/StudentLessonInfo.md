/* Open Robotcontainer.java for the core of your code! and most task!
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  binding commands to triggers -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#binding-commands-to-triggers
 *  Whiletrue trigger ->  https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#whiletrue
 *  CONTROLLER DOCUMENTATION -> Notice all the triggers to bind to! -> https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/CommandXboxController.html
 * 
 * lesson 1 - Your first Command based project and binding a command to a trigger. 
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to mentor unless asking a question during developement!
 * requirements to pass this lesson:
 *
 * Task 0 : Project should "Build successful" When shown to mentor
 * 
 * Task 1 : read the comments (green text) below to learn -> 
 *  -> this is the skeleton of a command based robot. take a few minutes to see how it expands on the timed robot
 *  -> robot.java on a command based robot will run the command scheduler periodically and runs the autonomous through the scheduler. 
 *  -> when public RobotContainer() is ran all that happens is configureBindings(); that is where we will be binding a trigger today
 *  
 * Task 2 (viewed by Mentor): Run the command m_exampleSubsystem.exampleMethodCommand() by pressing the b button. 
 *  Step 1 of "running" a command is to actually build the command 
 *  Step 2 is to bind that command to a trigger (something that is true or false)
 *  step 3 is now to simply make that trigger go "true". which will run the commands. 
 *  in our case our trigger is going to come from our controller on port 0 that we call m_driverController. 
 *  if we stop pressing the "A" button the trigger for "while false" that would become true would be  "m_driverController.a().Whilefalse(COMMANDS TO RUN GO HERE)"
 *   
 *  you can view the solutions folder if you give up, but the best option is to look at the WPIlib resources above 
 *    and look for WhileTrue and drop the examplemethodcommand into the arguments/parameters 
 *   
 * 
 * This is just a "skeleton", or "boilerplate" of a robots code. its all the stuff that every robot team has to have. 
 *  if a team runs a command based java then they run commands through the command scheduler. many teams have command based java projects, 
 *  but do not run commands.  preferring to instead check periodics (if xButtonPressed ==true then do something) which makes them in fact, not command based. 
 *  to tell if you are command based you will "bind" commands to triggers. these commands will be re-used throughout the entire qualifier/playoff match.
*/