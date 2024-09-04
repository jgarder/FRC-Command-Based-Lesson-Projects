// Open Robotcontainer.java for the core of your code! and most task!
/*
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  -> https://docs.wpilib.org/en/stable/docs/software/commandbased/command-compositions.html#subclassing-compositions
 *  -> https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html
 * 
 * Lesson 4 - subclassing commands and subclassing composition and perhaps a simple command factory
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to a mentor unless asking a question during developement!
 * 
 * requirements to pass this lesson:
 *
 * Task 0 : open subsystems -> ComplexMechanismCommandFactory.java and try to observe ->
 *  -> we have moved commands to their own classes that we can have very granular control over. 
 *  -> these are the same commands from lesson 3. look back and forth to compare. how can that code be portable!? they all return a command and are public for one. 
import java.lang.invoke.ClassSpecializer.Factory;

 * 
 * Task 1 (viewed by Mentor):  joystick.b().whileTrue( should be running a method from the command factory. but which one? 
 *  -> we want to replace the code currently there. 
 *  -> Cut and paste it outof robot container and into their own command building factories or subclasses.
 *  -> make the method or function in "ComplexMechanismCommandFactory.java" return a command so we can schedule it in our command scheduler
 *  -> run cmdFactory.sequenceOfPrints() is the result. convert the joystick.b trigger below to Just run sequence of prints method. 
 *   
 */