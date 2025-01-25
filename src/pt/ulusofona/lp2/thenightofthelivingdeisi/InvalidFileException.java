package pt.ulusofona.lp2.thenightofthelivingdeisi;

public class InvalidFileException extends Exception {

    private int lineNumber;

    public InvalidFileException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public InvalidFileException(String message) {
        super(message);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    //Retorna a linha onde ocorreu o erro no ficheiro a dizer:
    // "atencao ficheiro tem um erro na linha x"
    public int getLineWithError() {
        return lineNumber;
    }
}
