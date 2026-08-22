package frc.robot.subsystems.interfaces;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Constants;



public class Intake {
    private static final TalonFX mIntakeRollerMotorMaster =
    new TalonFX(Constants.CAN_IDS.INTAKE_ROLLER_MASTER, "CANexternal");  
  private static final TalonFX mIntakeRollerMotorFollow = 
    new TalonFX(Constants.CAN_IDS.INTAKE_FOLLOWER_ROLLER, "CANexternal");



  private static final TalonFX mRackMotor = 
    new TalonFX(Constants.CAN_IDS.RACK_MOTOR, "CANexternal");

  private static final double STOW_POS = 1.85;
  private static final double MIDDLE_POS = 2.65;
  private static final double DOWN_POS = 10.2;

  public static void init() {    
    TalonFXConfiguration masterIntakeMotorConfiguration = new TalonFXConfiguration();

    masterIntakeMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
    masterIntakeMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;

    masterIntakeMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
    masterIntakeMotorConfiguration.CurrentLimits.StatorCurrentLimit = 60;
    masterIntakeMotorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
    masterIntakeMotorConfiguration.CurrentLimits.SupplyCurrentLimit = 25;

    masterIntakeMotorConfiguration.Slot0.kP = 3.0;
    masterIntakeMotorConfiguration.Slot0.kS = 12;
    masterIntakeMotorConfiguration.Slot0.kV = 0.0;
    masterIntakeMotorConfiguration.Slot0.kA = 0.0;

    masterIntakeMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    mIntakeRollerMotorFollow.getConfigurator().apply(masterIntakeMotorConfiguration);
    mIntakeRollerMotorMaster.getConfigurator().apply(masterIntakeMotorConfiguration);

    

    
    TalonFXConfiguration rackMotorConfiguration = new TalonFXConfiguration();
    
    rackMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    rackMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
    rackMotorConfiguration.CurrentLimits.StatorCurrentLimit = 70;
    rackMotorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
    rackMotorConfiguration.CurrentLimits.SupplyCurrentLimit = 25;

    rackMotorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    rackMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    rackMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold = DOWN_POS;
    rackMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    rackMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold = STOW_POS;
    
    rackMotorConfiguration.Slot0.kP = 2.0;
    rackMotorConfiguration.Slot0.kI = 0.0;
    rackMotorConfiguration.Slot0.kD = 0.0;
    rackMotorConfiguration.Slot0.kS = 0.75;
    rackMotorConfiguration.Slot0.kV = 0.1;
    rackMotorConfiguration.Slot0.kA = 0.0;
    rackMotorConfiguration.Slot0.kG = 0.0;

    rackMotorConfiguration.ClosedLoopGeneral.ContinuousWrap = false;
    rackMotorConfiguration.MotionMagic.MotionMagicAcceleration = 300.0;
    rackMotorConfiguration.MotionMagic.MotionMagicCruiseVelocity = 180.0;
    
    mRackMotor.getConfigurator().apply(rackMotorConfiguration);
    mRackMotor.setPosition(0.0);

    mIntakeRollerMotorFollow.setControl(new Follower(mIntakeRollerMotorMaster.getDeviceID(), MotorAlignmentValue.Opposed).withUpdateFreqHz(20));
  }

  public static void set(double speed) {
    mIntakeRollerMotorMaster.setControl(new VelocityTorqueCurrentFOC(speed).withUpdateFreqHz(20));
  }
  public static void stopIntake() {
    mIntakeRollerMotorMaster.setControl(new VoltageOut(0).withUpdateFreqHz(20));
  }

  public static void setRack(double target) {
    mRackMotor.setControl(new MotionMagicVoltage(target).withEnableFOC(true).withUpdateFreqHz(20));
  }

  public static void intake() {
    set(100);
  }
  public static void intakeSlow() {
    set(30);
  }

  public static void outtake() {
    set(-80);
  }
  public static void intakeUp() {
    setRack(STOW_POS);
  }

  public static void intakeOut() {
    setRack(DOWN_POS);
  }

  public static void intakeMid() {
    setRack(MIDDLE_POS);
  }
  
  public static double getRackPos() {
    return mRackMotor.getPosition().getValueAsDouble();
  }
  public static void allRollersIn() {
    intake();
    Indexer.IndexIn();
    Indexer.FeedIn();
  }

  public static void allRollersOut() {
    outtake();
    Indexer.IndexOut();
    Indexer.FeedOut();
  }

  public static void allRollersStop() {
    stopIntake();
    Indexer.stopIndex();
    Indexer.stopFeed();
  }
  



}
