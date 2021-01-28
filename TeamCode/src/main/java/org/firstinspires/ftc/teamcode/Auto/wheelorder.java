package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "wheel tuner", group = "Linear Opmode")
public class wheelorder extends LinearOpMode {
    // Declare OpMode members.
    public Robot Robot;
    private double mode = 1;
    Boolean hasSeenColor = false;
    Boolean firstFlag = false;

    public void runOpMode() {
        //build the robot
        Robot = new Robot(hardwareMap.get(Servo.class, "wobbleGrip"),
                hardwareMap.get(DcMotor.class, "wobbleMotor"),
                hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive")
                , hardwareMap.get(ColorSensor.class, "Color"), telemetry);

        //set the robot for the right mode
        Robot.setTeleMode();
        waitForStart();
        Robot.frontLeftDrive.setPower(.5);
        sleep(2000);
        Robot.frontLeftDrive.setPower(0);
        Robot.backLeftDrive.setPower(.5);
        sleep(2000);
        Robot.frontRightDrive.setPower(.5);
        Robot.backLeftDrive.setPower(0);
        sleep(2000);
        Robot.frontRightDrive.setPower(0);
        Robot.backRightDrive.setPower(.5);
        sleep(2000);

        stop();
    }
}