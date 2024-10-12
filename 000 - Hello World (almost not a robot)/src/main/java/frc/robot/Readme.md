  ## FRC #8608 java FRC programming
  #### lesson 000 - Hello World! (bare minimum robot. does nothing.)
  
  ### Lesson Task:
  
  1. (In VSCode open Robot.java) Look at ALL the comments (green text after //) in Main.java and Robot.java. <br><br>
  
  2. (in vsCode) We need to start debugging, we can do this a few ways
     - on your keyboard press CTRL-SHIFT-P > (then a box will appear at the TOP of the vscode window) Click Simulate robot code
        - another box will appear at the top of VSCode, check the box (sim GUI) and click ok. the simulator window should open on your screen.
     - (You can also) click in the top menus  "Run" > "start debugging", 
     - (You can also) press f5 to start debugging (does not work in every circumstance and should not be used) 
  3. The project will build, then 2 SMALL options at top middle of screen will appear. check sim Gui and press OK
    - view the terminal at the bottom of the VSCODE screen. The printouts you see here are is the system.out.println() that are executing.<br><br>
  
4. (viewed by Mentor): find the method in the Main class that says "Hello " and find the method in the Robot class that prints "World" 
   - AFTER RESTARTING THE SIMULATION YOU SHOULD BE ABLE TO VIEW THE OUTPUT IN THE TERMINAL IN VSCODE, 

5. (viewed by Mentor): find the "hello" and "world" text in the terminal. why is the hello where it is? why is World where it is?
    - did you know, "Hello " is running before ANY robot code, its just regular ol' java at this point. 
    - "World" is run as the first line of your (the user) code. everything before it is Wpilib "TimedRobot" class and other libraries booting up. everything after is your robots "main loop" running over and over. 
