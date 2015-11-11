import java.io.*;

public class Extractor {

    private String compressedFilePath;
    private String passwordsFilePath;
    private boolean success = false;
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

    public boolean isSuccess() {
        return success;
    }

    private void setSuccess(boolean success) {
        this.success = success;
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

                Thread threadCurrent = new Thread(extractorThreads[con]);
                threads[con] = threadCurrent;


                System.out.println(extractorThreads[con].getThreadName() + " From:" + extractorThreads[con].getLineStart() + " To:" + extractorThreads[con].getLineEnd());

                start = start + linesPerThread;
            }

            for (con = 0; con < numThreads; con++) {
                threads[con].start();
            }

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


