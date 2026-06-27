// representa um horario disponivel de um profissional num dia especifico
// antes a gente so guardava array de strings com os dias, agora tem mais controle
public class Horario {

    public String diaSemana;   // ex: "segunda", "terca", etc.
    public String horaInicio;  // formato HH:MM
    public String horaFim;     // formato HH:MM
    public boolean disponivel; // se ta livre pra agendamento ou nao

    // construtor basico - so o dia e o intervalo
    public Horario(String diaSemana, String horaInicio, String horaFim) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.disponivel = true;
    }

    // construtor com disponibilidade ja definida
    public Horario(String diaSemana, String horaInicio, String horaFim, boolean disponivel) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.disponivel = disponivel;
    }

    // verifica se um horario especifico cabe dentro do intervalo do profissional
    // ex: se o prof atende das 08:00 as 12:00, o horario 09:00 esta dentro
    public boolean contemHorario(String horario) {
        int hConsulta = converterParaMinutos(horario);
        int hInicio = converterParaMinutos(horaInicio);
        int hFim = converterParaMinutos(horaFim);
        return hConsulta >= hInicio && hConsulta < hFim;
    }

    // verifica se dois horarios colidem (pra detectar conflito)
    // supoe consultas de 1 hora de duracao
    public boolean colidecom(String horario) {
        int hConsulta = converterParaMinutos(horario);
        int hInicio = converterParaMinutos(horaInicio);
        // se o horario ta exatamente no inicio do bloco, colide
        return hConsulta == hInicio;
    }

    // converte HH:MM pra minutos totais - facilita comparacao
    private int converterParaMinutos(String horario) {
        int horas = Integer.parseInt(horario.substring(0, 2));
        int minutos = Integer.parseInt(horario.substring(3, 5));
        return horas * 60 + minutos;
    }

    public void bloquear() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public String exibirResumo() {
        String status = disponivel ? "Disponivel" : "Ocupado";
        return "Dia: " + diaSemana + " | " + horaInicio + " - " + horaFim + " | " + status;
    }
}
