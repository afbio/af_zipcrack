import java.util.Observable;
import java.util.Observer;

public class ObserverThread implements Observer {

    public void update(Observable o, Object arg) {
        ExtractorThread extractorThread = (ExtractorThread)o;
        ObservableArgs observableArgs = (ObservableArgs)arg;

        if (observableArgs.isPasswordFound()) {
            System.out.println(extractorThread.getThreadName() + " PASSWORD FOUND");
        }
    }
}
