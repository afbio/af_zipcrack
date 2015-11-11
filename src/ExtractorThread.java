import java.io.BufferedReader;
import java.io.IOException;
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

        String pass, line;
        Boolean extracted = false;
        ObservableThreadArgs observerArgs = new ObservableThreadArgs();
        ObservableProgressArgs observableProgressArgs = new ObservableProgressArgs();
        BufferedReader in;

        try {
            int lineNumber = 0;

            while ((pass = this.getSeeker().readLine()) != null) {

                lineNumber++;

                if (Thread.currentThread().isInterrupted() == true) {
                    break;
                }

                if (lineNumber <= this.getLineStart()) {
                    continue;
                }

                if (lineNumber > this.getLineEnd()) {
                    break;
                }

//                System.out.println(this.getThreadName() + " -- Trying: " + pass);
                in = CommandRunner.execute(this.getCompressedFilePath(), pass);

                while ((line = in.readLine()) != null) {

                    if (line.contains("Everything is Ok")) {
                        extracted = true;
                        observerArgs.setPasswordFound(pass);
                        this.setChanged();
                        this.notifyObservers(observerArgs);
                    }
                }

                if (extracted) {
                    break;
                }

                observableProgressArgs.addTriedLines();
                this.setChanged();
                this.notifyObservers(observableProgressArgs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
