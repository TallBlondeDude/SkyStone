package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.List;

@Autonomous(name = "Place Wobble Camera", group = "Linear Opmode")
public class placeWobbleCamera extends LinearOpMode {
    // Declare OpMode members.
    public Robot Robot;
    int zoneNumber;
    double fowardInches;
    double endDirection;
    double turnInches;

    Boolean doTurn = false;

    public void runOpMode() {
        Robot = new Robot(hardwareMap.get(Servo.class, "wobbleGrip"),
                hardwareMap.get(DcMotor.class, "wobbleMotor"),
                hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive")
                , hardwareMap.get(ColorSensor.class, "Color"), telemetry);
        List<Recognition> Recog = null;

        //set the robot for the right mode
        Robot.setEncoderMode();
        Robot.setWheelPower(.7);

        if (hardwareMap.get(WebcamName.class, "Webcam 1").isAttached()) {
            Robot.initWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()), hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        } else {
            telemetry.addData("camera null", "ur dumb00");
        }
        waitForStart();
        Robot.wobbleMotor.setTargetPosition(Robot.wobbleMotor.getCurrentPosition());
        Robot.wobbleMotor.setPower(1);
        Robot.wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.closeWobbleGrip();
        Robot.encoderDriveInches(10);
        while (Robot.isWheelsBusy()) {
        }
        sleep(2000);
        Recog = Robot.tfod.getUpdatedRecognitions();
        if(!Recog.isEmpty()) {
            telemetry.addData("Found", Recog.get(0).getLabel());
            telemetry.update();
            if (Recog.get(0).getLabel().equals("Single")) {
                zoneNumber = 2;
            }
            if (Recog.get(0).getLabel().equals("Quad")) {
                zoneNumber = 3;
            }
        }
        else{
            zoneNumber = 1;
        }

        telemetry.addData("Zone", zoneNumber);
        telemetry.update();
        Robot.encoderCrabwalkInches(20);
        while (Robot.isWheelsBusy()) {
        }
        //branch the pathes
        if (zoneNumber == 1) {
            fowardInches = 46;
            turnInches = 2;
            endDirection = 1;
        }
        else if (zoneNumber == 2) {
            fowardInches = 85;
            doTurn = true;
            endDirection = 1;

            turnInches = Robot.nintydegreeturninches;
        } else {
            fowardInches = 110;
            turnInches = 2;
            endDirection = -1;

        }
        Robot.encoderDriveInches(fowardInches/4);
        while (Robot.isWheelsBusy()) {
        }
        if(zoneNumber != 1){
            Robot.encoderTurnInches(4.75);
            while (Robot.isWheelsBusy()) {
            }
        }
        else{
            Robot.encoderTurnInches(1);
            while (Robot.isWheelsBusy()) {
            }
        }
        Robot.encoderDriveInches(3 * fowardInches/4);
        while (Robot.isWheelsBusy()) {
        }
        sleep(300);
        Robot.encoderTurnInches(turnInches);
        while (Robot.isWheelsBusy()) {
        }
        sleep(300);
        Robot.wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Robot.lowerWobbleArm();
        sleep(600);
        Robot.wobbleMotor.setPower(0);
        Robot.openWobbleGrip();
        sleep(300);
        Robot.raiseWobbleArm();
        sleep(800);
        Robot.wobbleMotor.setPower(0);
        if (zoneNumber == 2) {
            Robot.encoderTurnInches(turnInches);
            while (Robot.isWheelsBusy()) {
            }
        } else if (zoneNumber == 3) {
            Robot.encoderTurnInches(-turnInches);
            while (Robot.isWheelsBusy()) {
            }
        } else {
            Robot.encoderDriveInches(-10);
            while (Robot.isWheelsBusy()){

            }
            Robot.encoderTurnInches(12);
            while (Robot.isWheelsBusy()) {
            }
        }
        sleep(400);

        Robot.setTeleMode();
        Robot.Drive((double) endDirection * 3.1415 / 2, 0, .6);

        while (Robot.Color.blue() < 700 && opModeIsActive()) {
        }
        Robot.Drive((double) endDirection * -3.1415 / 2, 0, .5);
        sleep(300);
        Robot.Halt();

        // figure out how tall the stuff is i guess
        Robot.tfod.shutdown();

        stop();
    }
}