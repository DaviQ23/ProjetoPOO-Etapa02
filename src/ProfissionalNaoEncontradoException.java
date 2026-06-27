public class ProfissionalNaoEncontradoException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public ProfissionalNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
