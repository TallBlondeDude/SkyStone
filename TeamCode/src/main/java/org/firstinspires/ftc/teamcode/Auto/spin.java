package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Spin ", group = "Linear Opmode")
public class spin extends LinearOpMode {
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
        Robot.Drive(1,1,0);
        sleep(40000);
        Robot.Halt();

        stop();
    }
}