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
    }

    @Override
    public void loop() {
        // Set the power of the motors to 1
        Robot.backRightDrive.setPower(1);
        Robot.frontRightDrive.setPower(1);
        Robot.frontLeftDrive.setPower(1);
        Robot.backLeftDrive.setPower(1);
        // telemetry to get the encoder values
        telemetry.addData("Front Left Encoder", Robot.frontLeftDrive.getCurrentPosition());
        telemetry.addData("Back Left Encoder", Robot.backLeftDrive.getCurrentPosition());
        telemetry.addData("Front Right Encoder", Robot.frontRightDrive.getCurrentPosition());
        telemetry.addData("Back Right Encoder", Robot.backRightDrive.getCurrentPosition());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // stop the motors and show the telemetry
        telemetry.addData("Task", "Halting");
        Robot.Halt();
        //    Servos.Halt();
    }

}