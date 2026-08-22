package frc.robot;

import java.security.PublicKey;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.lib.auto.AutoSubsystem;
import frc.robot.subsystems.interfaces.Indexer;
import frc.robot.subsystems.interfaces.Intake;
import frc.robot.subsystems.interfaces.Limelight;
import frc.robot.subsystems.interfaces.Shooter;
import frc.robot.subsystems.interfaces.swerve.Swerve;
import frc.robot.subsystems.statemachines.drivetrain.DrivetrainMasterStateMachine;
import frc.robot.subsystems.statemachines.scoring.ScoringMasterStateMachine;

public class RobotMap {
    private static final PowerDistribution PDP = new PowerDistribution();
    private static final AutoSubsystem autosubsystem = new AutoSubsystem(Swerve.createAutoFactory());

    /*State machines instances */
    public static final DrivetrainMasterStateMachine drivetrainmasterstatemachine = new DrivetrainMasterStateMachine();
    public static final ScoringMasterStateMachine scoringmasterstatemachine = new ScoringMasterStateMachine();

    /* Controllers */
    public static final XboxController driverController = new XboxController(0);
    public static final XboxController manipulatorController = new XboxController(1);

    public static void init() {
        /* Subsystems "your ment to start every subsystem here"*/
        Swerve.init();
        Indexer.init();
        Intake.init();
        Limelight.init();
        Shooter.init();
        

        /* statemachines */
        drivetrainmasterstatemachine.enable();
        scoringmasterstatemachine.enable();

     drivetrainmasterstatemachine.setCurrentState(drivetrainmasterstatemachine.teleopState);
     scoringmasterstatemachine.setCurrentState(scoringmasterstatemachine.idleState);

    scheduleAuto();
    }
    public static void subsystemPeriodics() {
    Swerve.periodic();
    Limelight.periodic();

    if (driverController.getXButtonPressed()) {
      Swerve.zeroGyro();
    }

    Logger.recordOutput("PDP TOTAL", PDP.getTotalCurrent());
  }

  public static void scheduleAuto() {
    autosubsystem.scheduleAuto();


    // "For free?"
    //  - Jai Patel

  }
}
