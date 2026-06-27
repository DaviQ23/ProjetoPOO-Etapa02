// Etapa 7 — Consulta agora implementa Agendavel e Exportavel
public class Consulta implements Agendavel, Exportavel {
    // Etapa 7 — Refatoração para conhecer as instâncias de Paciente e Profissional (Associação)
    public Paciente paciente;
    public Profissional profissional;
    public String cpfPaciente; // Mantido para compatibilidade com buscas textuais legadas
    public String nomeProfissional; // Mantido para compatibilidade com buscas textuais legadas
    public String data;
    // Etapa 5 — Substituição do atributo de horário String pela classe Horario
    public Horario horarioObj;
    public String horario; // Mantido para compatibilidade com métodos de exibição legados
    public String tipo;
    public String status;

    // sem tipo - assume inicial
    // Etapa 7 — Construtor modificado para receber os objetos associados Paciente e Profissional
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.cpfPaciente = paciente.getCpf();
        this.nomeProfissional = profissional.getNome();
        this.data = data;
        this.horarioObj = new Horario(horario); // Etapa 5
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    // Etapa 7 — Construtor modificado para receber os objetos associados Paciente e Profissional
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario, String tipo) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.cpfPaciente = paciente.getCpf();
        this.nomeProfissional = profissional.getNome();
        this.data = data;
        this.horarioObj = new Horario(horario); // Etapa 5
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    // esse aqui a gente usa na remarcacao pra poder setar o status direto
    // Etapa 7 — Construtor modificado para receber os objetos associados Paciente e Profissional
    public Consulta(Paciente paciente, Profissional profissional, String data,
                    String horario, String tipo, String status) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.cpfPaciente = paciente.getCpf();
        this.nomeProfissional = profissional.getNome();
        this.data = data;
        this.horarioObj = new Horario(horario); // Etapa 5
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    public void cancelar() {
        this.status = "cancelada";
    }

    // cancelar com motivo - retorna a msg formatada
    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    public void remarcar() {
        this.status = "remarcada";
    }

    public void realizar() {
        this.status = "realizada";
    }

    public String exibirResumo() {
        // Etapa 7 — Exibição utilizando as referências diretas de associação quando disponíveis
        String nomePac = (paciente != null) ? paciente.getNome() : "Desconhecido";
        return "Paciente: " + nomePac + " (CPF: " + cpfPaciente + ") | Prof: " + nomeProfissional
                + " | Data: " + data + " | Hora: " + horarioObj.getHora()
                + " | Tipo: " + tipo + " | Status: " + status;
    }

    // Etapa 6 — Implementação dos métodos exigidos pela interface Agendavel
    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public String getHorario() {
        return this.horarioObj.getHora();
    }

    // Etapa 6 & 7 — Implementação da interface Exportavel
    @Override
    public String exportarDados() {
        return "CONSULTA;" + cpfPaciente + ";" + nomeProfissional + ";" + data + ";" + horarioObj.getHora() + ";" + tipo + ";" + status;
    }
}