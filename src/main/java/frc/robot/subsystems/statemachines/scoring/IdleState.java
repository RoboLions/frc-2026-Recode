// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.statemachines.scoring;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotMap;
import frc.robot.lib.statemachine.State;
import frc.robot.lib.statemachine.Transition;
import frc.robot.subsystems.interfaces.Indexer;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.LEDS;
import frc.robot.subsystems.interfaces.Shooter;

/** Add your docs here. */
public class IdleState extends State {

  @Override
  public void build() {
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
    if (RobotMap.driverController.getYButtonPressed() ) {
      Intake.intakeUp();
      Intake.intakeSlow();
    }

    if (RobotMap.driverController.getYButtonReleased() ) {
      Intake.stopIntake();
    }

    if (RobotMap.driverController.getRightTriggerAxis() > .25) {
      Intake.intakeOut();
    }
    if (RobotMap.driverController.getLeftTriggerAxis() > .25) {
      Intake.intakeUp();
    }
    if (RobotMap.driverController.getAButton()) {
      Shooter.setShootSpeed(20);
    }
    if (RobotMap.driverController.getBButton()) {
      Indexer.IndexIn();;
    }
    if (RobotMap.driverController.getXButton()) {
      Shooter.setShootSpeed(20);
    }
    if (RobotMap.driverController.getRightBumperButtonPressed()) {
      Intake.intake();
    }
    if (RobotMap.driverController.getLeftBumperButtonPressed()) {
      Intake.outtake();
    }
    
  }

  @Override
  public void exit(State nextState) {
        LEDS.turnOff();

  }
}