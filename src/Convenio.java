// Etapa 5 — Criar classe Convenio para representar as operadoras de saúde
public class Convenio {
    private String nome;
    private double percentualCobertura;

    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome;
        this.percentualCobertura = percentualCobertura;
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualCobertura() {
        return percentualCobertura;
    }
}