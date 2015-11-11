import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Observable;

public class ExtractorThread extends Observable implements Runnable {

    String threadName;
    RandomAccessFile seeker;
    String CompressedFilePath;
    int lineStart;
    int lineEnd;

    public RandomAccessFile getSeeker() {
        return seeker;
    }

    public void setSeeker(RandomAccessFile seeker) {
        this.seeker = seeker;
    }

    public String getCompressedFilePath() {
        return CompressedFilePath;
    }

    public void setCompressedFilePath(String compressedFilePath) {
        CompressedFilePath = compressedFilePath;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public int getLineStart() {
        return lineStart;
    }

    public void setLineStart(int lineStart) {
        this.lineStart = lineStart;
    }

    public int getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public void run() {

        String s, line;
        Boolean extracted = false;
        ObservableArgs ObserverArgs = new ObservableArgs();

        try {
            Runtime rt = Runtime.getRuntime();
            int lineNumber = 0;

            while((s = this.getSeeker().readLine()) != null) {

                lineNumber++;

                if (lineNumber <= this.getLineStart()) {
                    continue;
                }

                if (lineNumber > this.getLineEnd()) {
                    break;
                }

                System.out.println(this.getThreadName() + " -- Trying: " + s);

                Process p = rt.exec("7z x -p" + s + " -oout -y " + this.getCompressedFilePath());
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

                while ((line = in.readLine()) != null) {
                    if (line.contains("Everything is Ok")) {


                        extracted = true;

                        ObserverArgs.setPasswordFound(true);
                        this.setChanged();
                        this.notifyObservers(ObserverArgs);
                    }
                }

                if (extracted) {
                    break;
                }
            }

            ObserverArgs.setFinished(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
