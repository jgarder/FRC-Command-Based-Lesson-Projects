Lesson 2 - my first subsytem

setting up and making our first "subsystem"

in this we copy lesson 0-1 to keep our awesome drivetrain but then we start adding on top of it. 

the main goal of this will be to Make a new subsystem class and instantiate(create/use) and object of that class in our robot container!
in the end we should have a skeleton subsystem that will have a constructor method/function (called ONCE when we delcare the use of the command) that prints a statement, 
we will also have the intialization method/function (called once each time the command is scheduled to run) that will just print a statement,  
and also the periodic ( runs every .02 second the command IS SCHEDULED) that prints a statement aswell

The secondary goal is in robotcontainer.java where we bind all of our joystick buttons. 
you should be able to create simple instantCommands and use the RunOnce method to Run any code you want through the command scheduler.
using the command scheduler for even simple task means we can add and compose those later to larger and Longer task/compositions/movements.

this guide is just a rewriting of the 100% awesome WPILIB docs! please just read the "command based programming" section and you wont even need a mentor! 
For this lesson you should also read the section on commands to get an idea of how to inline an instant command/RunOnce -> https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html

the implied 8608 resource is this lesson 2 lesson plan -> N/A yet! coming soon!
if you have no mentor read the wpilib here and replicate this source code on your own! -> https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html

