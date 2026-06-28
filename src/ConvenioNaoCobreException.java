public class ConvenioNaoCobreException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public ConvenioNaoCobreException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public ConvenioNaoCobreException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
