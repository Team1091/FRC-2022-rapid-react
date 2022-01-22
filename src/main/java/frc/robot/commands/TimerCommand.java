package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TimerCommand extends CommandBase {
    private final Timer timer;
    private final double duration;

    public TimerCommand(double duration) {
        this.timer = new Timer();
        this.duration = duration;
    }


    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(duration);
    }
}

