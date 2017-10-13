/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * OmniDrive Version 1.0.1
 *
 * Changelog
 * Version 1.0.0
 *      -Early version of our OmniDrive formula
 *      -Displays position/power of servos and motors
 *
 * Version 1.0.1
 *      -Changed zeroPowerBehavior to BRAKE
 *      -Fixed bugs in OmniDrive formula
 * Version 1.2.0
 *      -Made very important changes to telemetry to better match the theme of our sexy (her name is "Sexy")
 *
 */

@TeleOp(name="What a sexy OmniDrive TeleOpMode", group="Sexy")
public class SexyOmniDrive extends LinearOpMode {

    // Declares object, in this case, just the hardware map
    SexyHardware sexy = new SexyHardware();


    @Override
    public void runOpMode() {
        double clipRange = 1;
        double otherClip = 0.6;
        double pos = sexy.jewlThiefUp;
        double jawsPos = 0.5;

        // Initializes the hardware class (motors, servos, sensors, etc.)
        sexy.init(hardwareMap);

        //For troubleshooting, sends message if initialization is successful
        telemetry.addData("Status", " Paint me like one of your French girls.");
        telemetry.update();

        // Wait for driver to press PLAY
        waitForStart();

        // Run until driver presses STOP
        while (opModeIsActive()) {

            double liftSpeed = gamepad2.right_stick_y;
            //Creates variables based off of joystick input
            double C1RX = -gamepad1.right_stick_x *0.8;
            double C1LY = gamepad1.left_stick_y *otherClip;
            double C1LX = gamepad1.left_stick_x *otherClip;
            // Literally pure magic
            double FrontLeft = C1LY - C1LX + C1RX;
            double RearLeft =  C1LY + C1LX + C1RX;
            double FrontRight = C1LY + C1LX - C1RX;
            double RearRight = C1LY - C1LX - C1RX;

            //Clips the power for all drive motors using a variable, this allows us to change our sexy's max speed
            FrontRight = Range.clip(FrontRight, -clipRange, clipRange);
            FrontLeft = Range.clip(FrontLeft, -clipRange, clipRange);
            RearRight = Range.clip(RearRight, -clipRange, clipRange);
            RearLeft = Range.clip(RearLeft, -clipRange, clipRange);


            //Sets power to each drive motor
            sexy.frontLeftWheel.setPower(FrontLeft);
            sexy.frontRightWheel.setPower(-FrontRight);
            sexy.backLeftWheel.setPower(RearLeft);
            sexy.backRightWheel.setPower(-RearRight);

            //Allows driver to hold the right bumper to decrease Sex's max speed for more precise control
            if(gamepad1.right_bumper) {
                otherClip = 0.3;
                clipRange = 0.5;
            }else if(gamepad1.left_bumper)  {
                otherClip = 0.7;
            }else{
                otherClip = 0.6;
                clipRange = 1;
            }

            //Incrementally moves jewlthief arm between its up and down positions
            if(gamepad1.dpad_up && pos <= sexy.jewlThiefUp){
                pos += 0.01;
            }else if(gamepad1.dpad_down && pos >= sexy.jewlThiefDown){
                pos -= 0.01;
            }else{

            }

            if(gamepad2.y && jawsPos >= sexy.jawsClosed){
                jawsPos -= 0.03;
            }else if(gamepad2.a && jawsPos <= sexy.jawsOpen){
                jawsPos += 0.03;
            }else{

            }

            sexy.jewlThief.setPosition(pos);
            sexy.jawsOfLife.setPosition(jawsPos);
            sexy.glyphLift.setPower(liftSpeed/2);




            telemetry.addData("Front Right",  "%.2f", FrontRight);
            telemetry.addData("Front Left",  "%.2f", FrontLeft);
            telemetry.addData("Rear Right",  "%.2f", RearRight);
            telemetry.addData("Rear Left",  "%.2f", RearLeft);
            telemetry.addData("POS",  "%.2f", pos);
            telemetry.addData("JAWS POS",  "%.2f", jawsPos);
            telemetry.addData("LIFT SPEED",  "%.2f", liftSpeed);

            telemetry.update();


            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
