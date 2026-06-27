public class HorarioIndisponivelException extends Exception {

    // SOBRECARGA: construtor só com mensagem
    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }

    // SOBRECARGA: construtor com mensagem e causa
    public HorarioIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
