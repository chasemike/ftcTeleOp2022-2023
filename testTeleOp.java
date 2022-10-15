package org.firstinspires.ftc.robotcontroller.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "myBabyTeleOp", group = "InteractivemyBabyTeleOp")
public class testTeleOp extends OpMode {

    //    private DcMotor frontLeft;
//    private DcMotor frontRight;
//    private DcMotor backLeft;
//    private DcMotor backRight;
    public Robot robot = new Robot(); // This is for init method and to set power to each of our motors calling a method

    @Override
    public void init() {
        robot.init(hardwareMap); // This sets up the motors for use
    }

    @Override
    public void loop() {
        /*
        We only gather x and y value from the left stick for robot movement. However right stick only uses x
        value to turn robot, y value is not needed.
         */
        double x = gamepad1.left_stick_x; // Left stick x value [-1,1]
        double y = -gamepad1.left_stick_y; // Left stick y value [-1,1]
        double turn = Math.round(gamepad1.right_stick_x); // Right stick x value [-1, 1]

        double speed = Math.hypot((x), (y)); // Desired robot speed −1 to 1
        double angle = Math.atan2((y), (x)); // Desired robot angle 0 to 2pie

        double sin = Math.round(Math.sin(angle - (Math.PI/4))); // Force against wheels for FR and BL or red wheels
        double cos = Math.round(Math.cos(angle - (Math.PI/4))); // Force against wheels for FL and BR or blue wheels
        double maxPower = Math.max(Math.abs(sin), Math.abs(cos)); // This accounts for the the full range of the controller for power limits

        /*
        The next four line sets power to a specific variable name
        for a motor which is later called using robot.mecDrive()
         */

        double frontLeft = ((speed * cos) + turn) / maxPower; // FL power
        double frontRight = ((speed * sin) - turn) / maxPower; // FR power
        double backLeft = ((speed * sin) + turn )/ maxPower;
        double backRight = ((speed * cos) - turn) / maxPower; // BR power


        if ((speed + Math.abs(turn)) > 1) { // Makes sure we don't exceed the [-1,1] motor speed range
            frontLeft /= speed + turn;
            frontRight /= speed + turn;
            backLeft /= speed + turn;
            backRight /= speed + turn;
        }

        /*
        Telemetry is like a System.out.println() that uses to be read by the driver
        on the driver station.
         */

        telemetry.addData("x", x);
        telemetry.addData("y", y);
        telemetry.addData("Turn", turn);
        telemetry.addData("angle (tan)", angle);
        telemetry.addData("speed (hypo)", speed);
        telemetry.addData("frontLeft", frontLeft);
        telemetry.addData("frontRight", frontRight);
        telemetry.addData("backLeft", backLeft);
        telemetry.addData("backRight", backRight);
        //telemetry.addData("inside", inside);

        robot.mecDrive(-frontLeft, frontRight, -backLeft, -backRight); // Calls the robot class to set motor speed to each wheels
        telemetry.update(); // Updates telemetry at the very end
    }
}