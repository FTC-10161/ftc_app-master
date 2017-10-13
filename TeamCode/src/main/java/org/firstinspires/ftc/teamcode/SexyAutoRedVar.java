/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Autonomous Mode Version 1.0.0
 * Red Team Variant
 *
 * Changelog:
 * Version 1.0.0
 *      -Has Sexy lower servo-mounted arm between the two jewels, and determine which one to hit off based on its color
 *      -Still requires additional troubleshooting to determine accuracy
 * Version 1.0.1
 *      -Now accurately determines color of ball and responds appropriately
 */

@Autonomous(name="Hot like a red fire (AUTO)", group="Sexy")

public class SexyAutoRedVar extends LinearOpMode {

    // Declares objects and variables
    SexyHardware sexy = new SexyHardware();
    private ElapsedTime runtime  = new ElapsedTime();


    double pos = sexy.jewlThiefUp;
    int color = 0;
    float hsvValues[] = {0F, 0F, 0F};
    final double SCALE_FACTOR = 255;

    @Override
    public void runOpMode() {

        // Initializes the hardware class (motors, servos, sensors, etc.)
        sexy.init(hardwareMap);

        //For troubleshooting, sends message if initialization is successful
        telemetry.addData("Status", " My body is ready.");
        telemetry.update();

        // Wait for driver to press PLAY
        waitForStart();

        // Lowers arm between the jewels
        do {
            sexy.jewlThief.setPosition(pos);
            pos -= 0.005;
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        while (opModeIsActive() && (pos >= sexy.jewlThiefDown));

        // Color sensor detects color of left side jewel, assigning it an integer value, 1 for blue and 2 for red
        do {
            Color.RGBToHSV((int) (sexy.sensorColor.red() * SCALE_FACTOR),
                    (int) (sexy.sensorColor.green() * SCALE_FACTOR),
                    (int) (sexy.sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);
            if (sexy.sensorColor.blue() > sexy.sensorColor.red()) {
                color = 1;
            } else if(sexy.sensorColor.blue() < sexy.sensorColor.red()) {
                color = 2;
            } else {
                color = 0;
            }
            runtime.reset();
        }
        while (opModeIsActive() && color == 0);

        // Depending on which color the left side jewel is determined to be, the sexy will shift in a different direction in order to knock off the red jewel
        do {
            if(color == 1) {
                sexy.frontLeftWheel.setPower(0.5);
                sexy.frontRightWheel.setPower(0.5);
                sexy.backLeftWheel.setPower(0.5);
                sexy.backRightWheel.setPower(0.5);
                telemetry.addData("Color: ", "Blue");
            } else if(color == 2) {
                sexy.frontLeftWheel.setPower(-0.5);
                sexy.frontRightWheel.setPower(-0.5);
                sexy.backLeftWheel.setPower(-0.5);
                sexy.backRightWheel.setPower(-0.5);
                telemetry.addData("Color: ", "Red");
            }
        }
        while(opModeIsActive() && (runtime.seconds() <0.176432));

        // Turns of motors after the jewel is knocked off
        sexy.frontLeftWheel.setPower(0);
        sexy.frontRightWheel.setPower(0);
        sexy.backLeftWheel.setPower(0);
        sexy.backRightWheel.setPower(0);

        // For troubleshooting, sends message after autonomous program is complete
        telemetry.addData("Status", " okay baby we done");
        telemetry.update();
        sleep(1000);
    }
}
