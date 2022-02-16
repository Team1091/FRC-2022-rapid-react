package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {


    private SerialPort arduino; //The serial port that we try to communicate with

    private Timer timer; //The timer to keep track of when we send our signal to the Arduino

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

        //Create a timer that will be used to keep track of when we should send
        //a signal and start it.
        timer = new Timer();
        timer.start();
    }



//    @Override
//    public void robotPeriodic() {
//        //If more than 5 seconds has passed
//        if(timer.get() > 5) {
//            //Output that we wrote to the arduino, write our "trigger byte"
//            //to the arduino and reset the timer for next time
//            System.out.println("Wrote to Arduino");
//            arduino.write(new byte[] {0x12}, 1);
//            timer.reset();
//        }
//
//        //If we've received something, read the entire buffer
//        //from the arduino as a string
//        if(arduino.getBytesReceived() > 0) {
//            System.out.print(arduino.readString());
//        }
//    }
}
