 --->>>> Open Robot.java to begin working. <<<<<<<<<<-----
  
  ## FRC #8608 java FRC programming
  #### lesson 005
  
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
  
  1. Look at ALL the comments (green text) in RobotContainer.java we are implementing a mock subsystem that will pretend to move a motor. ;
  
  2. (viewed by Mentor): uncomment the bindings for the method 1 or method 2  Save and simulate again. make sure to the axis data into your elastic dashboard so we can check everything is working and debug issues. you are now moving a motor or adjusting something! this is control!
  3. (viewed by Mentor) advanced : Copy and paste the OurRobotsArmController and make another subsystem to pretend to control another motor, perhaps this would be a wrist or other joint on the arm! 2. Then copy the old controller commands and change the button and assign the commands to the new subsystem, you are now expanding and controlling multiple subsystems with just a few buttons!~
  
  
  - for lessons that require code changes there is a github branch "lessonSolutions" where you can find 1 possible way to pass pass the task, though there are MANY 
  way to approach each problem.  but the best option is to look at the WPIlib resources linked above and read about the topic at hand.
  

