 --->>>> Open Robot.java to begin working. <<<<<<<<<<-----
  
  ## FRC #8608 java FRC programming
  #### lesson 004
  
  This guide is just a rewriting of the 100% awesome WPILIB docs! 
  please just read the zero to robot section and you wont even need a mentor! 

  #### WpiLib references used:
    -> Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
    -> binding commands to triggers -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#binding-commands-to-triggers
    -> Whiletrue trigger ->  https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html#whiletrue
    -> CONTROLLER DOCUMENTATION -> Notice all the triggers to bind to! -> https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/CommandXboxController.
  
  
  ### Final requirements
  - you will show a completed lesson folder to your teacher. 
    - by branching this git repository and updating your own branch. 
    - or showing your laptop to a mentor!
    - The Project should always "Build successful" When shown to mentor unless asking a question during developement!
  
  ### Task to pass this lesson:
  
  1. Look at ALL the comments (green text) in Robot.java and look for a commented out print command object. this object wont be created if its commented out. uncomment it now. If you Simulate your code did anything operate different before and after this change?  
  
  2. (viewed by Mentor): uncomment the bindings for the print command to the controller and will print "printing a statement from my first command!" when teleop is enabled and the A button is pressed OR the smartdashboard(WE USE ELASTIC) button is pressed. Save and simulate again.
    - YOU SHOULD BE ABLE TO VIEW THE OUTPUT IN THE TERMINAL IN VSCODE
  
  3. (viewed by Mentor): The print commands still arent working correctly! what are we missing? well did you know the command based framework that manages all commands and subsystems need the command scheduler to actually RUN each periodic loop? make sure that your command scheduler is running inside your robotPeriodic() loop!

  4. (viewed by mentor): use the dashboard to run your command. the  dashboard we use it called elastic and comes with wpilib and is already installed on your PC. Open elastic AND THEN run your program in debug, then press "add widget" and drag to add the command to your dashboard. then press the button and check your console. it should run the very same command as pressing the "A" button!  
  
  - for lessons that require code changes there is a github branch "lessonSolutions" where you can find 1 possible way to pass pass the task, though there are MANY 
  way to approach each problem.  but the best option is to look at the WPIlib resources linked above and read about the topic at hand.
  

