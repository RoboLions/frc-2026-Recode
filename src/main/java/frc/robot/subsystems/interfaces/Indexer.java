package frc.robot.subsystems.interfaces;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.Constants;

public class Indexer {
    
    private static final TalonFX mIndexMasterMotor = 
    new TalonFX(Constants.CAN_IDS.INDEX_MOTOR_MASTER, "CANext");
    private static final TalonFX mIndexFollowMotor = 
    new TalonFX(Constants.CAN_IDS.INDEX_MOTOR_FOLLOWER, "CANext");

    private static final TalonFX mFeedMotor = 
    new TalonFX(Constants.CAN_IDS.FEEDER_MOTOR, "CANext");


public static void init() {
    TalonFXConfiguration indexMotorConfiguration = new TalonFXConfiguration();

    indexMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
    indexMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;

    indexMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
    indexMotorConfiguration.CurrentLimits.StatorCurrentLimit = 80;
    indexMotorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
    indexMotorConfiguration.CurrentLimits.SupplyCurrentLimit = 25;

    indexMotorConfiguration.Slot0.kP = 2.5;
    indexMotorConfiguration.Slot0.kI = 0.0;
    indexMotorConfiguration.Slot0.kD = 0.0;
    indexMotorConfiguration.Slot0.kS = 15.0;
    indexMotorConfiguration.Slot0.kA = 0.0;
    indexMotorConfiguration.Slot0.kV = 0.165;
    indexMotorConfiguration.Slot0.kG = 0.0;

    indexMotorConfiguration.Feedback.SensorToMechanismRatio = 1 / 1;
    indexMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    mIndexMasterMotor.getConfigurator().apply(indexMotorConfiguration);
    mIndexFollowMotor.getConfigurator().apply(indexMotorConfiguration);

    TalonFXConfiguration feedMotorConfiguration = new TalonFXConfiguration();

    feedMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
    feedMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;

    feedMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
    feedMotorConfiguration.CurrentLimits.StatorCurrentLimit = 80;
    feedMotorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
    feedMotorConfiguration.CurrentLimits.SupplyCurrentLimit = 25;

    feedMotorConfiguration.Slot0.kP = 5;
    feedMotorConfiguration.Slot0.kI = 0.0;
    feedMotorConfiguration.Slot0.kD = 0.0;
    feedMotorConfiguration.Slot0.kS = 4.75;
    feedMotorConfiguration.Slot0.kA = 0.0;
    feedMotorConfiguration.Slot0.kV = 0.05;
    feedMotorConfiguration.Slot0.kG = 0.0;

    feedMotorConfiguration.Feedback.SensorToMechanismRatio = 1 / 1;
    feedMotorConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

    mFeedMotor.getConfigurator().apply(feedMotorConfiguration);

    mIndexFollowMotor.setControl(new Follower(mIndexMasterMotor.getDeviceID(), MotorAlignmentValue.Opposed).withUpdateFreqHz(20));

}
    public static void setFeed(double speed) {
        mFeedMotor.setControl(new VelocityTorqueCurrentFOC(speed).withUpdateFreqHz(50));
    }

    public static void FeedIn() {
        setFeed(80);
    }

    public static void FeedOut() {
        setFeed(-10);
    }

    public static void stopFeed() {
        mFeedMotor.setControl(new VoltageOut(0).withUpdateFreqHz(20));
    }

    public static void setIndex(double rpm) {
        mIndexMasterMotor.setControl(new VelocityTorqueCurrentFOC(rpm).withUpdateFreqHz(50));
    }

    public static void IndexIn() {
        setIndex(75);
    }

    public static void IndexOut() {
        setIndex(-40);
    }

    public static void stopIndex() {
        mIndexMasterMotor.setControl(new VoltageOut(0).withUpdateFreqHz(20));
    }
}
