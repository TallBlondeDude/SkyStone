package org.firstinspires.ftc.teamcode.drive;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.control.*;

public class Roadrunner {
    PIDCoefficients coeffs;
    public Roadrunner(double kP, double kI, double kD){
        coeffs = new PIDCoefficients(kP, kI, kD);
    }
    PIDFController controller = new PIDFController(coeffs);
}
