import java.util.*;

public class ObserverProgress implements Observer {

    private Map triedLines;
    private int totalTriedLines;

    public int getTotalTriedLines() {
        return totalTriedLines;
    }

    private void setTotalTriedLines(int totalTriedLines) {
        this.totalTriedLines = totalTriedLines;
    }

    public Map<String, Integer> getTriedLines() {
        return triedLines;
    }

    public ObserverProgress() {
        this.triedLines = new HashMap<String, Integer>();
    }

    public void setTriedLinesByThread(ExtractorThread extractorThread, ObservableProgressArgs observableProgressArgs) {
        this.getTriedLines().put(extractorThread.getThreadName(), observableProgressArgs.getTriedLines());
    }

    public void refreshTotalLines() {

        int totalTriedLines = 0;

        Iterator entries = this.getTriedLines().entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer value = (Integer)entry.getValue();
            totalTriedLines += value;
        }

        this.setTotalTriedLines(totalTriedLines);
    }

    public void update(Observable o, Object arg) {

        if ( ! (arg instanceof ObservableProgressArgs)) {
            return;
        }

        ExtractorThread extractorThread = (ExtractorThread)o;
        ObservableProgressArgs observableProgressArgs = (ObservableProgressArgs)arg;

        this.setTriedLinesByThread(extractorThread, observableProgressArgs);
        this.refreshTotalLines();
    }
}
