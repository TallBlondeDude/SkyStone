package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "PID_Test", group = "Iterative Opmode")
public class PID_Test extends OpMode {
    // Declare OpMode members.
    public Robot Robot;
    double loopTime;
    double previousTime;
    Roadrunner Roadrunner;
    public void init() {

        Robot = new Robot(hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive")
                , telemetry);

    }

    @Override
    public void loop() {
        loopTime = previousTime - getRuntime();
        previousTime = getRuntime();


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