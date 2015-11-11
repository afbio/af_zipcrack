public class ObservableArgs {

    private boolean finished;
    private boolean passwordFound;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isPasswordFound() {
        return passwordFound;
    }

    public void setPasswordFound(boolean passwordFound) {
        this.passwordFound = passwordFound;
    }

    public ObservableArgs() {
        this.setFinished(false);
        this.setPasswordFound(false);
    }
}
