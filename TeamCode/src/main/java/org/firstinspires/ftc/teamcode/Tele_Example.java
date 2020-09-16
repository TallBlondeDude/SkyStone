package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Advanced Drive", group = "Iterative Opmode")
public class Tele_Example extends OpMode {
    // Declare OpMode members.
    public Robot Robot;

    public void init() {
        Robot = new Robot(hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive"), telemetry);
        Robot.setTeleMode();
    }

    @Override
    public void loop() {

        Robot.Drive(Robot.GetHeading(gamepad1.left_stick_x, gamepad1.left_stick_y), gamepad1.right_stick_x, Robot.GetMagnitude(gamepad1.left_stick_x, gamepad1.left_stick_y));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // stop the motors and show the telemetry
        Robot.Halt();
    }

}