public class OperacaoInvalidaException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public OperacaoInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
