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

public class ShootState extends State {

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
    LEDS.setFlashOrange();
  }

  @Override
  public void execute() {
      Indexer.FeedIn();
      Indexer.IndexIn();
      Shooter.GetDistAndShoot(2.921);
      Intake.intakeShuffle(); 

  }


  @Override
  public void exit(State nextState) {
    isPass = false;
    LEDS.turnOff();
    Intake.intakeOut();
  

  }
}
