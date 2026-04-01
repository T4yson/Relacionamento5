# Relacionamento5

API REST desenvolvida com **Spring Boot** para gerenciamento de **Projetos** e **Tarefas**, demonstrando o uso de relacionamentos JPA (`@OneToMany` / `@ManyToOne`) em uma arquitetura em camadas bem definida.

---

## 📋 Sumário

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Modelo de Dados](#modelo-de-dados)
- [Endpoints](#endpoints)
- [DTOs e Validação](#dtos-e-validação)
- [Como Executar](#como-executar)
- [Testes](#testes)
- [Decisões Técnicas](#decisões-técnicas)
- [Autor](#autor)

---

## Visão Geral

O projeto expõe uma API RESTful para:

- Criar e listar **Projetos**
- Criar e listar **Tarefas**, vinculadas obrigatoriamente a um Projeto
- Filtrar Tarefas por projeto, ID ou título (busca case-insensitive)

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.5 |
| Spring Data JPA | — |
| Spring Web MVC | — |
| Jakarta Validation | — |
| Lombok | — |
| H2 Database (in-memory) | — |
| Maven | Wrapper incluso |

---

## Arquitetura

O projeto segue a arquitetura clássica de camadas do Spring:

```
Controller  →  Service  →  Repository  →  Model (JPA Entities)
     ↕               ↕
  DTOs           DTOs
```

```
src/main/java/com/example/Relacionamento5/
├── Relacionamento5Application.java   # Entry point
├── controller/
│   ├── ProjetoController.java
│   └── TarefaController.java
├── service/
│   ├── ProjetoService.java
│   └── TarefaService.java
├── repository/
│   ├── ProjetoRepository.java
│   └── TarefaRepository.java
├── model/
│   ├── Projeto.java
│   └── Tarefa.java
└── dto/
    ├── projeto/
    │   ├── ProjetoRequest.java
    │   └── ProjetoResponse.java
    └── tarefa/
        ├── TarefaRequest.java
        └── TarefaResponse.java
```

---

## Modelo de Dados

### Relacionamento

```
Projeto (1) ──────────── (N) Tarefa
```

- Um **Projeto** pode ter múltiplas **Tarefas** (`@OneToMany`, `cascade = CascadeType.ALL`)
- Cada **Tarefa** pertence a exatamente um **Projeto** (`@ManyToOne`, `fetch = FetchType.LAZY`, `nullable = false`)

### Projeto

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Identificador único (auto-incremento) |
| `nome` | String | Nome do projeto |
| `descricao` | String | Descrição do projeto |
| `tarefas` | List\<Tarefa\> | Lista de tarefas associadas |

### Tarefa

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Identificador único (auto-incremento) |
| `titulo` | String | Título da tarefa |
| `status` | String | Status atual da tarefa |
| `projeto` | Projeto | Projeto pai (FK: `projeto_id`) |

---

## Endpoints

### Projetos — `/atividade5/projetos`

| Método | Path | Descrição | Status |
|---|---|---|---|
| `POST` | `/atividade5/projetos` | Cria um novo projeto | `201 Created` |
| `GET` | `/atividade5/projetos` | Lista todos os projetos | `200 OK` |

#### POST `/atividade5/projetos`

**Request Body:**
```json
{
  "nome": "Meu Projeto",
  "descricao": "Descrição do projeto"
}
```

**Response:**
```json
{
  "id": 1,
  "nome": "Meu Projeto",
  "descricao": "Descrição do projeto"
}
```

---

### Tarefas — `/atividade5/tAREFAS`

> **Nota:** O path `/atividade5/tAREFAS` com capitalização mista é o mapeamento literal definido no controller (`@RequestMapping("/atividade5/tAREFAS")`). Utilize exatamente este path nas requisições.

| Método | Path | Descrição | Status |
|---|---|---|---|
| `POST` | `/atividade5/tAREFAS` | Cria uma nova tarefa | `201 Created` |
| `GET` | `/atividade5/tAREFAS` | Lista tarefas (com filtros opcionais) | `200 OK` |

#### POST `/atividade5/tAREFAS`

**Request Body:**
```json
{
  "titulo": "Implementar autenticação",
  "status": "Em andamento",
  "projetoId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "titulo": "Implementar autenticação",
  "status": "Em andamento",
  "projetoId": 1,
  "projetoNome": "Meu Projeto"
}
```

#### GET `/atividade5/tAREFAS` — Query Parameters (todos opcionais)

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `projetoId` | Long | Filtra tarefas pelo ID do projeto |
| `id` | Long | Filtra por ID da tarefa (usado em conjunto com `titulo`) |
| `titulo` | String | Filtra por título (case-insensitive, busca parcial; usado em conjunto com `id`) |

**Lógica de filtragem:**
1. Se `id` **e** `titulo` forem informados → busca por ID + título (case-insensitive)
2. Se apenas `projetoId` for informado → lista tarefas do projeto
3. Sem parâmetros → lista todas as tarefas

---

## DTOs e Validação

O projeto utiliza **Java Records** como DTOs, garantindo imutabilidade e código conciso.

As validações são feitas via **Jakarta Validation** (`@Valid` nos controllers):

| DTO | Campo | Regra |
|---|---|---|
| `ProjetoRequest` | `nome` | `@NotBlank` |
| `ProjetoRequest` | `descricao` | `@NotBlank` |
| `TarefaRequest` | `titulo` | `@NotBlank` |
| `TarefaRequest` | `status` | `@NotBlank` |
| `TarefaRequest` | `projetoId` | `@NotNull` |

---

## Como Executar

### Pré-requisitos

- Java 21+
- Maven (ou usar o wrapper incluso `./mvnw`)

### Rodando a aplicação

```bash
# Clone o repositório
git clone https://github.com/T4yson/Relacionamento5.git
cd Relacionamento5/Relacionamento5

# Execute com o Maven Wrapper
./mvnw spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`.

> **Banco de dados:** H2 em memória — os dados são reiniciados a cada execução. O console H2 pode ser acessado em `http://localhost:8080/h2-console` (se habilitado nas properties).

### Exemplo rápido com cURL

```bash
# Criar projeto
curl -X POST http://localhost:8080/atividade5/projetos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Projeto Alpha","descricao":"API de exemplo"}'

# Criar tarefa
curl -X POST http://localhost:8080/atividade5/tAREFAS \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Criar endpoints","status":"Pendente","projetoId":1}'

# Listar tarefas do projeto 1
curl http://localhost:8080/atividade5/tAREFAS?projetoId=1
```

---

## Testes

O projeto contém um teste de carregamento de contexto Spring Boot:

```bash
./mvnw test
```

O teste `Relacionamento5ApplicationTests#contextLoads` valida que todo o contexto da aplicação é inicializado corretamente.

---

## Decisões Técnicas

| Decisão | Justificativa |
|---|---|
| **Java Records como DTOs** | Imutabilidade nativa, código conciso e seguro para transferência de dados |
| **Lombok (`@Data`, `@RequiredArgsConstructor`)** | Reduz boilerplate de getters/setters e construtores nas entidades e services |
| **CascadeType.ALL no `@OneToMany`** | Persistência e remoção de tarefas são propagadas automaticamente a partir do projeto pai |
| **FetchType.LAZY no `@ManyToOne`** | Evita carregamento desnecessário do projeto ao buscar tarefas individualmente |
| **H2 em memória** | Banco leve para desenvolvimento e testes, sem necessidade de configuração externa |
| **Injeção via construtor (`@RequiredArgsConstructor`)** | Boas práticas do Spring — favorece imutabilidade e facilita testes unitários |
| **Camada Service dedicada** | Isola a lógica de negócio dos controllers, mantendo responsabilidades bem definidas |
| **`ProjetoService.findEntityById`** | Centraliza a busca de entidades com tratamento de "não encontrado", reutilizável pelo `TarefaService` |
| **Query Methods do Spring Data** | `findByProjetoId` e `findByIdAndTituloContainingIgnoreCase` eliminam a necessidade de JPQL manual |
| **Validação com `@Valid`** | Garante integridade dos dados na entrada da API antes de qualquer processamento |

---

## Autor

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/T4yson">
        <b>T4yson</b>
      </a>
    </td>
  </tr>
</table>

[![GitHub](https://img.shields.io/badge/GitHub-T4yson-181717?style=flat&logo=github)](https://github.com/T4yson)
