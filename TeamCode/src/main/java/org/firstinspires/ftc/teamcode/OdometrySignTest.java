package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Odometry Sign Test", group="Debug")
public class OdometrySignTest extends LinearOpMode {

    DcMotor leftOdo, rightOdo, strafeOdo;

    @Override
    public void runOpMode() {
        // Map your odometry wheel motors/encoders
        leftOdo   = hardwareMap.get(DcMotor.class, "LFMotor");
        rightOdo  = hardwareMap.get(DcMotor.class, "RBMotor");
        strafeOdo = hardwareMap.get(DcMotor.class, "LBMotor");

        // We don't want them driving, just reading
        leftOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        strafeOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        strafeOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Read encoder positions
            int leftPos   = leftOdo.getCurrentPosition();
            int rightPos  = rightOdo.getCurrentPosition();
            int strafePos = strafeOdo.getCurrentPosition();

            telemetry.addLine("Push robot in different directions:");
            telemetry.addLine("Forward → Left & Right should increase");
            telemetry.addLine("Right strafe → Strafe should increase");
            telemetry.addLine("Rotate CW → Left increases, Right decreases");

            telemetry.addData("Left Odo",   leftPos);
            telemetry.addData("Right Odo",  rightPos);
            telemetry.addData("Strafe Odo", strafePos);

            telemetry.update();
        }
    }
}
