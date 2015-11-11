import java.io.*;

public class Extractor {

    private String compressedFilePath;
    private String passwordsFilePath;
    private String message = "";

    public String getCompressedFilePath() {
        return compressedFilePath;
    }

    public void setCompressedFilePath(String compressedFilePath) {
        this.compressedFilePath = compressedFilePath;
    }

    public String getPasswordsFilePath() {
        return passwordsFilePath;
    }

    public void setPasswordsFilePath(String passwordsFilePath) {
        this.passwordsFilePath = passwordsFilePath;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = this.prepareMessage(message);
    }

    public void execute() {

        try {
            int totalLines = this.countLines(this.getPasswordsFilePath());
            int numThreads = 3;
            int lineStart, lineEnd, con, start = 0;
            int linesPerThread;

            ExtractorThread[] extractorThreads = new ExtractorThread[numThreads];
            Thread[] threads = new Thread[numThreads];
            ObserverThread observerThread = new ObserverThread();
            ObserverProgress observerProgress = new ObserverProgress();

            linesPerThread = Math.round(totalLines/numThreads);

            System.out.println("Total Threads: " + numThreads + " -- Total Lines: " + totalLines + " -- Lines/Thread: " + linesPerThread);

            for (con = 0; con < numThreads; con++) {

                lineStart = start;
                lineEnd = lineStart + linesPerThread;

                ExtractorThread extractorThreadCurrent = new ExtractorThread();
                extractorThreads[con] = extractorThreadCurrent;

                extractorThreads[con].setLineStart(lineStart);
                extractorThreads[con].setLineEnd(lineEnd);

                RandomAccessFile seekerCurrent =  new RandomAccessFile(this.getPasswordsFilePath(), "r");
                extractorThreads[con].setSeeker(seekerCurrent);
                extractorThreads[con].setThreadName("Thread " + con);
                extractorThreads[con].setCompressedFilePath(this.getCompressedFilePath());
                extractorThreads[con].addObserver(observerThread);
                extractorThreads[con].addObserver(observerProgress);

                Thread threadCurrent = new Thread(extractorThreads[con]);
                threads[con] = threadCurrent;

                System.out.println(extractorThreads[con].getThreadName() + " From:" + extractorThreads[con].getLineStart() + " To:" + extractorThreads[con].getLineEnd());

                start += linesPerThread;
            }

            observerThread.setThreads(threads);

            for (con = 0; con < numThreads; con++) {
                threads[con].start();
            }

            ProgressThread progressThread = new ProgressThread();
            progressThread.setObserverProgress(observerProgress);
            progressThread.setObserverThread(observerThread);
            progressThread.setTotalLines(totalLines);

            Thread threadProgressOutput = new Thread(progressThread);
            threadProgressOutput.run();

        } catch (IOException e) {
            this.setMessage("ERROR: " + e.getMessage());
        }
    }

    private String prepareMessage(String message) {
        StringBuilder content = new StringBuilder();

        content.append("\n########################");
        content.append("\n--");
        content.append("\n--> ");
        content.append(message);

        return content.toString();
    }

    private int countLines(String filename) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
        int cnt = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {}

        cnt = reader.getLineNumber();
        reader.close();

        return cnt;
    }
}


