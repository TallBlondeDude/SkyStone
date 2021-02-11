package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "encoderreadout", group = "Iterative Opmode")
public class encoderreadout extends OpMode {
    // Declare OpMode members.
    public Robot Robot;
    private double mode = 1;
    public void init() {
        //build the robot

        Robot = new Robot(hardwareMap.get(Servo.class, "wobbleGrip"),
                hardwareMap.get(DcMotor.class, "wobbleMotor"),
                hardwareMap.get(DcMotor.class, "frontLeftDrive"),
                hardwareMap.get(DcMotor.class, "frontRightDrive"), hardwareMap.get(DcMotor.class,
                "backLeftDrive"), hardwareMap.get(DcMotor.class, "backRightDrive")
                , hardwareMap.get(ColorSensor.class, "Color"), telemetry);
        //set the robot ready for standered tele
        Robot.setPIDMode();
    }

    @Override
    public void loop() {
        telemetry.addData("FR", Robot.frontRightDrive.getCurrentPosition());
        telemetry.addData("BR", Robot.backRightDrive.getCurrentPosition());
        telemetry.addData("FL", Robot.frontLeftDrive.getCurrentPosition());
        telemetry.addData("BL", Robot.backLeftDrive.getCurrentPosition());

        //read the state of the joysticks
        double leftY = gamepad1.left_stick_y;
        double leftX = gamepad1.left_stick_x;
        //set the robot to drive as per the joysticks (Not using dead wheels)
        Robot.Drive(Robot.GetHeading(leftX, leftY), (float) (gamepad1.right_stick_x * mode), mode * Robot.GetMagnitude(leftX, leftY));
        // Push Ring into Launcher on right bumper
        telemetry.addData("Power", mode *  Robot.GetMagnitude(leftX, leftY));
        // somthing like
        /*
        if(gamepad1.right_bumper){
            //do the ring
        }

         */
        // Start Launcher on Left Bumper
        if(gamepad2.left_bumper){
            Robot.raiseWobbleArm();
        }


        // Stop Launcher on A
        else if(gamepad2.right_bumper){
            Robot.lowerWobbleArm();
        }
        else{
            Robot.wobbleMotor.setPower(0);
        }

        // Slow Mode on Left Trigger
        if(gamepad1.left_trigger > .4){
            mode = .5;
        }
        else{
            mode = 1;
        }
        // CONTROLLER TWO

        // Start Intake on X
    /*    if(gamepad2.x){
            intake.setPower(.6);
        }

     */
        // Stop Intake on B
    /*    if(gamepad2.b){
            intake.setPower(0);
        }

     */
        // Inverse Intake on A
     /*   if(gamepad2.a){
            intake.setPower(-1);
        }

      */
        // Raise Lower Wobble Goal lifter on left joystick Y Cord
        //   wobbleLifter.setPower(gamepad1.left_stick_y);
        // Close Wobble goal lifter on Right Bumper
        if(gamepad2.a){
            Robot.closeWobbleGrip();
        }
        // Open wobble goal grabber on left bumper
        if(gamepad2.b){
            Robot.openWobbleGrip();
        }




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