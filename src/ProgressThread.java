public class ProgressThread implements Runnable{

    ObserverProgress observerProgress;
    ObserverThread observerThread;
    int TotalLines;

    public ObserverProgress getObserverProgress() {
        return observerProgress;
    }

    public void setObserverProgress(ObserverProgress observerProgress) {
        this.observerProgress = observerProgress;
    }

    public ObserverThread getObserverThread() {
        return observerThread;
    }

    public void setObserverThread(ObserverThread observerThread) {
        this.observerThread = observerThread;
    }

    public int getTotalLines() {
        return TotalLines;
    }

    public void setTotalLines(int totalLines) {
        TotalLines = totalLines;
    }

    public void run() {

        boolean showProgress = true;
        boolean eof;
        boolean passFound;

        try {
            while (showProgress)  {
                eof = this.getObserverProgress().getTotalTriedLines() >= this.getTotalLines();
                passFound = this.getObserverThread().isPassFound();

                if (eof == true) {
                    showProgress = false;
                    break;
                }

                if (passFound == true) {
                    showProgress = false;
                    break;
                }

                this.showMessage(this.getObserverProgress().getTotalTriedLines());

                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(int triedLines) {

        int percentage = 0;

        if (triedLines > 0) {
            percentage = Math.round((triedLines * 100) / this.getTotalLines()) + 1;
        }

        System.out.println("Progress: " + percentage + "%");
    }
}
