// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.statemachines.scoring;

import frc.robot.lib.statemachine.StateMachine;
import frc.robot.subsystems.statemachines.drivetrain.ShootMoveState;
import frc.robot.subsystems.statemachines.drivetrain.TeleopState;

/** Add your docs here. */
public class ScoringMasterStateMachine extends StateMachine{
    /* EXAMPLE: public static StateName  statename = StateName(); */
    public static IdleState idleState = new IdleState();
    public static CycleState cycleState = new CycleState();
    public static IntakeState intakeState = new IntakeState();
    public static OuttakeState outtakeState = new OuttakeState();

    public ScoringMasterStateMachine() {
        /* EXAMPLE: statename.build(); */
        idleState.build();
        cycleState.build();
        intakeState.build();
        outtakeState.build();
    }
}
