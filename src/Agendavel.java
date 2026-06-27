// interface que define o contrato basico de qualquer coisa que pode ser agendada
// a Consulta vai implementar isso
public interface Agendavel {

    // confirma o agendamento (muda status pra agendada, etc)
    void agendar();

    // cancela - pode receber motivo ou nao, por isso tem duas versoes
    void cancelar();
    String cancelar(String motivo);

    // remarca pra outra data/hora
    void remarcar();

    // retorna o status atual (agendada, cancelada, realizada...)
    String getStatus();
}
