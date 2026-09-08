package frc.robot.subsystems.statemachines.drivetrain;

import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.swerve.Swerve;
import frc.robot.subsystems.statemachines.scoring.ScoringMasterStateMachine;

public class TeleopState extends State {
    @Override
    public void build() {
        addTransition(
            new Transition(
                () -> {
                  return RobotMap.driverController.getBButtonPressed();
                },
                DrivetrainMasterStateMachine.teleopState));  
        addTransition(
            new Transition(
                () -> {
                  return RobotMap.driverController.getLeftTriggerAxis() > 0.25
                    || RobotMap.scoringmasterstatemachine.getCurrentState().equals(ScoringMasterStateMachine.cycleState);
                },
                DrivetrainMasterStateMachine.alignstate));  
    
        addTransition(
            new Transition(
                () -> {
                  return RobotMap.driverController.getRightTriggerAxis() > 0.25
                    || RobotMap.scoringmasterstatemachine.getCurrentState().equals(ScoringMasterStateMachine.idleState);
                },
                DrivetrainMasterStateMachine.pushState)); }
        

    @Override
    public void init(State prevState) {}  

    @Override
    public void execute() {
        Swerve.teleopDrive();
    }   
    
    @Override
    public void exit(State nextState) {}
}