package frc.robot.subsystems.statemachines.drivetrain;

import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.swerve.Swerve;

public class PushState extends State {
    @Override
    public void build() {
        addTransition(
            new Transition(
                () -> {
                  return RobotMap.driverController.getBButtonPressed();
                },
                DrivetrainMasterStateMachine.teleopState));  

    }

    @Override
    public void init(State prevState) {}  

    @Override
    public void execute() {
        Swerve.pushDrive();
    }   
    
    @Override
    public void exit(State nextState) {}
}