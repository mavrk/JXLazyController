/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavrk.jxlazycontroller.controller;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputAxesDelta;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputComponentsDelta;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.SimpleXInputDeviceListener;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanatt Abrol
 */
public class XinputController {

    static float INPUT_DEADZONE = 7849f;

    XInputDevice device;
    XInputDeviceListener deviceListener;
    XInputComponents components;
    XInputAxes axes;
    XInputComponentsDelta delta;
    XInputAxesDelta axesDelta;
    Point mouseLocation;
    Robot robot;

    public XinputController() {
        try {
            device = XInputDevice.getDeviceFor(0);

            deviceListener = new SimpleXInputDeviceListener() {
                @Override
                public void connected() {
                    // Resume the game
                    System.out.println("device connected");
                }

                @Override
                public void disconnected() {
                    // Pause the game and display a message
                    System.out.println("device disconected  ");
                }

                @Override
                public void buttonChanged(final XInputButton button, final boolean pressed) {
                    // The given button was just pressed (if pressed == true) or released (pressed == false)
                    switch (button.name()) {
                        case "BACK":
                            mouseLocation = MouseInfo.getPointerInfo().getLocation();
                            break;
                    }
                }
            };

            device.addListener(deviceListener);
            components = device.getComponents();
            axes = components.getAxes();
            robot = new Robot();
            mouseLocation = MouseInfo.getPointerInfo().getLocation();
            pollAndAct();
        } catch (XInputNotLoadedException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pollAndAct() throws InterruptedException {
        float LX, LY, RX, RY, LT, RT;
        float magnitude, normalizedLX, normalizedLY, normalizedMagnitude = 0f;
        System.out.println(INPUT_DEADZONE);
        while (device.poll()) {
            LX = axes.lxRaw;
            LY = axes.lyRaw;
            //System.out.println("LX " + LX + " LY " + LY);
            magnitude = (float) Math.sqrt(LX * LX + LY * LY);
            
            if (magnitude > INPUT_DEADZONE) {
                if (magnitude > 32767) {
                    magnitude = 32767;
                }
                magnitude -= INPUT_DEADZONE;
                normalizedMagnitude = magnitude / (32767 - INPUT_DEADZONE);
                moveMouse(LX, LY, (float) 0.0001);
            }
            else //if the controller is in the deadzone zero out the magnitude
            {
                magnitude = 0f;
                normalizedMagnitude = 0f;
            }
            
        }

    }
    
    private void moveMouse(float LX, float LY, float mult) throws InterruptedException{
        mouseLocation.x += (int) (LX * mult);
        mouseLocation.y -= (int) (LY * mult);
        robot.mouseMove(mouseLocation.x, mouseLocation.y);
        System.out.println(mouseLocation.x + "," + mouseLocation.y);
        normalizeMouseLocation();
        Thread.sleep(5);
    }
    
    private void normalizeMouseLocation(){
        if(mouseLocation.x < 0) mouseLocation.x = 0;
        else if(mouseLocation.x > 1920) mouseLocation.x = 1920;
        
        if(mouseLocation.y < 0) mouseLocation.y = 0;
        else if(mouseLocation.y > 1080) mouseLocation.y = 1080;
    }

    public static void main(String[] args) {
        XinputController xinputController = new XinputController();
    }
}
