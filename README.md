# VetFinance AI

Um assistente financeiro inteligente para clínicas veterinárias integrado com Inteligência Artificial, processamento de áudio (Speech-to-Text e Text-to-Speech) e Tool Calling.

---

## Sobre o Projeto

O **VetFinance AI** é a evolução de um projeto de controle de orçamento pessoal concebido durante a trilha Spring Boot + Spring AI da Digital Innovation One (DIO). A principal proposta desta evolução foi transformar o domínio original (finanças pessoais) em uma solução voltada para **clínicas veterinárias**, mantendo a excelência arquitetural.

O sistema permite que gestores de clínicas registrem movimentações financeiras, consultem faturamento e auditem interações não apenas por meios tradicionais (endpoints REST), mas também enviando comandos de áudio. A IA interpreta a voz do usuário, chama as ferramentas (Tool Calling) necessárias e responde de forma humanizada com um novo áudio gerado dinamicamente.

---

## Funcionalidades

* **Processamento de Áudio Inteligente**: Recebimento de áudios com comandos financeiros.
* **Transcrição com Whisper (Speech-to-Text)**: Conversão do áudio do usuário para texto.
* **Tool Calling**: A IA detecta a intenção e executa automaticamente casos de uso do sistema (registrar transações, buscar faturamento, etc.).
* **Resposta em Áudio (Text-to-Speech)**: Resposta gerada pela IA convertida em áudio mp3 utilizando a voz da OpenAI (Nova).
* **Auditoria de Interações**: Histórico salvo de todos os comandos de texto enviados à IA e suas respectivas respostas.
* **Dashboard Financeiro**: Endpoint para visão consolidada da clínica (faturamento total, número de transações, categoria líder, volume de auditorias).
* **Consultas Financeiras**: Listagem de transações por categoria e totais financeiros.
* **Cadastro Manual de Movimentações**: Inserção de transações financeiras diretamente por payload JSON.

---

## Arquitetura

O projeto foi construído inspirado em **Domain-Driven Design (DDD)** e **Clean Architecture**, dividindo suas responsabilidades em camadas bem definidas para garantir baixo acoplamento e alta coesão:

* **Domain**: O coração da aplicação. Contém as entidades de negócio (`Transaction`, `Interaction`, `Category`), regras fundamentais e interfaces de repositórios (contratos). Não conhece frameworks externos.
* **Application**: Camada de orquestração. Contém os casos de uso (`GetDashboardUseCase`, `PersistTransactionUseCase`, `RegisterInteractionUseCase`) e as portas de entrada e saída (DTOs). É onde as *Tools* da IA são declaradas.
* **Infrastructure**: Responsável por detalhes técnicos. Contém os controladores HTTP, integrações de banco de dados (`Spring Data JPA`), configuração do Spring AI, persistência e comunicação com a OpenAI.

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring AI
* Spring Data JPA
* Spring Web
* MySQL
* OpenAI API
* Whisper (Transcrição)
* GPT-4o Mini (LLM)
* Text-to-Speech / Voz Nova (Geração de Áudio)
* Gradle
* Docker Compose

---

## Como Executar

**1. Clonar repositório:**

```bash
git clone https://github.com/seu-usuario/vetfinance-ai.git
cd vetfinance-ai
```

**2. Configurar OPENAI_API_KEY:**
Defina sua chave da OpenAI como uma variável de ambiente no seu terminal:

```bash
export OPENAI_API_KEY="sk-SuaChaveAqui"
```

**3. Configurar banco de dados:**
O projeto utiliza a dependência `spring-boot-docker-compose`, que irá iniciar e configurar automaticamente um container MySQL a partir do arquivo docker-compose (caso exista) na inicialização da aplicação. Basta ter o Docker instalado e rodando na sua máquina.

**4. Executar aplicação:**

```bash
./gradlew bootRun
```

---

## Como Testar

O fluxo de negócio mais avançado do projeto é a **interação com a Inteligência Artificial**:

1. **Envio de áudio**: O usuário envia um arquivo de áudio para o endpoint `POST /transactions/ai`. Exemplo de fala: *"Registre uma consulta de 150 reais."*
2. **Transcrição**: O Spring AI aciona o Whisper para transformar o áudio em texto.
3. **Interpretação**: O GPT-4o Mini recebe o prompt (contextualizado para identificar gastos) e entende a intenção.
4. **Tool Calling**: A LLM aciona o `PersistTransactionUseCase` e a transação é persistida.
5. **Persistência da Auditoria**: O sistema grava no banco a interação (texto enviado x texto respondido).
6. **Geração de Áudio**: A resposta ("A consulta de R$ 150 foi registrada com sucesso") é convertida em um arquivo mp3 de áudio pelo Text-to-Speech e retornado na chamada HTTP.

**Exemplo de requisição (cURL):**

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@/caminho/para/seu/audio.m4a" \
  --output resposta.mp3
```

### Endpoints REST Adicionais

Você também pode gerenciar o sistema manualmente usando ferramentas como Postman, Insomnia ou cURL:

* **Criar Transação**: `POST /transactions` (Payload JSON)
* **Listar por Categoria**: `GET /transactions/{category}`
* **Ver Dashboard**: `GET /dashboard`
* **Consultar Auditorias**: `GET /audits`

---

## Melhorias Implementadas

Além da proposta do projeto base (orçamento pessoal), esta evolução trouxe:

* **Mudança de Domínio**: Adaptação de categorias e prompts para o nicho de Clínicas Veterinárias.
* **Dashboard Analítico**: Endpoint dedicado para extração de indicadores financeiros consolidados, agrupando chamadas otimizadas da aplicação.
* **Auditoria de Interações Exposta**: Criação de `ListInteractionsUseCase` e seu respectivo endpoint `GET /interactions` para transparência nos comandos repassados à IA.
* **Tratamento Específico por Tool Calling**: Integração granularizada de múltiplos Use Cases configurados como ferramentas individuais para a LLM (`@Tool`).

---

## Estrutura do Projeto

```text
src/main/java/dio/budgeting/
├── application/           # Use Cases e DTOs (Inputs/Outputs)
│   ├── input/
│   ├── output/
│   └── GetDashboardUseCase.java, PersistTransactionUseCase.java...
├── domain/                # Entidades, Enums e Repositórios
│   ├── Category.java
│   ├── Interaction.java
│   ├── Transaction.java
│   └── ...
└── infrastructure/        # Configurações, Controllers e Adaptadores JPA
    ├── http/
    └── persistence/
        ├── entity/
        └── repository/
```

---

## Aprendizados

Durante o desenvolvimento deste projeto, explorei e consolidei os seguintes conceitos:

* **Spring AI**: Configuração e integração unificada de diferentes modelos da OpenAI no ecossistema Spring.
* **Tool Calling (Function Calling)**: Ensinando LLMs a invocarem trechos específicos de código Java para gerar resultados determinísticos.
* **Speech-to-Text & Text-to-Speech**: Manipulação de streams de áudio e arquivos multipart no Spring Boot.
* **Domain-Driven Design (DDD)**: Foco no isolamento e na representação fiel das regras de negócio independentes de banco de dados ou frameworks.
* **Clean Architecture**: Garantia da fluidez dos dados de Fora (Infraestrutura) para Dentro (Domínio), e nunca o contrário.
* **Evolução Incremental de Software**: Como refatorar e estender um domínio genérico para um nicho específico de mercado sem quebrar a estrutura fundamental.

---

## Possíveis Evoluções Futuras

* **Autenticação**: Inclusão de Spring Security (JWT) para restringir acesso à administração.
* **Multiusuário (Multi-tenant)**: Capacidade de suportar diversas clínicas na mesma base de dados.
* **Integração com Webhooks ou Chatbots**: Transformar as respostas de áudio em disparos via WhatsApp (Ex: Twilio/Meta API).
* **Filtros e Paginação**: Melhoria nos endpoints de listagem de transações e auditorias utilizando `Pageable`.
* **Dashboard Web**: Construção de um frontend em React ou Angular que consuma os dados analíticos de `GET /dashboard`.

---

## Créditos

Este projeto foi originalmente desenvolvido durante a trilha **Spring Boot da Digital Innovation One (DIO)**. O escopo e a estrutura base serviram como fundação para criar o *VetFinance AI*, aplicando um domínio próprio de mercado veterinário e agregando novas camadas de visibilidade (Dashboards) e segurança (Auditorias).
