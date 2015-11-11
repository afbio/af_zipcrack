import java.util.Observable;
import java.util.Observer;

public class ObserverThread implements Observer {

    Thread[] threads;

    public Thread[] getThreads() {
        return threads;
    }

    public void setThreads(Thread[] threads) {
        this.threads = threads;
    }

    public void update(Observable o, Object arg) {
        ExtractorThread extractorThread = (ExtractorThread)o;
        ObservableArgs observableArgs = (ObservableArgs)arg;

        if (observableArgs.isPasswordFound()) {
            System.out.println(extractorThread.getThreadName() + " PASSWORD FOUND");
            this.stopThreads();
        }
    }

    private void stopThreads() {
        for (Thread thread : this.getThreads()) {
            thread.interrupt();
        }
    }
}
