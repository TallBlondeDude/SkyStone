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

           //set the robot for the right mode
           Robot.setEncoderMode();
           Robot.setWheelPower(.7);
           if(hardwareMap.get(WebcamName.class, "Webcam 1").isAttached()) {
               Robot.initWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()), hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
           }
           else{
               telemetry.addData("camera null", "ur dumb00");
           }
           waitForStart();
           sleep(2000);
           List<Recognition> Recog = Robot.tfod.getUpdatedRecognitions();

           if(Recog.size() < 1){
               zoneNumber = 1;
           }
           else if(Recog.get(0).getLabel().equals("Single")){
               zoneNumber = 2;
           }

           else {

               zoneNumber = 3;
           }
           telemetry.addData("Found", Recog.get(0).getLabel());
           telemetry.addData("Zone", zoneNumber);
           telemetry.update();
           //branch the pathes
            if(zoneNumber == 1){
                fowardInches = 72;
                turnInches = 2;
                endDirection = 1;
            }

            else if(zoneNumber == 2){
                fowardInches = 108;
                doTurn = true;
                endDirection = 1;

                turnInches = Robot.nintydegreeturninches;
            }

            else{
                fowardInches = 120;
                turnInches = 2;
                endDirection = -1;

            }
            Robot.encoderDriveInches(fowardInches);
            while(Robot.isWheelsBusy()){}
            sleep(300);
            Robot.encoderTurnInches(turnInches);
            while(Robot.isWheelsBusy()){}
            sleep(300);
            Robot.lowerWobbleArm();
            sleep(600);
            Robot.wobbleMotor.setPower(0);
            Robot.openWobbleGrip();
            sleep(300);
            Robot.raiseWobbleArm();
            sleep(800);
            Robot.wobbleMotor.setPower(0);
            if(zoneNumber == 2){
                Robot.encoderTurnInches(turnInches);
                while(Robot.isWheelsBusy()){}
            }
            else if(zoneNumber == 3){
                Robot.encoderTurnInches(-turnInches);
                while(Robot.isWheelsBusy()){}
            }
            else {
                Robot.encoderTurnInches(Robot.nintydegreeturninches);
                while(Robot.isWheelsBusy()){}
            }
            sleep(400);
            if(zoneNumber == 1){
                Robot.encoderTurnInches(-Robot.nintydegreeturninches);
                while(Robot.isWheelsBusy()){}
                sleep(300);
                Robot.encoderDriveInches(36);
                while(Robot.isWheelsBusy()){}
                Robot.encoderTurnInches(Robot.nintydegreeturninches);
                while(Robot.isWheelsBusy()){}
                sleep(300);
            }
            Robot.setTeleMode();
            Robot.Drive((double) endDirection * 3.1415/2, 0, .6);

            while(Robot.Color.blue() < 700 && opModeIsActive()){
            }
            Robot.Drive((double) endDirection * -3.1415/2, 0, .5);
            sleep(300);
             Robot.Halt();

        // figure out how tall the stuff is i guess
          Robot.tfod.shutdown();

           stop();
    }
}