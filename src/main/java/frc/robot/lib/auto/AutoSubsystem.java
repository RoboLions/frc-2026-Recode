package frc.robot.lib.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.lib.Pursuiter.PursuitAutoFactory;
import frc.robot.lib.Pursuiter.helpers.FieldMap;

public class AutoSubsystem {
    public PursuitAutoFactory autoFactory;
    private Command runningAuto;

    private final double SHOOT_TIMEOUT_1 = 3.25; // time to empty the hopper
    private final double SHOOT_TIMEOUT_2 = 2.75; // time to empty the hopper

    // Used by the right-side autos to mirror the left paths (replaces .mirrorY()).
    private final Translation2d CENTER_FIELD = FieldMap.center;

    public AutoSubsystem(PursuitAutoFactory createAutoFactory) {
        autoFactory = createAutoFactory;

        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.0025));
        autoFactory.addEvent("INTAKE_OUT",
            AutoCommands.intakeOutOnly()
            .withTimeout(0.0025));
        autoFactory.addEvent("SHOOT_START",
            AutoCommands.shootSequenceWithRampSOTM()
            .withTimeout(SHOOT_TIMEOUT_1));

        autoFactory.registerAutoCommand("Left-Trench 2P", left2TrenchTripONLY());
        autoFactory.registerAutoCommand("Right-Trench 2P", right2TrenchTripONLY());

        autoFactory.registerAutoCommand("Left-Delay 2P", leftDELAY2Trip());
        autoFactory.registerAutoCommand("Right-Delay 2P", rightDELAY2Trip());

        autoFactory.registerAutoCommand("Left-Bump 2P", leftBump2Trip());
        autoFactory.registerAutoCommand("Right-Bump 2P", rightBump2Trip());

        autoFactory.registerAutoCommand("leftBump2delay", leftBump2Tripdelay());

        autoFactory.registerAutoCommand("HubPullBack", Hub_PullBack());

        autoFactory.registerAutoCommand("LeftBumpHub", Left_Bump_Hub());
        autoFactory.registerAutoCommand("RightBumpHub", Right_Bump_Hub());

        autoFactory.registerAutoCommand("LeftTrenchDelay", left2TrenchTripDELAY());
        autoFactory.registerAutoCommand("RightTrenchDelay", right2TrenchTripDELAY());

        autoFactory.registerAutoCommand("LeftTrenchBump", left2Trenchbump());
        autoFactory.registerAutoCommand("RightTrenchBump", right2Trenchbump());

        // autoFactory.registerAutoCommand("LeftTrenchBumpSOTM", left2TrenchbumpSOTM());
        // autoFactory.registerAutoCommand("RightTrenchBumpSOTM", right2TrenchbumpSOTM());

        //  autoFactory.registerAutoCommand("auto name", AUTONAME());
        SmartDashboard.putData("AutoChooser", autoFactory.getAutoChooser());
    }

    public void scheduleAuto() {
        RobotModeTriggers.autonomous().onTrue(Commands.runOnce(this::startAuto));
        RobotModeTriggers.autonomous().onFalse(Commands.runOnce(this::stopAuto));
    }

    private void startAuto() {
        runningAuto = autoFactory.getSelectedAuto();
        if (runningAuto != null) {
            runningAuto.schedule();
        }
    }

    private void stopAuto() {
        if (runningAuto != null) {
            runningAuto.cancel();
        }
    }

// AUTO TEMP BELOW
// private Command AUTONAME() {
//         autoFactory.addEvent("INTAKE",
//             AutoCommands.intakeOutRollersIn()
//             .withTimeout(0.001));
//         autoFactory.addEvent("INTAKE_STOP",
//             AutoCommands.intakeStop()
//             .withTimeout(0.001));
//         autoFactory.addEvent("REV_SHOT",
//             AutoCommands.setShooter()
//             .withTimeout(0.001));
//
//         Command L1 = autoFactory.followPath(".traj");
//         Command L2 = autoFactory.followPath(".traj");
//         Command L3 = autoFactory.followPath(".traj");
//
//         return Commands.sequence(
//             Commands.waitSeconds(2.0),
//
//             AutoCommands.intakeZeroPosition()
//                 .withTimeout(0.001),
//
//             AutoCommands.feedStop()
//                 .withTimeout(0.001),
//
//             AutoCommands.idleShooter()
//                 .withTimeout(0.0025),
//
//             L1,
//
//             AutoCommands.shootSequenceWithRamp()
//                 .withTimeout(SHOOT_TIMEOUT_1),
//
//             AutoCommands.feedStop()
//                 .withTimeout(0.001),
//
//             AutoCommands.idleShooter()
//                 .withTimeout(0.0025),
//
//             L2,
//
//             AutoCommands.shootSequenceWithRamp()
//                 .withTimeout(SHOOT_TIMEOUT_2),
//
//             AutoCommands.feedStop()
//                 .withTimeout(0.001),
//
//             AutoCommands.idleShooter()
//                 .withTimeout(0.0025),
//
//             L3);
//     }

    private Command left2TrenchTripONLY() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1.traj");
        Command L2 = autoFactory.followPath("L2.traj");
        Command L3 = autoFactory.followPath("L3.traj");

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.001),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command right2TrenchTripONLY() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        Command R1 = autoFactory.followPath("L1.traj", false, true,  CENTER_FIELD);
        Command R2 = autoFactory.followPath("L2.traj", false, true,  CENTER_FIELD);
        Command R3 = autoFactory.followPath("L3.traj", false, true,  CENTER_FIELD);

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R3);
    }

    private Command leftDELAY2Trip() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));

        Command L1 = autoFactory.followPath("L1_DELAY.traj");
        Command L2 = autoFactory.followPath("L2_DELAY.traj");

        return Commands.sequence(
            Commands.waitSeconds(1.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2));
    }

    private Command rightDELAY2Trip() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));

        Command R1 = autoFactory.followPath("L1_DELAY.traj", false, true,  CENTER_FIELD);
        Command R2 = autoFactory.followPath("L2_DELAY.traj", false, true,  CENTER_FIELD);

        return Commands.sequence(
            Commands.waitSeconds(10.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2));
    }

    private Command leftBump2Trip() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1_Bump.traj");
        Command L2 = autoFactory.followPath("L2_Bump.traj");
        Command L3 = autoFactory.followPath("L3_Bump.traj");

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command rightBump2Trip() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));

        Command L1 = autoFactory.followPath("L1_Bump.traj", false, true,  CENTER_FIELD);
        Command L2 = autoFactory.followPath("L2_Bump.traj", false, true,  CENTER_FIELD);
        Command L3 = autoFactory.followPath("L3_Bump.traj", false, true,  CENTER_FIELD);

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command leftBump2Tripdelay() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("Intake_ROLLERS_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));

        Command L1 = autoFactory.followPath("L1_Bump.traj");
        Command L2 = autoFactory.followPath("L2_Bump.traj");
        Command L3 = autoFactory.followPath("L3_Bump.traj");

        return Commands.sequence(
            Commands.waitSeconds(5.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command Hub_PullBack() {
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.0025));
        autoFactory.addEvent("INTAKE_OUT",
            AutoCommands.intakeOutOnly()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1_Hub.traj");

        return Commands.sequence(
            L1,
            AutoCommands.shootSequenceWithRamp()
                .withTimeout(20));
    }

    private Command Left_Bump_Hub() {
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.03));
        autoFactory.addEvent("INTAKE_OUT",
            AutoCommands.intakeOutOnly()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1_BumpHub.traj");

        return Commands.sequence(
            L1,
            AutoCommands.shootSequenceWithRamp()
                .withTimeout(20));
    }

    private Command Right_Bump_Hub() {
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.03));
        autoFactory.addEvent("INTAKE_OUT",
            AutoCommands.intakeOutOnly()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1_BumpHub.traj", false, true,  CENTER_FIELD);

        return Commands.sequence(
            L1,
            AutoCommands.shootSequenceWithRamp()
                .withTimeout(20));
    }

    private Command left2TrenchTripDELAY() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.0025));

        Command L1 = autoFactory.followPath("L1.traj");
        Command L2 = autoFactory.followPath("L2.traj");
        Command L3 = autoFactory.followPath("L3.traj");

        return Commands.sequence(
            Commands.waitSeconds(2.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.001),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command right2TrenchTripDELAY() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        Command R1 = autoFactory.followPath("L1.traj", false, true,  CENTER_FIELD);
        Command R2 = autoFactory.followPath("L2.traj", false, true,  CENTER_FIELD);
        Command R3 = autoFactory.followPath("L3.traj", false, true,  CENTER_FIELD);

        return Commands.sequence(
            Commands.waitSeconds(2.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R3);
    }

    private Command left2Trenchbump() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        Command L1 = autoFactory.followPath("L1_TB.traj");
        Command L2 = autoFactory.followPath("L2_TB.traj");
        Command L3 = autoFactory.followPath("L3_TB.traj");

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command right2Trenchbump() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        Command R1 = autoFactory.followPath("L1_TB.traj", false, true,CENTER_FIELD);
        Command R2 = autoFactory.followPath("L2_TB.traj", false, true, CENTER_FIELD);
        Command R3 = autoFactory.followPath("L3_TB.traj", false, true, CENTER_FIELD);

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R1,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_1),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R2,

            AutoCommands.shootSequenceWithRamp()
                .withTimeout(SHOOT_TIMEOUT_2),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R3);
    }

    private Command left2TrenchbumpSOTM() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        // SHOOT_START has a different timeout on each path, so set it right before each one.
        autoFactory.addEvent("SHOOT_START",
            AutoCommands.shootSequenceWithRampSOTM()
            .withTimeout(SHOOT_TIMEOUT_1));
        Command L1 = autoFactory.followPath("L1_TBSM.traj");

        autoFactory.addEvent("SHOOT_START",
            AutoCommands.shootSequenceWithRampSOTM()
            .withTimeout(SHOOT_TIMEOUT_2));
        Command L2 = autoFactory.followPath("L2_TBSM.traj");

        Command L3 = autoFactory.followPath("L3_TBSM.traj");

        return Commands.sequence(
            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L1,

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L2,

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            L3);
    }

    private Command right2TrenchbumpSOTM() {
        autoFactory.addEvent("INTAKE",
            AutoCommands.intakeOutRollersIn()
            .withTimeout(0.001));
        autoFactory.addEvent("INTAKE_STOP",
            AutoCommands.intakeStop()
            .withTimeout(0.001));
        autoFactory.addEvent("REV_SHOT",
            AutoCommands.setShooter()
            .withTimeout(0.001));

        // SHOOT_START has a different timeout on each path, so set it right before each one.
        autoFactory.addEvent("SHOOT_START",
            AutoCommands.shootSequenceWithRampSOTM()
            .withTimeout(SHOOT_TIMEOUT_1));
        Command R1 = autoFactory.followPath("L1_TBSM.traj", false, true,  CENTER_FIELD);

        autoFactory.addEvent("SHOOT_START",
            AutoCommands.shootSequenceWithRampSOTM()
            .withTimeout(SHOOT_TIMEOUT_2));
        Command R2 = autoFactory.followPath("L2_TBSM.traj", false, true,  CENTER_FIELD);

        Command R3 = autoFactory.followPath("L3_TBSM.traj", false, true, CENTER_FIELD);

        return Commands.sequence(
            Commands.waitSeconds(2.0),

            AutoCommands.intakeZeroPosition()
                .withTimeout(0.001),

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R1,

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R2,

            AutoCommands.feedStop()
                .withTimeout(0.001),

            AutoCommands.idleShooter()
                .withTimeout(0.0025),

            R3);
    }
}