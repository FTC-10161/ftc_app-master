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

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Hardware Class Version 1.0.1
 *
 * Changelog:
 * Version 1.0.0
 *      -Defines four motors for wheels and one servo for the color sensor arm
 * Version 1.2.0
 *      -Defines motors for the glyph lift and the "Jaws of Life"
 */
public class SexyHardware
{
    // Set maximum and minimum positions for the servo
    public double jewlThiefUp = 0.6;
    public double jewlThiefDown = 0.01;

    public double jawsClosed = 0.61;
    public double jawsOpen = 1.0;

    // Defines four main wheel motors
    public DcMotor frontLeftWheel  = null;
    public DcMotor frontRightWheel = null;
    public DcMotor backLeftWheel   = null;
    public DcMotor backRightWheel  = null;

    // Defines other motors
    public DcMotor glyphLift = null;
    public Servo jawsOfLife = null;

    // Defines servo controlling color sensor arm
    public Servo jewlThief = null;

    //Defines color sensor used in Autonomous Modes
    public ColorSensor sensorColor;

    /* local OpMode members. */
    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();

    public SexyHardware(){
    }

    // Initializes all motors, servos, and sensors; this method is called at the beginning of every program
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        // Defines and initializes motors
        frontLeftWheel = hwMap.get(DcMotor.class, "fl");
        frontRightWheel = hwMap.get(DcMotor.class, "fr");
        backLeftWheel = hwMap.get(DcMotor.class, "bl");
        backRightWheel = hwMap.get(DcMotor.class, "br");

        //Defines and initializes other motors
        glyphLift = hwMap.get(DcMotor.class, "gl");
        jawsOfLife = hwMap.get(Servo.class, "jl");

        // Defines and initializes servos
        jewlThief = hwMap.get(Servo.class, "jt");

        // Defines and initializes sensors
        sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");


        //Sets zero power behavior for Drive motors
        frontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Sets zero power behavior for other motors
        glyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Sets all motors to zero power
        frontLeftWheel.setPower(0);
        frontRightWheel.setPower(0);
        backLeftWheel.setPower(0);
        backRightWheel.setPower(0);

        // Sets initial servo positions
        jewlThief.setPosition(jewlThiefUp);
        jawsOfLife.setPosition(0.5);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        glyphLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
 }

