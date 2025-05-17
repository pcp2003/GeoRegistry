# ES-2024_25-2Sem-Quarta-LEI-G
Projeto de Engenharia de Software 2024/25

## Identificação da Equipa
- Grupo G
- Disciplina: Engenharia de Software
- Semestre: 2º Semestre 2024/25

| Nome             | Número de Estudante | GitHub Username |
|------------------|---------------------|-----------------|
| Guilherme Penedo | 111222              | GuilhermePenedo |
| Pedro Pacheco    | 111039              | pcp2003         |
| João Antunes     | 111139              | deavenZ         |
| Rafael Lopes     | 111110              | tranquilizante  |

## Descrição do Projeto
O projeto consiste num sistema de gestão de cadastros imobiliários que processa dados geográficos a partir de ficheiros CSV. O sistema utiliza tecnologias modernas para processamento de dados geoespaciais e logging robusto, com uma interface gráfica para a visualização dos dados.

### Funcionalidades Principais
- Importação de dados cadastrais de ficheiros CSV
- Processamento de geometrias MultiPolygon usando JTS (Java Topology Suite)
- Sistema de logging completo com output para ficheiro
- Validação de dados geométricos
- Deteção de adjacências físicas entre propriedades
- Ordenação de cadastros por diferentes critérios (ID, comprimento, área, proprietário)
- Interface gráfica para visualização e interação com os dados
- Visualização de formas geométricas em painel dedicado
- Sugestões de troca de propriedades com análise de viabilidade

### Tecnologias Utilizadas
- Java 24
- Apache Commons CSV (1.9.0) para processamento de ficheiros CSV
- JTS Core (1.19.0) para manipulação de dados geométricos
- JUnit Jupiter (5.8.2) para testes unitários
- Maven para gestão de dependências e build

### Estrutura do Projeto
```
src/
├── lib/           # Bibliotecas externas
│   ├── commons-csv-1.9.0.jar
│   ├── jts-core-1.19.0.jar
│   └── junit-jupiter-5.8.2.jar
├── main/
│   ├── java/
│   │   ├── core/            # Classes fundamentais do sistema
│   │   │   ├── Constants.java
│   │   │   └── Main.java
│   │   ├── model/          # Modelos de dados
│   │   │   ├── Cadastro.java
│   │   │   └── Location.java
│   │   ├── service/        # Lógica de negócio
│   │   │   ├── exchange/   # Funcionalidade de troca de propriedades
│   │   │   │   ├── PropertyExchange.java
│   │   │   │   └── PropertyExchangeService.java
│   │   │   ├── OwnerGraph.java
│   │   │   └── PropertyGraph.java
│   │   └── ui/            # Interface gráfica
│   │       ├── Gui.java
│   │       └── ShapePanel.java
└── test/
    └── java/
        ├── core/          # Testes das classes fundamentais
        │   └── ConstantsTest.java    # Testes das constantes do sistema
        ├── model/         # Testes dos modelos de dados
        │   ├── CadastroTest.java     # Testes do modelo Cadastro
        │   └── LocationTest.java     # Testes do modelo Location
        └── service/       # Testes dos serviços
            ├── GraphTest.java        # Testes da classe base Graph
            ├── OwnerGraphTest.java   # Testes do grafo de proprietários
            ├── PropertyGraphTest.java # Testes do grafo de propriedades
            └── exchange/  # Testes da funcionalidade de troca
                ├── PropertyExchangeTest.java        # Testes do modelo de troca
                └── PropertyExchangeServiceTest.java # Testes do serviço de troca
```

### Classes Principais

#### Cadastro
- Representa um cadastro imobiliário
- Processa dados de ficheiros CSV
- Valida e processa geometrias MultiPolygon
- Gere informações como ID, comprimento, área, forma geométrica, proprietário e localização
- Implementa ordenação por diferentes critérios
- Calcula preços com base na localização e densidade de propriedades

#### Location
- Representa uma localização com freguesia, concelho e distrito
- Gere preços por metro quadrado baseados na hierarquia administrativa
- Implementa lógica de preços para diferentes níveis administrativos

#### PropertyExchange
- Representa uma sugestão de troca de propriedades
- Calcula diferenças de preço e viabilidade
- Avalia melhorias na área média após a troca

#### PropertyExchangeService
- Gera sugestões de troca de propriedades
- Analisa viabilidade das trocas
- Calcula métricas de melhoria
- Implementa algoritmos de otimização para trocas

#### GUI
- Implementa a interface gráfica do utilizador utilizando Java Swing
- Visualização de cadastros e suas propriedades
- Painel dedicado para visualização de formas geométricas
- Interação com o grafo de propriedades
- Visualização de sugestões de troca

### Como Executar
1. Certifique-se de ter Java 24 instalado
2. Clone o repositório
3. Execute o projeto usando Maven:
   ```bash
   mvn clean install
   # Para executar a interface gráfica
   mvn exec:java -Dexec.mainClass="core.Main"
   ```

### Testes
O projeto inclui testes unitários abrangentes organizados por pacote:

#### Testes de Core
- `ConstantsTest`: Testa as constantes do sistema
  - Validação de valores constantes
  - Verificação de configurações do sistema
  - Testes de mensagens de erro
  - Testes de constantes da GUI

#### Testes de Modelo
- `CadastroTest`: Testa a criação, validação e processamento de cadastros
  - Validação de dados de entrada
  - Processamento de geometrias
  - Cálculo de preços
  - Ordenação de cadastros
  - Manipulação de localização

- `LocationTest`: Testa o modelo de localização
  - Validação de hierarquia administrativa
  - Cálculo de preços por nível
  - Formatação de strings
  - Acesso a propriedades

#### Testes de Serviço
- `GraphTest`: Testa a classe base Graph
  - Construção do grafo
  - Adição de adjacências
  - Verificação de adjacência física
  - Filtragem por localização

- `OwnerGraphTest`: Testa o grafo de proprietários
  - Criação do grafo
  - Cálculo de área média
  - Obtenção de propriedades adjacentes
  - Contagem de proprietários e adjacências

- `PropertyGraphTest`: Testa o grafo de propriedades
  - Criação do grafo
  - Cálculo de área média
  - Obtenção de propriedades adjacentes
  - Contagem de propriedades e adjacências

- `PropertyExchangeTest`: Testa o modelo de troca de propriedades
  - Criação de sugestões de troca
  - Cálculo de diferenças de preço
  - Validação de viabilidade
  - Cálculo de melhorias de área

- `PropertyExchangeServiceTest`: Testa a funcionalidade de troca
  - Geração de sugestões
  - Cálculo de viabilidade
  - Análise de melhorias
  - Otimização de trocas

Execute os testes com:
```bash
mvn test
```

### Configuração do Ambiente
- Java 24
- Maven 3.8.1 ou superior
- UTF-8 encoding
- Dependências geridas via Maven

### Bibliotecas Externas
O projeto utiliza as seguintes bibliotecas externas, localizadas no diretório `src/lib`:
- Apache Commons CSV 1.9.0: Para processamento de ficheiros CSV
- JTS Core 1.19.0: Para manipulação de dados geométricos
- JUnit Jupiter 5.8.2: Para testes unitários

Estas bibliotecas são geridas pelo Maven e não precisam de ser instaladas manualmente.

