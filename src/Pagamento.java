// R6 — Pagamento é abstrata: não pode ser instanciada diretamente.
// Cada subclasse implementa calcularValorFinal() de forma diferente (polimorfismo).
public abstract class Pagamento implements Exportavel {

    private int indiceConsulta;
    private double valorBase;
    private int parcelas;

    // SOBRECARGA: construtor sem parcelas
    public Pagamento(int indiceConsulta, double valorBase) {
        this.indiceConsulta = indiceConsulta;
        setValorBase(valorBase);
        this.parcelas = 1;
    }

    // SOBRECARGA: construtor com parcelas
    public Pagamento(int indiceConsulta, double valorBase, int parcelas) {
        this.indiceConsulta = indiceConsulta;
        setValorBase(valorBase);
        this.parcelas = parcelas;
    }

    // Método abstrato: cada subclasse calcula o valor final de forma diferente
    // LIGAÇÃO DINÂMICA: o método executado depende do tipo REAL do objeto, não do tipo da referência
    public abstract double calcularValorFinal();

    // Método concreto compartilhado por todas as subclasses
    public String exibirResumo() {
        double valorFinal = Math.round(calcularValorFinal() * 100.0) / 100.0;
        String resumo = "Consulta #" + indiceConsulta
                + " | Valor base: R$" + valorBase
                + " | Valor final: R$" + valorFinal
                + " | Parcelas: " + parcelas;
        if (parcelas > 1) {
            double valorParcela = Math.round((calcularValorFinal() / parcelas) * 100.0) / 100.0;
            resumo = resumo + " (R$" + valorParcela + " cada)";
        }
        return resumo;
    }

    // Getters e setters — encapsulamento (R1)
    public int getIndiceConsulta() {
        return indiceConsulta;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        if (valorBase < 0) {
            throw new IllegalArgumentException("Valor base nao pode ser negativo.");
        }
        this.valorBase = valorBase;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        if (parcelas < 1) {
            throw new IllegalArgumentException("Parcelas nao pode ser menor que 1.");
        }
        this.parcelas = parcelas;
    }

    // SOBRESCRITA: implementação do contrato da interface Exportavel
    @Override
    public String exportarDados() {
        return "PAGAMENTO;"
                + indiceConsulta + ";"
                + valorBase + ";"
                + calcularValorFinal() + ";"
                + parcelas;
    }
}
