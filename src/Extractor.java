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





//            Runtime rt = Runtime.getRuntime();

//            FileReader fr = new FileReader(this.getPasswordsFilePath());
//            BufferedReader br = new BufferedReader(fr);




//            RandomAccessFile seeker = new RandomAccessFile(this.getPasswordsFilePath(), "r");
//            RandomAccessFile seeker2 = new RandomAccessFile(this.getPasswordsFilePath(), "r");
//
//            System.out.println("Tamanho: " + seeker.length());
//
//
//
//            ExtractorThread et1 = new ExtractorThread();
//            et1.setSeeker(seeker);
//            et1.setCompressedFilePath(this.getCompressedFilePath());
//            et1.setThreadName("Thread 1");
//            Thread t1 = new Thread(et1);
//            t1.start();
//
//            seeker2.seek(11000);
//
//            ExtractorThread et2 = new ExtractorThread();
//            et2.setSeeker(seeker2);
//            et2.setCompressedFilePath(this.getCompressedFilePath());
//            et2.setThreadName("Thread 2");
//            Thread t2 = new Thread(et2);
//            t2.start();





//            String s;
//            String line;
//            boolean extracted = false;

//            while((s = br.readLine()) != null) {

//            while((s = seeker.readLine()) != null) {
//
//                System.out.println("Tentando: " + s);
//
//                Process p = rt.exec("7z x -p" + s + " -oout -y " + this.getCompressedFilePath());
//                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//                while ((line = in.readLine()) != null) {
//                    if (line.contains("Everything is Ok")) {
//                        extracted = true;
//                    }
//                }
//
//                if (extracted) {
//                    break;
//                }
//            }

//            if (extracted) {
//                this.setMessage("Extraido com sucesso! \n--> Senha: " + s);
//                this.setSuccess(true);
//                return;
//            }
//
//            this.setMessage("Senha não encontrada!");
//            this.setSuccess(false);

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


