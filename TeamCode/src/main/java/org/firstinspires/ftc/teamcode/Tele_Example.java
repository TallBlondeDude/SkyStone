package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Basic Mech Drive", group = "Iterative Opmode")
public class Tele_Example extends OpMode {
    // Declare OpMode members.
    public Robot Robot;

    public void init() {
        //build the robot
        Robot = new Robot(hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive")
                , telemetry);
        //set the robot ready for standered tele
        Robot.setTeleMode();
    }

    @Override
    public void loop() {
        //read the state of the joysticks
        double leftY = gamepad1.left_stick_y;
        double leftX = gamepad1.left_stick_x;
        //set the robot to drive as per the joysticks (Not using dead wheels)
        Robot.Drive(Robot.GetHeading(leftX, leftY), gamepad1.right_stick_x, Robot.GetMagnitude(leftX, leftY));
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