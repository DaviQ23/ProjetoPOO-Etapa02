// Pagamento em cartão: permite parcelamento em até 6x.
// Acima de 3 parcelas, aplica taxa de 2,5% por parcela extra.
public class PagamentoCartao extends Pagamento {

    private static final int MAX_PARCELAS = 6;
    private static final double TAXA_POR_PARCELA_EXTRA = 2.5;

    // SOBRECARGA: construtor sem parcelas (1x no cartão)
    public PagamentoCartao(int indiceConsulta, double valorBase) {
        super(indiceConsulta, valorBase, 1);
    }

    // SOBRECARGA: construtor com parcelas
    public PagamentoCartao(int indiceConsulta, double valorBase, int parcelas) {
        super(indiceConsulta, valorBase);
        if (parcelas < 1 || parcelas > MAX_PARCELAS) {
            throw new IllegalArgumentException(
                "Parcelas invalidas. Permitido entre 1 e " + MAX_PARCELAS + "x."
            );
        }
        setParcelas(parcelas);
    }

    // SOBRESCRITA: calcula valor com taxa por parcela extra acima de 3x
    // LIGAÇÃO DINÂMICA: o método executado depende do tipo REAL do objeto, não do tipo da referência
    @Override
    public double calcularValorFinal() {
        double valor = getValorBase();
        int parcelas = getParcelas();

        if (parcelas > 3) {
            int parcelasExtras = parcelas - 3;
            double taxa = parcelasExtras * TAXA_POR_PARCELA_EXTRA / 100;
            valor = valor + (valor * taxa);
        }

        return valor;
    }

    // SOBRESCRITA: exibe resumo específico de cartão
    @Override
    public String exibirResumo() {
        String resumo = "[Cartao] " + super.exibirResumo();
        if (getParcelas() > 3) {
            int extras = getParcelas() - 3;
            resumo = resumo + " | Taxa: " + (extras * TAXA_POR_PARCELA_EXTRA) + "%";
        }
        return resumo;
    }

    // SOBRESCRITA: exporta dados específicos de cartão
    @Override
    public String exportarDados() {
        return super.exportarDados() + ";cartao;" + getParcelas() + "x";
    }
}
