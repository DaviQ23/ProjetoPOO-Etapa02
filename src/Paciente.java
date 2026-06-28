public class Paciente extends Pessoa {
    private static final String SEM_CONVENIO = "";

    private int idade;
    private String convenioNome;
    private boolean ativo;

    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.idade = 0;
        this.convenioNome = SEM_CONVENIO;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf);
        setIdade(idade);
        setTelefone(telefone);
        this.convenioNome = SEM_CONVENIO;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        super(nome, cpf);
        setIdade(idade);
        setTelefone(telefone);
        setConvenioNome(convenioNome);
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone, String dataNascimento, String convenioNome) {
        super(nome, cpf, telefone, dataNascimento);
        setIdade(idade);
        setConvenioNome(convenioNome);
        this.ativo = true;
    }

    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

    public void complementar(int idade, String telefone, String convenioNome) {
        setIdade(idade);
        setTelefone(telefone);
        setConvenioNome(convenioNome);
    }

    public void complementar(int idade, String telefone, String dataNascimento, String convenioNome) {
        setIdade(idade);
        setTelefone(telefone);
        setDataNascimento(dataNascimento);
        setConvenioNome(convenioNome);
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean temConvenio() {
        return convenioNome != null && !convenioNome.trim().isEmpty();
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        }
        this.idade = idade;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public void setConvenioNome(String convenioNome) {
        if (convenioNome == null || convenioNome.trim().isEmpty()) {
            this.convenioNome = SEM_CONVENIO;
        } else {
            this.convenioNome = convenioNome.trim();
        }
    }

    private String obterStatusTexto() {
        return ativo ? "Sim" : "Nao";
    }

    // SOBRESCRITA: Paciente redefine o comportamento abstrato herdado de Pessoa.
    @Override
    public String exibirResumo() {
        return "Nome: " + nome
                + " | CPF: " + cpf
                + " | Idade: " + idade
                + " | Tel: " + telefone
                + " | Data nasc.: " + dataNascimento
                + " | Convenio: " + convenioNome
                + " | Ativo: " + obterStatusTexto();
    }
}