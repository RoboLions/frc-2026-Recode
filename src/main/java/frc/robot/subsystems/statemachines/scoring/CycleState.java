package frc.robot.subsystems.statemachines.scoring;

import frc.robot.RobotMap;
import frc.robot.Constants;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.Indexer;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.Shooter;
import frc.robot.subsystems.interfaces.swerve.Swerve;
import frc.robot.subsystems.interfaces.LEDS;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

public class CycleState extends State {

  public static boolean isPass = false;

  @Override
  public void build() {
    addTransition(
        new Transition(
            () -> {
              return RobotMap.driverController.getBButton();
            },
            ScoringMasterStateMachine.idleState));
    addTransition(
        new Transition(
            () -> {
              return RobotMap.driverController.getRightBumperButtonPressed();
            },
            ScoringMasterStateMachine.intakeState));}

  @Override
  public void init(State prevState) {
    Intake.intakeOut();
    LEDS.setFlashRed();
  }

  @Override
  public void execute() {
    if (RobotMap.driverController.getRightTriggerAxis() > 0.25) { // SHOOT
      Indexer.FeedIn();
      Indexer.IndexIn();
      LEDS.setFlashGreen();
    }

    if (RobotMap.driverController.getLeftBumperButtonPressed() || RobotMap.manipulatorController.getLeftBumperButtonPressed()) {
      Intake.intakeMid();
      Intake.intakeSlow();
    } else if (RobotMap.driverController.getRightBumperButtonPressed() || RobotMap.driverController.getAButtonPressed() || RobotMap.manipulatorController.getRightBumperButtonPressed()) {
      Intake.intakeOut();
    }

    if (Swerve.getPose().getX() >= 4.75 && Swerve.getPose().getX() <= 11.75) { // PASS LOGIC VS HUB
      
      if (Swerve.getPose().getY() > 4) {
       Shooter.GetDistAndPass(Swerve.getPose().getTranslation().getDistance(Constants.FIELD.PASS_TOP_FIELD.toTranslation2d()));
      } else {
       Shooter.GetDistAndPass(Swerve.getPose().getTranslation().getDistance(Constants.FIELD.PASS_BOTTOM_FIELD.toTranslation2d()));
      }
      
      isPass = true;
    } else {
      Shooter.GetDistAndShoot(Swerve.getPose().getTranslation().getDistance(Constants.FIELD.HUB_POSE));
      isPass = false;
    }
  }

  @Override
  public void exit(State nextState) {
    isPass = false;
        LEDS.turnOff();

  }
}
