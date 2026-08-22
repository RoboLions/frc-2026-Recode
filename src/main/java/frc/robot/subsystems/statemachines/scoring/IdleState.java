// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.statemachines.scoring;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.LEDS;
import frc.robot.subsystems.interfaces.Shooter;

/** Add your docs here. */
public class IdleState extends State {

  @Override
  public void build() {
    addTransition(
            new Transition(
                () -> {
                return RobotMap.driverController.getBButtonPressed();
                },
                ScoringMasterStateMachine.idleState));
    addTransition(
        new Transition(
            () -> {
              return RobotMap.driverController.getLeftTriggerAxis() > 0.25;
            },
              ScoringMasterStateMachine.cycleState));
    addTransition(
            new Transition( 
                () -> {
                return RobotMap.driverController.getRightBumper();
                },
                ScoringMasterStateMachine.intakeState));
    addTransition(
        new Transition(
            () -> {
            return RobotMap.driverController.getLeftBumper();
            },
            ScoringMasterStateMachine.outtakeState));
    addTransition(
        new Transition(
            () -> {
            return RobotMap.manipulatorController.getAButton();
            },
            ScoringMasterStateMachine.outtakeState));
        new Transition(
            () -> {
            return RobotMap.manipulatorController.getYButton();
            },
            ScoringMasterStateMachine.intakeState);
  }

  @Override
public void init(State prevState) {
    Intake.allRollersStop();
    Shooter.idleShooter();
    DriverStation.getAlliance().ifPresentOrElse(
        alliance -> {
            if (alliance == DriverStation.Alliance.Blue) {
                LEDS.setSolidBlue();} 
                else { LEDS.setSolidRed();}},
        () -> LEDS.setLarsonWhite() 
    );
}

  @Override
  public void execute() {
    if (RobotMap.driverController.getYButtonPressed() || RobotMap.manipulatorController.getLeftBumperButtonPressed()) {
      Intake.intakeUp();
      Intake.intakeSlow();
    }

    if (RobotMap.driverController.getYButtonReleased() || RobotMap.manipulatorController.getLeftBumperButtonReleased()) {
      Intake.stopIntake();
    }

    if (RobotMap.manipulatorController.getRightBumperButtonPressed()) {
      Intake.intakeOut();
    }
  }

  @Override
  public void exit(State nextState) {
        LEDS.turnOff();

  }
}