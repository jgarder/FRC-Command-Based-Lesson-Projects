# FRC# 8608 - Programming 101

### How to use this? 
 
 1. Follow the Laptop Driverstation quickstart guide here -> https://alpha-bots.gitbook.io/alpha-bots-8608/learning-resources/programming/laptop-driver-station-programmer-quick-setup-guide
 2. In Vscode open each lesson folder the same way you would a real robot project. 
 3. inside each lesson src/java/frc/robot folder open the readme.md for lesson task for grading!

 ## Lessons
 000 - Barebones code that does not run a robot but instead just simply prints "hello!" and "World!" to your terminal

 001 - This robot source code has just enough code to see the built in operational modes such as Disabled,autonomous, and teleop. It has no other features. Please observe how much code is similar to the 000 barebones code, this is called "boilerplate" code because its always needed and in most cases can just be "ignored" by your brain as you write a program. 

 002 - this is same as 001 except the task is to see how periodic means "looping over and over". again, look how much code is exactly the same as 000 and 001. most code is repeated and it start feeling less scary. 

 003 - Add an Xbox controller to your code and read when the "A" button is pressed. This is not command based this is what we call functional programming or imperitive programming where it it checks over a series of If/else statements every loop (20 times a second) and executes  your code block. passing this point makes you able to program and help many beginner teams in FRC.

 004 - This project expands on having an xbox controller by "binding" or "declaring" a command and running that command as many times as needed! this is "delcaritive" programming and we setup our end goals and the system handles it. this is the very core of a command based robot. if you do this project over and over you will build a command based robot and be able to help many beginner teams at our local event! 

 005 - folder organization and importing files - this will show another concept you will repeat many times to make a robot, which is using multiple files. this programming practice is the "S" in the SOLID programming principles. which is the "Single-responsibility Principle" (SRP) : which states: "A class should have one and only one reason to change, meaning that a class should have only one job." Put bluntly we dont want 1 long file that is impossible to read doing everything. we want lots of files that have names that clearly represent their task and nothing more. 

 006 - this is like 005, but with subsystems and the class subsystembase. in this example we will pretend to move a motor forward and back with out joystick axis, you should also read out the motor speed with your elastic dashboard! this is key to debugging! sidenote : notice how we add new objects to robotContainer near the top, to quickly assess the objects used in a file and how the object may work. we usually put methods and functions below the objects (fields and properties) 

 007 - This is your first test, so far we have uncommented stuff, but creating is where winners are made. you will be working inside  a robot that already exist, so there will be lots of distracting text. construct a command based robot (just printing text) that uses buttons and will "lift elevator" and "swing arm to score" using the X and Y Button.. 