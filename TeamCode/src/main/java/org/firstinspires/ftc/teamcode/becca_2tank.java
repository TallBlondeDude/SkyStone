package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "becaaa", group = "Iterative Opmode")
public class becca_2tank extends OpMode {
    // Declare OpMode members.
    DcMotor right;
    DcMotor left;
    public void init() {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
      float forward = gamepad1.left_stick_y;
      float turn = gamepad1.right_stick_x / (float) 1.75;
      float leftPower = forward + turn;
      float rightPower = forward - turn;

        if (leftPower > 1 || rightPower > 1){
            if (leftPower > rightPower){
                leftPower = 1;
                rightPower = turn/leftPower;
            }
            if (rightPower > leftPower){
                rightPower = 1;
                leftPower = leftPower/turn;
            }
        }

      left.setPower(leftPower);

      right.setPower(rightPower);
              /*
              left.SetPower(0 - 1)


               */

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

}