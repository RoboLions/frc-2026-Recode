package frc.robot.subsystems.statemachines.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.Sotm;
import frc.robot.subsystems.interfaces.swerve.Swerve;
import frc.robot.subsystems.statemachines.scoring.CycleState;
import frc.robot.lib.statemachine.StateMachine;
public class ShootMoveState extends State {
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
                return RobotMap.driverController.getLeftTriggerAxis() > 0.25;
                },
                DrivetrainMasterStateMachine.alignstate));
         
    }

    @Override
    public void init(State prevState) {}  

    @Override
    public void execute() {
        if (CycleState.isPass) {
            if (Swerve.getPose().getY() > 4.0) {
                Swerve.TeleopDriveFacePose(Constants.FIELD.passTopField().toTranslation2d(), Rotation2d.fromDegrees(180.0), 1.25);
            } else {
                Swerve.TeleopDriveFacePose(Constants.FIELD.passBottomField().toTranslation2d(), Rotation2d.fromDegrees(180.0), 1.25);
            }

            return;
        }

        Swerve.TeleopDriveFacePose(Sotm.getLeadTarget(Constants.FIELD.hubPose()),Rotation2d.fromDegrees(180.0), 1.25); 
    }   
    
    @Override
    public void exit(State nextState) {}
}
