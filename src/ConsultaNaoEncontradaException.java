public class ConsultaNaoEncontradaException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public ConsultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public ConsultaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
