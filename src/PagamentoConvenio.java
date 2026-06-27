// Pagamento por convênio: aplica cobertura percentual conforme o convênio do paciente.
// SaudePlus = 40%, VidaMais = 30%, BemEstar = 50%
public class PagamentoConvenio extends Pagamento {

    private String nomeConvenio;
    private double percentualCobertura;

    // SOBRECARGA: construtor passando o nome do convênio (busca o percentual automaticamente)
    public PagamentoConvenio(int indiceConsulta, double valorBase, String nomeConvenio) {
        super(indiceConsulta, valorBase);
        this.nomeConvenio = nomeConvenio;
        this.percentualCobertura = buscarPercentual(nomeConvenio);
    }

    // SOBRECARGA: construtor passando nome e percentual manualmente
    public PagamentoConvenio(int indiceConsulta, double valorBase, String nomeConvenio, double percentualCobertura) {
        super(indiceConsulta, valorBase);
        this.nomeConvenio = nomeConvenio;
        setPercentualCobertura(percentualCobertura);
    }

    // Método privado auxiliar — busca o percentual pelo nome do convênio
    private double buscarPercentual(String nomeConvenio) {
        if (nomeConvenio == null) return 0;
        if (nomeConvenio.equalsIgnoreCase("SaudePlus")) return 40.0;
        if (nomeConvenio.equalsIgnoreCase("VidaMais"))  return 30.0;
        if (nomeConvenio.equalsIgnoreCase("BemEstar"))  return 50.0;
        throw new IllegalArgumentException("Convenio nao reconhecido: " + nomeConvenio);
    }

    // SOBRESCRITA: calcula valor aplicando a cobertura do convênio
    // LIGAÇÃO DINÂMICA: o método executado depende do tipo REAL do objeto, não do tipo da referência
    @Override
    public double calcularValorFinal() {
        double cobertura = getValorBase() * percentualCobertura / 100;
        return getValorBase() - cobertura;
    }

    // SOBRESCRITA: exibe resumo específico de convênio
    @Override
    public String exibirResumo() {
        return "[Convenio: " + nomeConvenio + "] " + super.exibirResumo()
                + " | Cobertura: " + percentualCobertura + "%";
    }

    // SOBRESCRITA: exporta dados específicos de convênio
    @Override
    public String exportarDados() {
        return super.exportarDados() + ";convenio;" + nomeConvenio + ";" + percentualCobertura + "%";
    }

    // Getters e setters
    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public double getPercentualCobertura() {
        return percentualCobertura;
    }

    public void setPercentualCobertura(double percentualCobertura) {
        if (percentualCobertura < 0 || percentualCobertura > 100) {
            throw new IllegalArgumentException("Percentual de cobertura deve estar entre 0 e 100.");
        }
        this.percentualCobertura = percentualCobertura;
    }
}
