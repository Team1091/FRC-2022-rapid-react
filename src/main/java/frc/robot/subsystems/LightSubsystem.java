package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.awt.*;
import java.nio.ByteBuffer;

public class LightSubsystem extends SubsystemBase {

    private SerialPort arduino; //The serial port that we try to communicate with
    private LightColors lightColors;

    public LightSubsystem() {
        this.lightColors = LightColors.OFF;

        //A "Capture Try/Catch". Tries all the possible serial port
        //connections that make sense if you're using the USB ports
        //on the RoboRIO. It keeps trying unless it never finds anything.
        try {
            arduino = new SerialPort(9600, SerialPort.Port.kUSB);
            System.out.println("Connected on kUSB!");
        } catch (Exception e) {
            System.out.println("Failed to connect on kUSB, trying kUSB 1");

            try {
                arduino = new SerialPort(9600, SerialPort.Port.kUSB1);
                System.out.println("Connected on kUSB1!");
            } catch (Exception e1) {
                System.out.println("Failed to connect on kUSB1, trying kUSB 2");

                try {
                    arduino = new SerialPort(9600, SerialPort.Port.kUSB2);
                    System.out.println("Connected on kUSB2!");
                } catch (Exception e2) {
                    System.out.println("Failed to connect on kUSB2, all connection attempts failed!");
                }
            }
        }
    }

    @Override
    public void periodic() {
        byte[] arduinoSender = ByteBuffer.allocate(4).putInt(lightColors.arduinoColor()).array();
        arduino.write(arduinoSender,arduinoSender.length);
    }

    public void setLights(LightColors lightColors) {
        this.lightColors = lightColors;
    }

    public enum LightColors {
        OFF((byte)Color.yellow.getRGB()), RED((byte)0xff0000), BLUE((byte)0x0000ff);

        public final byte colorByte;
        LightColors(byte colorByte){
            this.colorByte = colorByte;
        }

        public byte arduinoColor(){
            return colorByte;
        }
    }
}
