package frc.robot.subsystems.statemachines.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.swerve.Swerve;
import frc.robot.subsystems.statemachines.scoring.CycleState;


public class AlignState extends State {
    @Override
    public void build() {
        addTransition(
        new Transition(
            () -> {
              return RobotMap.driverController.getRightBumperButtonPressed();
            },
                DrivetrainMasterStateMachine.teleopState));   
        addTransition(
        new Transition(
            () -> {
              return RobotMap.driverController.getLeftBumperButtonPressed();
            },
            DrivetrainMasterStateMachine.shootMoveState));  
    }

    @Override
    public void init(State prevState) {}  

    @Override
    public void execute() {
        if (CycleState.isPass) {
            if (Swerve.getPose().getY() > 4.0) {
                Swerve.facePose(Constants.FIELD.PASS_TOP_FIELD.toTranslation2d(), Rotation2d.fromDegrees(180.0));
            } else {
                Swerve.facePose(Constants.FIELD.PASS_BOTTOM_FIELD.toTranslation2d(), Rotation2d.fromDegrees(180.0));
            }

            return;
        }

        Swerve.facePose(Constants.FIELD.HUB_POSE, Rotation2d.fromDegrees(180.0));
    }   
    
    @Override
        public void exit(State nextState) { 
    }
}
