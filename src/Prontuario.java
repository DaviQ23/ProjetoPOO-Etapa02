import java.util.ArrayList;
import java.util.List;

// COMPOSIÇÃO: Prontuario só existe dentro de Atendimento.
// O construtor é package-private (sem modificador) para que apenas
// classes do mesmo pacote (Atendimento) possam instanciar Prontuario.
// Se o Atendimento for removido, o Prontuario também deixa de existir.
class Prontuario {

    private String observacoes;
    private String diagnostico;
    private List<String> procedimentos; // R10: ArrayList no lugar de array fixo
    private String dataRegistro;

    // Construtor package-private: só Atendimento pode criar um Prontuario
    Prontuario(String observacoes, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = "";
        this.dataRegistro = dataRegistro;
        this.procedimentos = new ArrayList<>();
    }

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes
    Prontuario(String observacoes, String diagnostico, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.dataRegistro = dataRegistro;
        this.procedimentos = new ArrayList<>();
    }

    // Getters e setters — encapsulamento (R1)
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        if (observacoes == null || observacoes.trim().isEmpty()) {
            throw new IllegalArgumentException("Observacoes nao podem ser vazias.");
        }
        this.observacoes = observacoes;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        if (diagnostico == null) {
            this.diagnostico = "";
        } else {
            this.diagnostico = diagnostico;
        }
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        if (dataRegistro == null || dataRegistro.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de registro nao pode ser vazia.");
        }
        this.dataRegistro = dataRegistro;
    }

    // Retorna cópia da lista para proteger o estado interno
    public List<String> getProcedimentos() {
        return new ArrayList<>(procedimentos);
    }

    // Adiciona um procedimento por vez
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void adicionarProcedimento(String procedimento) {
        if (procedimento == null || procedimento.trim().isEmpty()) {
            throw new IllegalArgumentException("Procedimento nao pode ser vazio.");
        }
        procedimentos.add(procedimento);
    }

    // Adiciona uma lista de procedimentos de uma vez
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void adicionarProcedimento(List<String> lista) {
        for (String p : lista) {
            if (p != null && !p.trim().isEmpty()) {
                procedimentos.add(p);
            }
        }
    }

    public String exibirResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("  [Prontuario - ").append(dataRegistro).append("]\n");
        sb.append("  Observacoes: ").append(observacoes);

        if (!diagnostico.isEmpty()) {
            sb.append("\n  Diagnostico: ").append(diagnostico);
        }

        if (!procedimentos.isEmpty()) {
            sb.append("\n  Procedimentos: ");
            for (int i = 0; i < procedimentos.size(); i++) {
                sb.append(procedimentos.get(i));
                if (i < procedimentos.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }
}
