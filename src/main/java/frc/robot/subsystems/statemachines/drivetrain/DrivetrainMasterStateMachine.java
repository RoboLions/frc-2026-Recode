// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.statemachines.drivetrain;

import frc.robot.lib.statemachine.StateMachine;

/** Add your docs here. */
public class DrivetrainMasterStateMachine extends StateMachine{
    /* EXAMPLE: public static StateName  statename = StateName(); */
    public static TeleopState teleopState = new TeleopState();
    public static ShootMoveState shootMoveState = new ShootMoveState();
    public static AlignState alignstate = new AlignState();
    public static PushState pushState = new PushState();
    

    public DrivetrainMasterStateMachine() {
        /* EXAMPLE: statename.build(); */
        teleopState.build();
        shootMoveState.build();
        alignstate.build();
        pushState.build();

    }
}
