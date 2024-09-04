// Open Robotcontainer.java for the core of your code! and most task!
/*
 * FRC #8608 "a dummies guide to programming FRC java"
 * 
 * this guide is just a rewriting of the 100% awesome WPILIB docs! 
 * please just read the zero to robot section and you wont even need a mentor! 
 * 
 * Wpilib references when in trouble :
 *  Command based programming -> https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html
 *  command composition -> https://docs.wpilib.org/en/stable/docs/software/commandbased/command-compositions.html
 *  binding triggers (buttons) -> https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
 *  binding triggers is stuff like .ontrue(),.onfalse(),.negate(), .and(), these are used to fire a command when trigger is true. (button is pressed)
 * 
 * Lesson 3 - Making complex commands from composing simple commands
 * 
 * you will show a completed lesson folder to your teacher. 
 * -> by branching this in git and updating your branch. 
 * -> or showing them your personal laptop ready to compile/run or simulate your code!
 * -> The Project should always "Build successful" When shown to a mentor unless asking a question during developement!
 * requirements to pass this lesson:
 *
 * Task 0 : look below at private void configureBindings() and try to observe ->
 *  -> notice the way we can run a command .andthen() run one after the first is completed!
 *  -> notice we can do the same thing by building a list to run in sequence using Commands.sequence(
 *  -> think about how you can do almost anything with these tools  Ready->Aim->Fire Or maybe all together like Moving/aiming/spin-shooter .andthen Fire!
 *
 * Task 1 (viewed by Mentor): Make a message print over and over and over while you Hold the A button using joystick.a().whileTrue().
 *  -> https://docs.wpilib.org/en/stable/docs/software/commandbased/command-compositions.html#repeating
 *  -> using .repeatedly() can be used to make a command run time after time. but the command has to end for it to start again, this one is instant so it does!
 * 
 * Task 2 (viewed by Mentor): make the B "joystick.b().whileTrue" also print the text "button b true 3"
 *  -> System.out.println(); is the code you need to run, its not a command, its needs to be wrapped in an instant command or a "runonce"
 * 
 *   
 * Sticking commands together can do incredibly complex things. 
 * if you have made it this far and can take apart and rebuild the commands below. you can program a robot. you are. you already have. 
*/