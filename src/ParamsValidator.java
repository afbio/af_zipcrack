import java.io.File;

public class ParamsValidator {

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void addMessage(String message) {
        this.message = this.message.concat("\n" + message);
    }

    public boolean isValid(String args[]) {

        if (args.length < 2) {
            this.addMessage("Número inválido de parametros");
            return false;
        }

        if (args[0].isEmpty()) {
            this.addMessage("Parâmetro de arquivo a ser extraido é inválido");
            return false;
        }

        if (args[1].isEmpty()) {
            this.addMessage("Parâmetro de arquivo a ser extraido é inválido");
            return false;
        }

        boolean validCcompressedFile = new File(args[0]).isFile();

        if(validCcompressedFile == false) {
            this.addMessage("Arquivo compactado não encontrado");
            return false;
        }

        return true;
    }
}
