// Pagamento em dinheiro ou pix: aplica 5% de desconto sobre o valor base.
public class PagamentoDinheiro extends Pagamento {

    private static final double DESCONTO = 5.0;

    // SOBRECARGA: construtor sem parcelas (dinheiro não parcela)
    public PagamentoDinheiro(int indiceConsulta, double valorBase) {
        super(indiceConsulta, valorBase);
    }

    // SOBRECARGA: construtor com informação do tipo (dinheiro ou pix)
    public PagamentoDinheiro(int indiceConsulta, double valorBase, String tipo) {
        super(indiceConsulta, valorBase);
    }

    // SOBRESCRITA: calcula valor com 5% de desconto
    // LIGAÇÃO DINÂMICA: o método executado depende do tipo REAL do objeto, não do tipo da referência
    @Override
    public double calcularValorFinal() {
        double desconto = getValorBase() * DESCONTO / 100;
        return getValorBase() - desconto;
    }

    // SOBRESCRITA: exibe resumo específico de dinheiro
    @Override
    public String exibirResumo() {
        return "[Dinheiro/Pix] " + super.exibirResumo()
                + " | Desconto: " + DESCONTO + "%";
    }

    // SOBRESCRITA: exporta dados específicos de dinheiro
    @Override
    public String exportarDados() {
        return super.exportarDados() + ";dinheiro;" + DESCONTO + "%_desconto";
    }
}
