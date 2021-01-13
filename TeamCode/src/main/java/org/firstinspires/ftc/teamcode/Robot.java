package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class Robot{
  //  public Vector2d position;
    //init var
    private double wobbleArmPowerUp = .7;
    private  double wobbleArmPowerDown = -.3;
    public DcMotor frontLeftDrive;
    public  final double nintydegreeturninches = 4 * 3.1415;
    public DcMotor frontRightDrive;
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;
    public DcMotor wobbleMotor;
    public ColorSensor Color;
    private final double wheelSizeMM = 100;
    public Telemetry telemetry;
    public Servo wobbleGrip;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    VuforiaLocalizer vuforia;
    OpenGLMatrix lastLocation = null;
    public TFObjectDetector tfod;
    int captureCounter = 0;

    //init and declare var
    private static int maxSpeed = 1;
    //construct robot
    public Robot(Servo wobbleGrip, DcMotor wobbleMotor, DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive, ColorSensor Color, Telemetry T){
        this.frontLeftDrive = frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.wobbleMotor = wobbleMotor;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;
        this.Color = Color;
        this.telemetry = T;
        this.wobbleGrip = wobbleGrip;
    }

    public double inchesToTicks(double inches){
        return ((inches/3.9) * 537.6);

    }
    public void encoderDriveInches(double inches){
        int ticks = (int) inchesToTicks(inches);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + ticks);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + ticks);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + ticks);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + ticks);
    }
    public boolean isWheelsBusy(){
        if(backLeftDrive.isBusy() ||frontLeftDrive.isBusy() || frontRightDrive.isBusy() || backRightDrive.isBusy()){
            return true;
        }
        else{
            return false;
        }
    }

    public void encoderTurnInches(double inches){
        int ticks = (int) inchesToTicks(inches);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - ticks);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() - ticks);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + ticks);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + ticks);
    }
    public void openWobbleGrip(){
    wobbleGrip.setPosition(0);
    }
    public void closeWobbleGrip(){
    wobbleGrip.setPosition(1);
    }
    public void raiseWobbleArm(){
    wobbleMotor.setPower(.81);
    }
    public void lowerWobbleArm(){
        wobbleMotor.setPower(-.4);
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
        wobbleMotor.setTargetPosition(wobbleMotor.getCurrentPosition());

        wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wobbleMotor.setDirection(DcMotor.Direction.FORWARD);

    }
    //to init webcame use the parameters: initWebcam(hardwareMap.get(WebcamName.class, "Webcam 1") ,hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()), hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
    public void initWebcam(WebcamName webcamName, int cameraMonitorViewId, int tfodid){
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AVdZkZP/////AAABmaOjnJKfN0gbuOuEAxggg2YG35nh19lFl4mXMGCImmPnolZt3Aoq5faKYv9DtSM2WmIrgRkHQpujQ01O0DdVlCmkDPLSZ/ZXt8p6zD5RzvK3g8CATon7u8UgZyz9XtcBqN7iZAjloDBd6N6ApNpwvSUJ4EL7fApBclhKj330U9wd+ZokjfMPgNYY84fQPAoafydsuUQ13WG6vSmDfG3z+oB3fPLLGcuz/tpavDMI6Y+jJHChvDeyEHuP44Ol7EFgNwjpO1i6gRB5RNNyC4+EJLHNxfc6vAp8xfvyN7V13bcc7NLmeSk5WEeE/LCFMBoSBLIBVJeiYeVhNlljHAqFang01+kdHVDhiraIaJYubHVy";
        parameters.cameraName = webcamName;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        vuforia.enableConvertFrameToBitmap();
        int tfodMonitorViewId = tfodid;
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.5, 16.0/9.0);
        }
    }

    public void setWheelPower(double power){
        backLeftDrive.setPower(power);
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void setEncoderMode() {
        telemetry.addData("Mode", "Init for Encoder");

        //inits the motors in a way suitible for manual control
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //stop and resest enc
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //set pos to current pos
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition());
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition());
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition());
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition());

        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wobbleMotor.setTargetPosition(wobbleMotor.getCurrentPosition());
        wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wobbleMotor.setDirection(DcMotor.Direction.FORWARD);
    }
    public void setPIDMode() {
        telemetry.addData("Mode", "Init for PID");

        //inits the motors in a way suitible for manual control
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wobbleMotor.setDirection(DcMotor.Direction.FORWARD);
    }
    public String getRingStack(){
        int onestack = 0;
        int quadstack = 0;
        for (int b = 0; b < tfod.getRecognitions().size(); b ++){
            if(tfod.getRecognitions().get(b).getLabel() == "quad"){
                quadstack ++;
            }
            else if(tfod.getRecognitions().get(b).getLabel() == "single"){
                onestack ++;
            }
        }
        if(onestack > quadstack){
            return "One";
        }
        else if(quadstack > onestack){
            return "Quad";
        }
        else if(quadstack > 1){
            return "Quad";
        }
        else{
            return "Zero";
        }
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
        if(largestSpeedSoFar > 1) {
            for (int h = 0; h < 4; h++) {
                powers[h] = powers[h] * motorCheck;
            }
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
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addData("Mode", "Stopping");
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
    }

}
