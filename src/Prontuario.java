// prontuario do paciente - agrupa o historico de atendimentos dele
// cada paciente tem um prontuario com todos os registros clinicos
public class Prontuario {

    public String cpfPaciente;
    public Atendimento[] atendimentos;  // lista de atendimentos do paciente
    public int totalAtendimentos;
    public String observacoesGerais;    // anotacoes gerais que ficam no prontuario

    // inicia prontuario vazio pra um paciente
    public Prontuario(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
        this.atendimentos = new Atendimento[50]; // historico de ate 50 atendimentos
        this.totalAtendimentos = 0;
        this.observacoesGerais = "";
    }

    // cria prontuario ja com uma observacao geral (ex: alergia, condicao cronica)
    public Prontuario(String cpfPaciente, String observacoesGerais) {
        this.cpfPaciente = cpfPaciente;
        this.atendimentos = new Atendimento[50];
        this.totalAtendimentos = 0;
        this.observacoesGerais = observacoesGerais;
    }

    // adiciona um atendimento ao historico
    public void adicionarAtendimento(Atendimento atendimento) {
        if (totalAtendimentos < 50) {
            atendimentos[totalAtendimentos] = atendimento;
            totalAtendimentos++;
        } else {
            // se lotou, avisa - idealmente usaria lista dinamica mas seguindo o padrao do projeto
            System.out.println("Prontuario cheio! Nao foi possivel adicionar o atendimento.");
        }
    }

    // atualiza as observacoes gerais (ex: descobriu uma alergia nova)
    public void atualizarObservacoes(String novasObservacoes) {
        this.observacoesGerais = novasObservacoes;
    }

    // retorna o ultimo atendimento registrado
    public Atendimento getUltimoAtendimento() {
        if (totalAtendimentos == 0) return null;
        return atendimentos[totalAtendimentos - 1];
    }

    // busca atendimentos pelo indice da consulta (pra linkar com a Consulta)
    public Atendimento buscarPorConsulta(int indiceConsulta) {
        for (int i = 0; i < totalAtendimentos; i++) {
            if (atendimentos[i].indiceConsulta == indiceConsulta) {
                return atendimentos[i];
            }
        }
        return null; // se nao achou, retorna null
    }

    // mostra o historico completo do paciente
    public String exibirResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("=== PRONTUARIO - CPF: ").append(cpfPaciente).append(" ===\n");

        if (!observacoesGerais.equals("")) {
            resumo.append("Obs. Gerais: ").append(observacoesGerais).append("\n");
        }

        resumo.append("Total de atendimentos: ").append(totalAtendimentos).append("\n");

        for (int i = 0; i < totalAtendimentos; i++) {
            resumo.append("\n[Atendimento ").append(i + 1).append("]\n");
            resumo.append(atendimentos[i].exibirResumo());
            resumo.append("\n");
        }

        return resumo.toString();
    }
}
