import java.util.Observable;
import java.util.Observer;

public class ObserverThread implements Observer {

    Thread[] threads;
    boolean passFound;

    public Thread[] getThreads() {
        return threads;
    }

    public void setThreads(Thread[] threads) {
        this.threads = threads;
    }

    public boolean isPassFound() {
        return passFound;
    }

    public void setPassFound(boolean passFound) {
        this.passFound = passFound;
    }

    public ObserverThread() {
        this.setPassFound(false);
    }

    public void update(Observable o, Object arg) {

        if ( ! (arg instanceof ObservableThreadArgs)) {
            return;
        }

        ExtractorThread extractorThread = (ExtractorThread)o;
        ObservableThreadArgs observableThreadArgs = (ObservableThreadArgs)arg;

        if (observableThreadArgs.getPasswordFound() != null) {
            this.setPassFound(true);
            this.stopThreads();
            System.out.println("###################\n###################\n");
            System.out.println(extractorThread.getThreadName() + " PASSWORD FOUND: " + observableThreadArgs.getPasswordFound());

            CommandRunner.execute(extractorThread.getCompressedFilePath(), observableThreadArgs.getPasswordFound());
        }
    }

    private void stopThreads() {
        for (Thread thread : this.getThreads()) {
            thread.interrupt();
        }
    }
}
