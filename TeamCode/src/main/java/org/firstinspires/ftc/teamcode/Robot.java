package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Robot{

    //init var
    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;
    public Telemetry telemetry;


    //init and declare var
    private static int maxSpeed = 1;
    //construct robot
    public Robot(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive, Telemetry T){
        this.frontLeftDrive = frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;
        this.telemetry = T;
    }


    public void setTeleMode() {
        telemetry.addData("Mode", "Init for Tele");

        //inits the motors in a way suitible for manual control
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public double GetHeading(double leftStickX, double leftStickY){
        // inverse tangent, gives the angle of the point
        double theta = Math.atan2(leftStickX, leftStickY);
        return (theta - 1.57079);
    }

    public double GetMagnitude(double leftStickX, double leftStickY) {
        //pretty much just pythag, figure out how far point is from center - combined the GetHeading direction and speed can be gotten
        return (Math.sqrt(leftStickY * leftStickY + leftStickX * leftStickX));
    }
    //for use in tele to get heading and speed
    public void Drive(double directionInRadians, float turnInRadians, double powerInPercentage) {
        telemetry.addData("Mode", "Driving");

        //takes input in direction, turning, and %of the max speed desired
        double wheelsSetA = Math.sin(directionInRadians - .7957) * powerInPercentage;
        double wheelsSetB = Math.sin(directionInRadians + .7957) * powerInPercentage;
        double motorCheck;
        //checks if one of the wheel sets is > 100% power, if so reduce it to one, and reduce the other by the same factor
        double[] powers = {wheelsSetA + turnInRadians, wheelsSetA - turnInRadians, wheelsSetB + turnInRadians, wheelsSetB - turnInRadians};
        double largestSpeedSoFar = powers[0];

        for (int i = 1; i < 4; i++) {
            if (Math.abs(powers[i]) > largestSpeedSoFar) {
                largestSpeedSoFar = Math.abs(powers[i]);
            }
        }
        motorCheck = maxSpeed / largestSpeedSoFar;
        for (int h = 0; h < 4; h++) {
            powers[h] = powers[h] * motorCheck;
        }
        this.backLeftDrive.setPower(powers[0]);
        this.frontRightDrive.setPower(powers[1]);
        this.frontLeftDrive.setPower(powers[2]);
        this.backRightDrive.setPower(powers[3]);

        telemetry.addData("back left:", powers[0]);
        telemetry.addData("front right:", powers[1]);
        telemetry.addData("front left:", powers[2]);
        telemetry.addData("back right:", powers[3]);
        telemetry.addData("turning", turnInRadians);
        telemetry.addData("power in percentage", powerInPercentage);
    }

    public void Halt(){
        //stops the wheels
        telemetry.addData("Mode", "Stopping");
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
    }

}
