public class PacienteNaoEncontradoException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public PacienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
