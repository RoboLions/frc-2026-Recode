package frc.robot;

import java.security.PublicKey;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.lib.Pursuiter.PursuitAutoFactory;
import frc.robot.lib.Pursuiter.PursuitProfile;
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
    private static final AutoSubsystem autosubsystem = new AutoSubsystem(Swerve.createPursuitAutoFactory(PursuitProfile()));
    private static PursuitProfile profile =
      new PursuitProfile(
          Units.Meters.of(0.1),
          Units.Degrees.of(5.0),
          Units.Meters.of(0.3),
          new PIDController(1.0, 0, 0),
          new PIDController(3.0, 0, 0),
          new PIDController(7.5, 0, 0),
          true);
  private static PursuitAutoFactory autoFactory =
      new PursuitAutoFactory(Swerve::getPose, Swerve::setFieldChassisSpeeds, profile);

      
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
