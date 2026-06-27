// etapa 7: Consulta refatorada
// agora ela conhece de verdade o Paciente e o Profissional (associacao)
// e implementa Agendavel e Exportavel
public class Consulta implements Agendavel, Exportavel {

    // associacao: em vez de guardar so cpf e nome, guarda as referencias diretas
    // isso e o que caracteriza associacao em OO — um objeto "conhece" o outro
    public Paciente paciente;
    public Profissional profissional;

    public String data;
    public String horario;
    public String tipo;
    public String status;

    // sem tipo - assume inicial
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    public Consulta(Paciente paciente, Profissional profissional, String data, String horario, String tipo) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    // esse aqui a gente usa na remarcacao pra poder setar o status direto
    public Consulta(Paciente paciente, Profissional profissional, String data,
                    String horario, String tipo, String status) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    // -- getters de conveniencia --
    // a gente vai precisar do cpf e nome em varios lugares, entao mantem acesso facil
    public String getCpfPaciente() {
        return paciente.cpf;
    }

    public String getNomeProfissional() {
        return profissional.nome;
    }

    // -- implementacao de Agendavel --

    @Override
    public void agendar() {
        // so faz sentido agendar se nao ta ja agendada ou realizada
        if (this.status.equals("cancelada") || this.status.equals("remarcada")) {
            this.status = "agendada";
        }
    }

    @Override
    public void cancelar() {
        this.status = "cancelada";
    }

    // cancela com motivo - retorna a msg formatada
    @Override
    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    @Override
    public void remarcar() {
        // remarcada significa que essa versao virou historico — uma nova consulta vai ser criada
        this.status = "remarcada";
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    // metodo que existia antes, mantido pra nao quebrar o Atendimento
    public void realizar() {
        this.status = "realizada";
    }

    // -- implementacao de Exportavel --

    @Override
    public String exibirResumo() {
        // agora da pra exibir mais info porque tem o objeto inteiro do paciente
        return "Paciente: " + paciente.nome + " (CPF: " + paciente.cpf + ")"
                + " | Prof: " + profissional.nome + " (" + profissional.especialidade + ")"
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }

    @Override
    public String exportarCsv() {
        // formato: cpf,nomePaciente,nomeProfissional,especialidade,data,horario,tipo,status
        // util se precisar exportar pra planilha ou arquivo de texto
        return paciente.cpf + "," + paciente.nome + ","
                + profissional.nome + "," + profissional.especialidade + ","
                + data + "," + horario + ","
                + tipo + "," + status;
    }
}
