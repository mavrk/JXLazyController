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
import com.mavrk.jxlazycontroller.controller.keyboard.KeyboardUI;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanatt Abrol
 */
public class XinputController {

    static float INPUT_DEADZONE = 7849f;
    static float MULTIPLIER = (float) 0.0001;
    static float[] SPEED = {3f, 2f, 1f, 0.8f, 0.4f};
    static int SPEED_INDEX = 2;

    static boolean IS_LISTENING = false;
    static boolean IS_START_PRESSED = false;
    static boolean IS_BACK_PRESSED = false;
    static boolean IS_LB_PRESSED = false;
    static boolean IS_RB_PRESSED = false;
    static boolean IS_LT_PRESSED = false;
    static boolean IS_RT_PRESSED = false;
    static boolean IS_L3_PRESSED = false;

    XInputDevice device;
    XInputDeviceListener deviceListener;
    XInputComponents components;
    XInputAxes axes;
    XInputComponentsDelta delta;
    XInputAxesDelta axesDelta;
    Point mouseLocation;
    Robot robot;
    KeyboardUI keyboardUI;

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

                    Logger.getLogger(XinputController.class.getName()).log(Level.INFO, button.name() + (pressed ? " pressed" : " released"));

                    //If NOT listening state 
                    if (pressed) {
                        switch (button.name()) {
                            case "BACK":
                                IS_BACK_PRESSED = true;
                                break;
                            case "START":
                                IS_START_PRESSED = true;
                                break;
                        }
                    } else {
                        switch (button.name()) {
                            case "BACK":
                                IS_BACK_PRESSED = false;
                                break;
                            case "START":
                                IS_START_PRESSED = false;
                                break;

                        }
                    }

                    //see if listening state needs to be changed
                    if (IS_BACK_PRESSED && IS_START_PRESSED) {
                        IS_LISTENING = !IS_LISTENING;
                    }

                    if (!IS_LISTENING) {
                        return;
                    }

                    //ADD Mappings for remaining keys
                    if (pressed) {
                        switch (button.name()) {
                            case "BACK":
                                robot.keyPress(KeyEvent.VK_ESCAPE);
                                break;

                            case "START":
                                robot.keyPress(KeyEvent.VK_WINDOWS);
                                break;

                            case "Y":
                                //show or hide keyboard
                                if (keyboardUI == null) {
                                    keyboardUI = new KeyboardUI();
                                    //keyboardUI.setVisible(true);
                                    keyboardUI.setAlwaysOnTop(true);
                                }
                                if (keyboardUI.isVisible()) {
                                    keyboardUI.setVisible(false);
                                } else {
                                    keyboardUI.setVisible(true);
                                    keyboardUI.toFront();
                                }
                                break;

                            case "LEFT_SHOULDER":
                                IS_LB_PRESSED = true;
                                robot.keyPress(KeyEvent.VK_CONTROL);
                                break;

                            case "RIGHT_SHOULDER":
                                IS_RB_PRESSED = true;
                                robot.keyPress(KeyEvent.VK_ALT);
                                break;

                            case "A":
                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                break;

                            case "X":
                                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                                break;
                                
                            case "B":
                                robot.keyPress(KeyEvent.VK_ENTER);
                                break;

                            case "DPAD_LEFT":
                                robot.keyPress(KeyEvent.VK_LEFT);
                                robot.keyRelease(KeyEvent.VK_LEFT);
                                break;

                            case "DPAD_RIGHT":
                                robot.keyPress(KeyEvent.VK_RIGHT);
                                break;

                            case "DPAD_UP":
                                robot.keyPress(KeyEvent.VK_UP);
                                break;

                            case "DPAD_DOWN":
                                robot.keyPress(KeyEvent.VK_DOWN);
                                break;

                            case "LEFT_THUMBSTICK":
                                if (!IS_L3_PRESSED) {
                                    robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                                    IS_L3_PRESSED = true;
                                } else {
                                    robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                                    IS_L3_PRESSED = false;
                                }
                                break;

                            case "RIGHT_THUMBSTICK":
                                robot.keyPress(KeyEvent.VK_WINDOWS);
                                robot.keyPress(KeyEvent.VK_S);
                                robot.keyRelease(KeyEvent.VK_S);
                                robot.keyRelease(KeyEvent.VK_WINDOWS);
                                break;
                        }
                    } else {
                        switch (button.name()) {
                            case "BACK":
                                robot.keyRelease(KeyEvent.VK_ESCAPE);
                                break;

                            case "START":
                                robot.keyRelease(KeyEvent.VK_WINDOWS);
                                break;

                            case "LEFT_SHOULDER":
                                IS_LB_PRESSED = false;
                                robot.keyRelease(KeyEvent.VK_CONTROL);
                                break;

                            case "RIGHT_SHOULDER":
                                IS_RB_PRESSED = false;
                                robot.keyRelease(KeyEvent.VK_ALT);
                                break;

                            case "A":
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                break;

                            case "X":
                                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                                break;

                            case "DPAD_LEFT":
                                robot.keyRelease(KeyEvent.VK_LEFT);
                                break;

                            case "DPAD_RIGHT":
                                robot.keyRelease(KeyEvent.VK_RIGHT);
                                break;

                            case "DPAD_UP":
                                robot.keyRelease(KeyEvent.VK_UP);
                                break;

                            case "DPAD_DOWN":
                                robot.keyRelease(KeyEvent.VK_DOWN);
                                break;
                        }
                    }

                    if (IS_LB_PRESSED && IS_RB_PRESSED) {
                        cycleThroughSpeedLevels();
                    }
                }
            };

            device.addListener(deviceListener);
            components = device.getComponents();
            axes = components.getAxes();
            robot = new Robot();
            pollAndAct();
        }
        catch (XInputNotLoadedException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AWTException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(XinputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pollAndAct() throws InterruptedException {
        mouseLocation = MouseInfo.getPointerInfo().getLocation();
        float LX, LY, RX, RY, LT, RT;
        float magnitudeL, magnitudeR;
        while (device.poll()) {
            if (IS_LISTENING) {
                //LEFT AXES 
                LX = axes.lxRaw;
                LY = axes.lyRaw;
                magnitudeL = (float) Math.sqrt(LX * LX + LY * LY);

                if (magnitudeL > INPUT_DEADZONE) {
                    if (magnitudeL > 32767) {
                        magnitudeL = 32767;
                    }
                    magnitudeL -= INPUT_DEADZONE;
                    moveMouse(LX, LY);
                } else //if the controller is in the deadzone zero out the magnitude
                {
                    magnitudeL = 0f;
                }

                //RIGHT AXES
                RX = axes.rxRaw;
                RY = axes.ryRaw;
                magnitudeR = (float) Math.sqrt(RX * RX + RY * RY);
                if (magnitudeR > INPUT_DEADZONE) {
                    if (magnitudeR > 32767) {
                        magnitudeR = 32767;
                    }
                    magnitudeR -= 32767;
                    int scrollAmount = (int) (RY * -0.0001);
                    System.out.println("SA " + scrollAmount);
                    robot.mouseWheel(scrollAmount);
                    Thread.sleep(50);
                } else {
                    magnitudeR = 0f;
                }

            } else {
                //WAIT till listening enabled
            }

        }

    }

    private void moveMouse(float LX, float LY) throws InterruptedException {
        mouseLocation.x += (int) (LX * MULTIPLIER * SPEED[SPEED_INDEX]);
        mouseLocation.y -= (int) (LY * MULTIPLIER * SPEED[SPEED_INDEX]);
        robot.mouseMove(mouseLocation.x, mouseLocation.y);
        //System.out.println(mouseLocation.x + "," + mouseLocation.y);
        normalizeMouseLocation();
        Thread.sleep(5);
    }

    private void normalizeMouseLocation() {
        if (mouseLocation.x < 0) {
            mouseLocation.x = 0;
        } else if (mouseLocation.x > 1920) {
            mouseLocation.x = 1920;
        }

        if (mouseLocation.y < 0) {
            mouseLocation.y = 0;
        } else if (mouseLocation.y > 1080) {
            mouseLocation.y = 1080;
        }
    }

    private void cycleThroughSpeedLevels() {
        SPEED_INDEX = (SPEED_INDEX + 1) % 5;
    }

    public static void main(String[] args) {
        XinputController xinputController = new XinputController();
    }
}
