public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String telefone;
    protected String dataNascimento;

    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
        this.telefone = "";
        this.dataNascimento = "";
    }

    public Pessoa(String nome, String cpf, String telefone, String dataNascimento) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        this.dataNascimento = dataNascimento;
    }

    public abstract String exibirResumo();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF nao pode ser vazio.");
        }
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null) {
            this.telefone = "";
        } else {
            this.telefone = telefone;
        }
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        if (dataNascimento == null) {
            this.dataNascimento = "";
        } else {
            this.dataNascimento = dataNascimento;
        }
    }
}