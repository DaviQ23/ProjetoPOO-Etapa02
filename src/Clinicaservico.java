import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Classe responsável por concentrar todas as regras de negócio da clínica.
// A Main cuida apenas de menus, entrada de dados e exibição de resultados.
public class ClinicaServico {

    // R10 — Coleções com justificativa:
    // ArrayList<Consulta>: ordem de inserção importa; acesso por índice necessário
    private List<Consulta> consultas;

    // ArrayList<Atendimento>: ordem de inserção importa; acesso por índice necessário
    private List<Atendimento> atendimentos;

    // ArrayList<Pagamento>: ordem de inserção importa; polimorfismo em List<Pagamento>
    private List<Pagamento> pagamentos;

    // ArrayList<Pessoa>: lista unificada para relatório com ligação dinâmica
    private List<Pessoa> pessoas;

    // HashMap<String, Paciente>: busca por chave (CPF); mais eficiente que percorrer lista
    private Map<String, Paciente> pacientesPorCpf;

    // HashMap<String, Profissional>: busca por chave (nome); mais eficiente que percorrer lista
    private Map<String, Profissional> profissionaisPorNome;

    // HashSet<String>: apenas verificação de existência (contains); não precisa de ordem
    private Set<String> cpfsCadastrados;

    public ClinicaServico() {
        this.consultas = new ArrayList<>();
        this.atendimentos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.pessoas = new ArrayList<>();
        this.pacientesPorCpf = new HashMap<>();
        this.profissionaisPorNome = new HashMap<>();
        this.cpfsCadastrados = new HashSet<>();
    }

    // =========================================================
    // PACIENTES
    // =========================================================

    // Cadastro mínimo de paciente
    public void cadastrarPaciente(String nome, String cpf) throws OperacaoInvalidaException {
        // HashSet: tentativa de adicionar duplicata retorna false
        if (!cpfsCadastrados.add(cpf)) {
            throw new OperacaoInvalidaException("CPF ja cadastrado: " + cpf);
        }
        Paciente paciente = new Paciente(nome, cpf);
        pacientesPorCpf.put(cpf, paciente);
        pessoas.add(paciente);
        System.out.println("Paciente cadastrado com sucesso: " + nome);
    }

    // Cadastro completo de paciente
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void cadastrarPaciente(String nome, String cpf, int idade, String telefone, String convenioNome)
            throws OperacaoInvalidaException {
        if (!cpfsCadastrados.add(cpf)) {
            throw new OperacaoInvalidaException("CPF ja cadastrado: " + cpf);
        }
        Paciente paciente = new Paciente(nome, cpf, idade, telefone, convenioNome);
        pacientesPorCpf.put(cpf, paciente);
        pessoas.add(paciente);
        System.out.println("Paciente cadastrado com sucesso: " + nome);
    }

    public void complementarPaciente(String cpf, int idade, String telefone)
            throws PacienteNaoEncontradoException {
        Paciente paciente = buscarPaciente(cpf);
        paciente.complementar(idade, telefone);
        System.out.println("Cadastro complementado com sucesso.");
    }

    // SOBRECARGA: complementar com convenio
    public void complementarPaciente(String cpf, int idade, String telefone, String convenioNome)
            throws PacienteNaoEncontradoException {
        Paciente paciente = buscarPaciente(cpf);
        paciente.complementar(idade, telefone, convenioNome);
        System.out.println("Cadastro complementado com sucesso.");
    }

    public void desativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente paciente = buscarPaciente(cpf);
        paciente.desativar();
        System.out.println("Paciente desativado: " + paciente.getNome());
    }

    public void ativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente paciente = buscarPaciente(cpf);
        paciente.ativar();
        System.out.println("Paciente reativado: " + paciente.getNome());
    }

    // Busca direta por CPF no HashMap — R10
    public Paciente buscarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente paciente = pacientesPorCpf.get(cpf);
        if (paciente == null) {
            throw new PacienteNaoEncontradoException("Paciente nao encontrado para o CPF: " + cpf);
        }
        return paciente;
    }

    // =========================================================
    // PROFISSIONAIS
    // =========================================================

    public void cadastrarProfissional(String nome, String especialidade)
            throws OperacaoInvalidaException {
        if (profissionaisPorNome.containsKey(nome)) {
            throw new OperacaoInvalidaException("Profissional ja cadastrado: " + nome);
        }
        Profissional profesional = new Profissional(nome, especialidade);
        profissionaisPorNome.put(nome, profesional);
        pessoas.add(profesional);
        System.out.println("Profissional cadastrado com sucesso: " + nome);
    }

    public void atualizarProfissional(String nome, String registro, double valor)
            throws ProfissionalNaoEncontradoException {
        Profissional profesional = buscarProfissional(nome);
        profesional.atualizar(registro, valor);
        System.out.println("Profissional atualizado: " + nome);
    }

    // SOBRECARGA: atualizar com dias de atendimento
    public void atualizarProfissional(String nome, String registro, double valor, String[] dias, int totalDias)
            throws ProfissionalNaoEncontradoException {
        Profissional profesional = buscarProfissional(nome);
        profesional.atualizar(registro, valor, dias, totalDias);
        System.out.println("Profissional atualizado: " + nome);
    }

    // Busca direta por nome no HashMap — R10
    public Profissional buscarProfissional(String nome) throws ProfissionalNaoEncontradoException {
        Profissional profesional = profissionaisPorNome.get(nome);
        if (profesional == null) {
            throw new ProfissionalNaoEncontradoException("Profissional nao encontrado: " + nome);
        }
        return profesional;
    }

    // =========================================================
    // CONSULTAS
    // =========================================================

    public void agendarConsulta(String cpfPaciente, String nomeProfissional, String data, String horario)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {

        Paciente paciente = buscarPaciente(cpfPaciente);

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException("Paciente inativo. Nao e possivel agendar consulta: "
                    + paciente.getNome());
        }

        Profissional profesional = buscarProfissional(nomeProfissional);

        // Verifica se o profissional atende no dia informado
        String diaSemana = extrairDiaSemana(data);
        if (!profesional.atendeNoDia(diaSemana)) {
            throw new HorarioIndisponivelException("Profissional nao atende no dia: " + data
                    + ". Dias disponiveis: " + diasDisponiveisFormatados(profesional));
        }

        // Verifica conflito de horário
        if (horarioOcupado(nomeProfissional, data, horario)) {
            String alternativa = sugerirHorarioAlternativo(nomeProfissional, data);
            throw new HorarioIndisponivelException("Horario ja ocupado: " + horario
                    + ". Sugestao de horario alternativo: " + alternativa);
        }

        // Etapa 7 — Passando referências de objetos Paciente e Profissional para o construtor refatorado
        Consulta consulta = new Consulta(paciente, profesional, data, horario);
        consultas.add(consulta);
        System.out.println("Consulta agendada com sucesso!");
        System.out.println(consulta.exibirResumo());
    }

    // SOBRECARGA: agendar com tipo de consulta
    public void agendarConsulta(String cpfPaciente, String nomeProfissional, String data, String horario, String tipo)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {

        Paciente paciente = buscarPaciente(cpfPaciente);

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException("Paciente inativo. Nao e possivel agendar consulta: "
                    + paciente.getNome());
        }

        Profissional profesional = buscarProfissional(nomeProfissional);

        if (horarioOcupado(nomeProfissional, data, horario)) {
            String alternativa = sugerirHorarioAlternativo(nomeProfissional, data);
            throw new HorarioIndisponivelException("Horario ja ocupado: " + horario
                    + ". Sugestao: " + alternativa);
        }

        // Etapa 7 — Passando referências de objetos Paciente e Profissional para o construtor refatorado
        Consulta consulta = new Consulta(paciente, profesional, data, horario, tipo);
        consultas.add(consulta);
        System.out.println("Consulta agendada com sucesso!");
        System.out.println(consulta.exibirResumo());
    }

    public void cancelarConsulta(int indice, String motivo)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta consulta = buscarConsulta(indice);

        if (consulta.status.equals("realizada")) {
            throw new OperacaoInvalidaException("Nao e possivel cancelar uma consulta ja realizada.");
        }
        if (consulta.status.equals("cancelada")) {
            throw new OperacaoInvalidaException("Consulta ja esta cancelada.");
        }

        String msg = consulta.cancelar(motivo);
        System.out.println(msg);
    }

    public void remarcarConsulta(int indice, String novaData, String novoHorario)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        Consulta original = buscarConsulta(indice);

        if (original.status.equals("cancelada") || original.status.equals("realizada")) {
            throw new OperacaoInvalidaException("Nao e possivel remarcar uma consulta com status: "
                    + original.status);
        }

        if (horarioOcupado(original.nomeProfissional, novaData, novoHorario)) {
            throw new HorarioIndisponivelException("Novo horario ja esta ocupado: " + novoHorario);
        }

        original.remarcar();
        // Etapa 7 — Capturando as associações do objeto original para repassar ao novo objeto Consulta
        Consulta nova = new Consulta(original.paciente, original.profissional,
                novaData, novoHorario, original.tipo);
        consultas.add(nova);
        System.out.println("Consulta remarcada com sucesso!");
        System.out.println(nova.exibirResumo());
    }

    public Consulta buscarConsulta(int indice)
            throws ConsultaNaoEncontradaException {
        if (indice < 0 || indice >= consultas.size()) {
            throw new ConsultaNaoEncontradaException("Consulta nao encontrada para o indice: " + indice);
        }
        return consultas.get(indice);
    }

    // Busca por CPF + data + horário
    public Consulta buscarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        for (Consulta c : consultas) {
            if (c.cpfPaciente.equals(cpf) && c.data.equals(data) && c.getHorario().equals(horario)) {
                return c;
            }
        }
        throw new ConsultaNaoEncontradaException("Consulta nao encontrada para CPF: "
                + cpf + ", data: " + data + ", horario: " + horario);
    }

    // =========================================================
    // ATENDIMENTOS
    // =========================================================

    public Atendimento registrarAtendimento(int indiceConsulta, String observacoes, String dataRegistro)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta consulta = buscarConsulta(indiceConsulta);

        if (!consulta.status.equals("agendada")) {
            throw new OperacaoInvalidaException(
                "Nao e possivel registrar atendimento. Status da consulta: " + consulta.status);
        }

        Atendimento atendimento = new Atendimento(indiceConsulta, observacoes, dataRegistro);
        atendimentos.add(atendimento);
        consulta.realizar();
        System.out.println("Atendimento registrado com sucesso!");
        return atendimento;
    }

    // =========================================================
    // PAGAMENTOS
    // =========================================================

    public void registrarPagamentoDinheiro(int indiceConsulta, double valorBase)
            throws ConsultaNaoEncontradaException, PagamentoInvalidoException {
        validarConsultaParaPagamento(indiceConsulta);

        if (valorBase < 0) {
            throw new PagamentoInvalidoException("Valor de pagamento nao pode ser negativo.");
        }

        try {
            Pagamento pagamento = new PagamentoDinheiro(indiceConsulta, valorBase);
            pagamentos.add(pagamento);
            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());
        } null {
            // finally garante que a mensagem de log seja exibida independente de sucesso ou falha
            System.out.println("--- Operacao de pagamento finalizada ---");
        }
    }

    public void registrarPagamentoCartao(int indiceConsulta, double valorBase, int parcelas)
            throws ConsultaNaoEncontradaException, PagamentoInvalidoException {
        validarConsultaParaPagamento(indiceConsulta);

        if (valorBase < 0) {
            throw new PagamentoInvalidoException("Valor de pagamento nao pode ser negativo.");
        }
        if (parcelas < 1 || parcelas > 6) {
            throw new PagamentoInvalidoException(
                "Parcelas invalidas: " + parcelas + ". Permitido entre 1 e 6.");
        }

        try {
            Pagamento pagamento = new PagamentoCartao(indiceConsulta, valorBase, parcelas);
            pagamentos.add(pagamento);
            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());
        } null {
            System.out.println("--- Operacao de pagamento finalizada ---");
        }
    }

    public void registrarPagamentoConvenio(int indiceConsulta, double valorBase, String nomeConvenio)
            throws ConsultaNaoEncontradaException, PagamentoInvalidoException, ConvenioNaoCobreException {
        validarConsultaParaPagamento(indiceConsulta);

        if (valorBase < 0) {
            throw new PagamentoInvalidoException("Valor de pagamento nao pode ser negativo.");
        }

        // Verifica se o convênio é reconhecido
        if (!convenioReconhecido(nomeConvenio)) {
            throw new PagamentoInvalidoException("Convenio nao reconhecido: " + nomeConvenio
                    + ". Convenios validos: SaudePlus, VidaMais, BemEstar.");
        }

        try {
            Pagamento pagamento = new PagamentoConvenio(indiceConsulta, valorBase, nomeConvenio);
            pagamentos.add(pagamento);
            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());
        } null {
            System.out.println("--- Operacao de pagamento finalizada ---");
        }
    }

    // =========================================================
    // RELATÓRIOS
    // =========================================================

    // Relatório unificado — percorre List<Pessoa> com ligação dinâmica
    // LIGAÇÃO DINÂMICA: o método chamado depende do tipo REAL do objeto, não do tipo da referência
    public void exibirRelatorioUnificado() {
        System.out.println("\n=== RELATORIO UNIFICADO DE CADASTROS ===");

        int totalPacientes = 0;
        int totalProfissionais = 0;

        for (Pessoa p : pessoas) {
            System.out.println(p.exibirResumo()); // ligação dinâmica aqui

            // Dynamic casting: identifica o tipo real para exibir informações extras
            if (p instanceof Paciente) {
                totalPacientes++;
            } else if (p instanceof Profissional) {
                totalProfissionais++;
            }

            System.out.println("---");
        }

        System.out.println("Total de pacientes: " + totalPacientes);
        System.out.println("Total de profissionais: " + totalProfissionais);
    }

    public void exibirRelatorioConsultas() {
        System.out.println("\n=== RELATORIO GERAL DE CONSULTAS ===");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
            System.out.println("---");
        }
    }

    // Filtra por profissional
    public void exibirRelatorioConsultas(String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (Consulta c : consultas) {
            if (c.nomeProfissional.equals(nomeProfissional)) {
                System.out.println(c.exibirResumo());
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhuma consulta encontrada para esse profissional.");
        }
    }

    public void exibirResumoFinanceiro() {
        int realizadas = 0;
        int canceladas = 0;
        double totalFaturado = 0;

        for (Consulta c : consultas) {
            if (c.status.equals("realizada")) realizadas++;
            if (c.status.equals("cancelada")) canceladas++;
        }

        // LIGAÇÃO DINÂMICA: calcularValorFinal() chama o método do tipo real de cada pagamento
        for (Pagamento p : pagamentos) {
            totalFaturado += p.calcularValorFinal();
        }

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Cancelamentos: " + canceladas);
        System.out.println("Total faturado: R$" + Math.round(totalFaturado * 100.0) / 100.0);
    }

    // =========================================================
    // MÉTODOS AUXILIARES PRIVADOS
    // =========================================================

    private boolean horarioOcupado(String nomeProfissional, String data, String horario) {
        for (Consulta c : consultas) {
            if (c.nomeProfissional.equals(nomeProfissional)
                    && c.data.equals(data)
                    && c.getHorario().equals(horario)
                    && !c.status.equals("cancelada")
                    && !c.status.equals("remarcada")) {
                return true;
            }
        }
        return false;
    }

    private String sugerirHorarioAlternativo(String nomeProfissional, String data) {
        String[] horarios = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"};
        for (String h : horarios) {
            if (!horarioOcupado(nomeProfissional, data, h)) {
                return h;
            }
        }
        return "Nenhum horario disponivel neste dia.";
    }

    private String extrairDiaSemana(String data) {
        // Simplificado: retorna a data para comparação com diasDisponiveis
        // Em uma implementação real usaria LocalDate
        return data;
    }

    private String diasDisponiveisFormatados(Profissional profesional) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < profesional.getTotalDias(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(profesional.getDiaDisponivel(i));
        }
        return sb.toString();
    }

    private boolean convenioReconhecido(String nomeConvenio) {
        return nomeConvenio != null && (
            nomeConvenio.equalsIgnoreCase("SaudePlus") ||
            nomeConvenio.equalsIgnoreCase("VidaMais") ||
            nomeConvenio.equalsIgnoreCase("BemEstar")
        );
    }

    private void validarConsultaParaPagamento(int indiceConsulta)
            throws ConsultaNaoEncontradaException, PagamentoInvalidoException {
        Consulta consulta = buscarConsulta(indiceConsulta);
        if (!consulta.status.equals("realizada")) {
            throw new PagamentoInvalidoException(
                "Pagamento so pode ser registrado para consultas realizadas. Status atual: "
                + consulta.status);
        }
    }

    // Getters das listas para uso na Main
    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}