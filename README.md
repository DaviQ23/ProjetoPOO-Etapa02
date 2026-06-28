# 🏥 Clínica VidaPlena — Sistema de Gerenciamento de Clínica

> Projeto acadêmico desenvolvido para a disciplina de **Programação Orientada a Objetos** — aplicação dos pilares de POO em Java através da simulação de um sistema real de gerenciamento clínico.

---

## 📋 Sobre o Projeto

O **Clínica VidaPlena** é um sistema de gerenciamento de clínica médica desenvolvido inteiramente em Java, sem frameworks ou bibliotecas externas. Toda a interação ocorre via terminal (linha de comando), com menus navegáveis em modo texto.

O projeto foi construído de forma incremental ao longo de **7 etapas**, cada uma introduzindo novos conceitos de POO: encapsulamento, herança, polimorfismo, interfaces, composição, associação e uso de coleções da Java Collections Framework.

---

## 👥 Equipe

| Nome | Papel |
|---|---|
| **Valdir Cazé Silva Netto** | Desenvolvedor |
| **Davi Queiroz** | Desenvolvedor |
| **João Emanuel Nascimento** | Desenvolvedor |

---

## 🚀 Funcionalidades

### 👤 Pacientes
- Cadastro com 3 níveis de detalhe: mínimo (nome + CPF), intermediário (+ idade e telefone) e completo (+ convênio)
- Complemento de cadastro posterior
- Busca por CPF
- Listagem geral
- Desativação de paciente (soft delete)

### 🩺 Profissionais
- Cadastro com especialidade, registro profissional e valor de consulta
- Definição dos dias disponíveis para atendimento (até 7 dias)
- Especialidades suportadas: Clínica Geral, Fisioterapia, Psicologia, Nutrição
- Atualização de registro e valor

### 📅 Consultas
- Agendamento de consultas vinculando paciente e profissional por objetos (associação direta)
- Validação de disponibilidade do profissional no dia
- Cancelamento (com ou sem motivo)
- Remarcação
- Realização de consulta
- Exportação de dados em formato CSV (`CONSULTA;...`)

### 🗂️ Atendimentos
- Registro de atendimento vinculado a uma consulta
- Adição de observações, diagnóstico e procedimentos realizados
- Prontuário composto internamente em cada atendimento (composição)
- Conclusão de atendimento
- Exportação de dados

### 💳 Pagamentos
- Três modalidades:
  - **Dinheiro** — sem acréscimo
  - **Cartão** — com suporte a parcelamento e taxa configurável
  - **Convênio** — desconto baseado no percentual de cobertura do convênio
- Cálculo automático do valor final por polimorfismo
- Exportação de dados

### 📊 Relatórios
- Relatório geral de todas as consultas com diagnósticos
- Filtro por profissional
- Filtro por período (data início / data fim)
- Resumo financeiro com total arrecadado, multas e exportação de registros

---

## 🏗️ Arquitetura e Estrutura de Classes

```
src/
├── Main.java                          # Ponto de entrada e menus de navegação
├── ClinicaServico.java                # Regras de negócio (separação de responsabilidade)
│
├── Pessoa.java                        # Classe abstrata base (nome, CPF, telefone, data nasc.)
├── Paciente.java                      # Herda Pessoa; adiciona idade, convênio, status ativo
├── Profissional.java                  # Herda Pessoa; adiciona especialidade, registro, dias disponíveis
│
├── Consulta.java                      # Implementa Agendavel e Exportavel; associa Paciente e Profissional
├── Atendimento.java                   # Implementa Exportavel; composição com Prontuario
├── Prontuario.java                    # Parte de Atendimento (composição); observações, diagnóstico, procedimentos
│
├── Pagamento.java                     # Classe abstrata; implementa Exportavel
├── PagamentoDinheiro.java             # Subclasse de Pagamento
├── PagamentoCartao.java               # Subclasse de Pagamento (com taxa e parcelas)
├── PagamentoConvenio.java             # Subclasse de Pagamento (com desconto por cobertura)
│
├── Convenio.java                      # Representa operadora de saúde (nome + % cobertura)
├── Horario.java                       # Encapsula horário no formato HH:MM com validação
│
├── Agendavel.java                     # Interface: getData(), getHorario(), remarcar(), cancelar()
├── Exportavel.java                    # Interface: exportarDados()
│
├── Relatorio.java                     # Geração de relatórios (sobrecarga de métodos)
│
├── ConsultaNaoEncontradaException.java
├── ConvenioNaoCobreException.java
├── HorarioIndisponivelException.java
├── OperacaoInvalidaException.java
├── PacienteInativoException.java
├── PacienteNaoEncontradoException.java
├── PagamentoInvalidoException.java
└── ProfissionalNaoEncontradoException.java
```

---

## 🧱 Conceitos de POO Aplicados

### Encapsulamento
Todos os atributos das classes são privados ou protegidos, acessados apenas via getters e setters com validação.

### Herança
`Paciente` e `Profissional` herdam de `Pessoa` (classe abstrata), reaproveitando nome, CPF, telefone e data de nascimento. Cada subclasse sobrescreve `exibirResumo()`.

### Polimorfismo e Ligação Dinâmica
`Pagamento` é abstrata com o método `calcularValorFinal()` implementado diferentemente em `PagamentoDinheiro`, `PagamentoCartao` e `PagamentoConvenio`. O método correto é resolvido em tempo de execução com base no tipo real do objeto.

### Interfaces
- `Agendavel` — implementada por `Consulta`, garante contrato de agendamento
- `Exportavel` — implementada por `Consulta`, `Atendimento` e `Pagamento`, garante exportação padronizada

### Composição
`Atendimento` contém um `Prontuario`. O `Prontuario` tem construtor _package-private_, impossibilitando sua criação fora de `Atendimento`. Se o atendimento for removido, o prontuário deixa de existir.

### Associação
`Consulta` mantém referências diretas a `Paciente` e `Profissional` (associação por objeto), além de manter os campos textuais legados para compatibilidade.

### Sobrecarga
Construtores e métodos com múltiplas assinaturas são amplamente usados — exemplo: `Paciente` possui 4 construtores; `adicionarProcedimento()` aceita `String` ou `List<String>`.

### Coleções (Java Collections Framework)
`ClinicaServico` usa:
- `ArrayList<Consulta>`, `ArrayList<Atendimento>`, `ArrayList<Pagamento>` — ordem de inserção e acesso por índice
- `HashMap<String, Paciente>` e `HashMap<String, Profissional>` — busca eficiente por CPF e nome
- `HashSet<String>` — verificação rápida de CPFs já cadastrados

### Exceções Customizadas
O sistema lança exceções próprias e descritivas para cada situação de erro de negócio, em vez de retornar códigos ou `null`.

---

## ▶️ Como Executar

### Pré-requisitos
- Java 11 ou superior instalado
- Nenhuma dependência externa necessária

### Compilar
```bash
# Dentro do diretório src/
javac *.java
```

### Executar
```bash
java Main
```

### Exemplo de uso
```
=== CLINICA VIDAPLENA ===
1 - Pacientes
2 - Profissionais
3 - Consultas
4 - Atendimentos
5 - Pagamentos
6 - Relatorios
0 - Sair
Escolha:
```

---

## 📌 Observações Técnicas

- **Persistência**: o sistema não persiste dados entre execuções — tudo é mantido em memória durante a sessão.
- **Validações**: campos obrigatórios, valores negativos e formatos inválidos (como horário fora de `HH:MM`) são rejeitados com exceções descritivas.
- **Exportação**: os registros exportáveis seguem o formato CSV com ponto-e-vírgula como separador, impresso diretamente no terminal.
- **Limite de especialidades**: o sistema aceita apenas Clínica Geral, Fisioterapia, Psicologia e Nutrição como especialidades válidas de profissionais.
