// representa um convenio medico - antes o paciente so guardava o nome do convenio
// agora tem uma classe propria com mais informacoes
public class Convenio {

    public String nome;
    public String codigo;           // codigo identificador do convenio
    public double percentualCobertura; // quanto % o convenio paga (ex: 80.0 = 80%)
    public boolean ativo;

    // construtor minimo - so nome e codigo
    public Convenio(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.percentualCobertura = 0;
        this.ativo = true;
    }

    // construtor com percentual de cobertura ja definido
    public Convenio(String nome, String codigo, double percentualCobertura) {
        this.nome = nome;
        this.codigo = codigo;
        this.percentualCobertura = percentualCobertura;
        this.ativo = true;
    }

    // construtor completo (com status ativo/inativo)
    public Convenio(String nome, String codigo, double percentualCobertura, boolean ativo) {
        this.nome = nome;
        this.codigo = codigo;
        this.percentualCobertura = percentualCobertura;
        this.ativo = ativo;
    }

    // calcula quanto o convenio cobre de um valor
    public double calcularCobertura(double valorTotal) {
        return valorTotal * percentualCobertura / 100;
    }

    // quanto o paciente ainda paga depois do convenio
    public double calcularValorPaciente(double valorTotal) {
        double cobertura = calcularCobertura(valorTotal);
        double restante = valorTotal - cobertura;
        if (restante < 0) restante = 0;
        return restante;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    // atualiza o percentual de cobertura
    public void atualizarCobertura(double novoPercentual) {
        this.percentualCobertura = novoPercentual;
    }

    public String exibirResumo() {
        String statusAtivo = ativo ? "Ativo" : "Inativo";
        return "Convenio: " + nome + " | Codigo: " + codigo
                + " | Cobertura: " + percentualCobertura + "% | Status: " + statusAtivo;
    }
}
