public class PagamentoInvalidoException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public PagamentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
