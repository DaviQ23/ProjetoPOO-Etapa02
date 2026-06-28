public class PacienteInativoException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public PacienteInativoException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public PacienteInativoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
