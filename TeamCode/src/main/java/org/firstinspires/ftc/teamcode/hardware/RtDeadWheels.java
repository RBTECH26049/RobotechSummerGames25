package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RtDeadWheels;
public class RtDeadWheels {
    private Telemetry m_telemetry;
    private DcMotor m_leftEncoder;
    private DcMotor m_rightEncoder;
    private DcMotor m_centerEncoder;
    private final double TICKS_PER_REV = 537.6; // adjust for your goBILDA motor gear ratio
    private final double WHEEL_DIAMETER_INCHES = 2.0;
    private final double GEAR_RATIO = 1.0;
    public RtDeadWheels(DcMotor parleftEncoder, DcMotor parrightEncoder, DcMotor parcenterEncoder, Telemetry parTelemetry){
        m_leftEncoder = parleftEncoder;
        m_rightEncoder = parrightEncoder;
        m_centerEncoder = parcenterEncoder;
        m_telemetry = parTelemetry;

        // Reset and configure encoders
        m_leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_centerEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        m_leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m_rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m_centerEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Convert encoder ticks to inches
    private double ticksToInches(int ticks) {
        return (ticks / TICKS_PER_REV) * Math.PI * WHEEL_DIAMETER_INCHES * GEAR_RATIO;
    }

    // Method to update telemetry with encoder values
    public void updateTelemetry() {
        int leftTicks = m_leftEncoder.getCurrentPosition();
        int rightTicks = m_rightEncoder.getCurrentPosition();
        int centerTicks = m_centerEncoder.getCurrentPosition();

        m_telemetry.addData("Left Encoder (ticks)", leftTicks);
        m_telemetry.addData("Right Encoder (ticks)", rightTicks);
        m_telemetry.addData("Center Encoder (ticks)", centerTicks);

        m_telemetry.addData("Left (in)", ticksToInches(leftTicks));
        m_telemetry.addData("Right (in)", ticksToInches(rightTicks));
        m_telemetry.addData("Center (in)", ticksToInches(centerTicks));

        m_telemetry.update();
    }

    // Optional: getters
    public double getLeftInches() {
        return ticksToInches(m_leftEncoder.getCurrentPosition());
    }

    public double getRightInches() {
        return ticksToInches(m_rightEncoder.getCurrentPosition());
    }

    public double getCenterInches() {
        return ticksToInches(m_centerEncoder.getCurrentPosition());
    }
}


