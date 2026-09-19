package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

public class Constants {

    public class FIELD {
        private static final Translation2d BLUE_HUB_POSE = new Translation2d(4.625, 4.034);
        private static final Translation2d RED_HUB_POSE = new Translation2d(11.915, 4.034);

        private static final Translation3d BLUE_PASS_TOP_FIELD = new Translation3d(1.5, 6.25, 0);
        private static final Translation3d RED_PASS_TOP_FIELD = new Translation3d(14.5, 6.25, 0);

        private static final Translation3d BLUE_PASS_BOTTOM_FIELD = new Translation3d(1.5, 1.75, 0);
        private static final Translation3d RED_PASS_BOTTOM_FIELD = new Translation3d(14.5, 1.75, 0);

        private static boolean isBlueAlliance() {
            return DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue)
                == DriverStation.Alliance.Blue;
        }

        public static Translation2d hubPose() {
            return isBlueAlliance() ? BLUE_HUB_POSE : RED_HUB_POSE;
        }

        public static Translation3d passTopField() {
            return isBlueAlliance() ? BLUE_PASS_TOP_FIELD : RED_PASS_TOP_FIELD;
        }

        public static Translation3d passBottomField() {
            return isBlueAlliance() ? BLUE_PASS_BOTTOM_FIELD : RED_PASS_BOTTOM_FIELD;
        }

    }

    public class CAN_IDS {
        public static final int FLYWHEEL_MOTOR_MASTER = 45;
        public static final int FLYWHEEL_MOTOR_FOLLOWER_UPPER_RIGHT = 46;
        public static final int FLYWHEEL_MOTOR_FOLLOWER_LOWER_LEFT = 47;
        public static final int FLYWHEEL_MOTOR_FOLLOWER_LOWER_RIGHT = 48;

        public static final int RACK_MOTOR = 50;
        public static final int FEEDER_MOTOR = 51;
        public static final int INTAKE_ROLLER_MASTER = 52;
        public static final int INTAKE_FOLLOWER_ROLLER = 53;
        public static final int INDEX_MOTOR_MASTER = 54;
        public static final int INDEX_MOTOR_FOLLOWER = 55;
    }

    public class Colors {
        public static final Color RED = new Color(255, 0, 0);
        public static final Color WHITE = new Color(255, 255, 255);
        public static final Color PURPLE = new Color(255, 0, 255);
        public static final Color GREEN = new Color(0, 255, 0);
    }
}
