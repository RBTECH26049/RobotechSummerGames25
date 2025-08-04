package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utilities.RtLog;

public class RtFieldDrive {
    private Telemetry m_telemetry;
    private DcMotor m_dtLeftBackMotor;
    private DcMotor m_dtRightBackMotor;
    private DcMotor m_dtRightFrontMotor;
    private DcMotor m_dtLeftFrontMotor;
    private IMU     m_imu;
    private RtLog   m_rtLog;
    public RtFieldDrive(DcMotor parLbMotor, DcMotor parRbMotor, DcMotor parRfMotor, DcMotor parLfMotor, Telemetry parTelemetry, IMU parIMU, RtLog parRtLog) {
        m_dtLeftBackMotor   = parLbMotor;
        m_dtRightBackMotor  = parRbMotor;
        m_dtRightFrontMotor = parRfMotor;
        m_dtLeftFrontMotor  = parLfMotor;
        m_telemetry         = parTelemetry;
        m_imu               = parIMU;
        m_rtLog             = parRtLog;
    }
    public void FieldDrive(double parx, double pary, double parrx, boolean reset){
        // Retrieve the IMU from the hardware map

        // Adjust the orientation parameters to match your robot

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward


        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (reset) {
            m_imu.resetYaw();
        }

        double botHeading = m_imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        m_rtLog.print("Botheading", "double", botHeading);
        

        // Rotate the movement direction counter to the bot's rotation
        double rotX = parx * Math.cos(-botHeading) - pary * Math.sin(-botHeading);
        double rotY = parx * Math.sin(-botHeading) + pary * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(parrx), 1);
        double frontLeftPower = (rotY - rotX - parrx) / denominator;
        double backLeftPower = (rotY + rotX - parrx) / denominator;
        double frontRightPower = (rotY + rotX + parrx) / denominator;
        double backRightPower = (rotY - rotX + parrx) / denominator;
        // we changed the positive to negative. Wheel position is different than GM 0
        m_dtLeftFrontMotor.setPower(frontLeftPower);
        m_dtLeftBackMotor.setPower(backLeftPower);
        m_dtRightFrontMotor.setPower(frontRightPower);
        m_dtRightBackMotor.setPower(backRightPower);
    }
}