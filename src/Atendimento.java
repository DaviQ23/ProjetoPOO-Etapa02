// COMPOSIÇÃO: Atendimento contém um Prontuario.
// O Prontuario é criado exclusivamente dentro do Atendimento (construtor package-private),
// e não existe sem ele. Se o Atendimento for removido, o Prontuario também é.
public class Atendimento implements Exportavel {

    private int indiceConsulta;
    private Prontuario prontuario; // COMPOSIÇÃO com Prontuario
    private boolean concluido;

    // Registro básico — só observações e data
    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (resolvido em tempo de compilação)
    public Atendimento(int indiceConsulta, String observacoes, String dataRegistro) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, dataRegistro); // cria o Prontuario internamente
        this.concluido = false;
    }

    // Registro com diagnóstico já definido
    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (resolvido em tempo de compilação)
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico, String dataRegistro) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico, dataRegistro);
        this.concluido = false;
    }

    // Getters — encapsulamento (R1): atributos privados, acesso apenas por métodos
    public int getIndiceConsulta() {
        return indiceConsulta;
    }

    public boolean isConcluido() {
        return concluido;
    }

    // Expõe o prontuario para leitura (mas o objeto em si só é criado aqui dentro)
    public Prontuario getProntuario() {
        return prontuario;
    }

    // Métodos que delegam ao Prontuario — mantém a interface familiar para quem usava Atendimento antes

    public String getObservacoes() {
        return prontuario.getObservacoes();
    }

    public void setObservacoes(String observacoes) {
        prontuario.setObservacoes(observacoes);
    }

    public String getDiagnostico() {
        return prontuario.getDiagnostico();
    }

    public void setDiagnostico(String diagnostico) {
        prontuario.setDiagnostico(diagnostico);
    }

    // Adiciona um procedimento por vez
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void adicionarProcedimento(String procedimento) {
        prontuario.adicionarProcedimento(procedimento);
    }

    // Adiciona vários procedimentos de uma lista
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void adicionarProcedimento(java.util.List<String> lista) {
        prontuario.adicionarProcedimento(lista);
    }

    public void concluir() {
        this.concluido = true;
    }

    public String exibirResumo() {
        String status = concluido ? "Concluido" : "Em andamento";
        return "Atendimento #" + indiceConsulta + " | Status: " + status
                + "\n" + prontuario.exibirResumo();
    }

    // SOBRESCRITA: implementação do contrato da interface Exportavel
    // LIGAÇÃO DINÂMICA: o método executado depende do tipo real do objeto em tempo de execução
    @Override
    public String exportarDados() {
        return "ATENDIMENTO;"
                + indiceConsulta + ";"
                + prontuario.getDataRegistro() + ";"
                + prontuario.getObservacoes() + ";"
                + prontuario.getDiagnostico() + ";"
                + prontuario.getProcedimentos().toString() + ";"
                + (concluido ? "concluido" : "em_andamento");
    }
}