import java.util.Map;
import java.util.Observable;

public class ObservableProgressArgs extends Thread {

    private int triedLines;

    public int getTriedLines() {
        return triedLines;
    }

    public void addTriedLines() {
        this.triedLines += 1;
    }

    public ObservableProgressArgs() {

    }
}
