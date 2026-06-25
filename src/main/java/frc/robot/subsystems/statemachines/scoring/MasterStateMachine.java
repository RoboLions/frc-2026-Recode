public class MasterStateMachine extends StateMachine {
   
    public static IdleState idleState = new IdleState();
    public static IntakeState intakeState = new IntakeState();
    public static OuttakeState outtakeState = new OuttakeState();
    public static ShootState shootState = new ShootState();
    public static AlignState alignState = new AlignState();
    


    public MasterStateMachine() {
            idleState.build();
            intakeState.build();
            outtakeState.build();
            shootState.build(); 
            alignState.build();
            
    }


}
