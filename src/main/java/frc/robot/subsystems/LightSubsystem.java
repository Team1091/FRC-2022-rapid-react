package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {

    private SerialPort arduino; //The serial port that we try to communicate with

    public LightSubsystem() {

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

    public void setLights(LightColors lightColors) {
        arduino.write(new byte[] {lightColors.arduinoColor()},1);
    }

    public enum LightColors {
        OFF((byte)0), RED((byte)0xff0000), BLUE((byte)0x0000ff);

        public final byte colorByte;
        LightColors(byte colorByte){
            this.colorByte = colorByte;
        }

        public byte arduinoColor(){
            return colorByte;
        }
    }
}
