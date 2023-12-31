# attornatus-backend-desafio
Sistema de gerenciamento de pessoas e endereços

## :page_with_curl: Conteúdo

- [Sobre a API](#sobre)
- [Inicializando a aplicação](#iniciando)
- [Tecnologias/Ferramentas](#ferramentas)

## :information_source: Sobre a API <a name = "sobre"></a>
Desafio Back-End da attornatus que consiste em criar uma API para gerenciar pessoas e seus endereços.

#### Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir: 

- Criar uma pessoa

- Editar uma pessoa

- Consultar uma pessoa

- Listar pessoas

- Criar endereço para pessoa

- Listar endereços da pessoa

- Poder informar qual endereço é o principal da pessoa
  

#### Uma Pessoa deve ter os seguintes campos: 

- Nome

- Data de nascimento

- Endereço:

  -> Logradouro

  -> CEP

  -> Número

  -> Cidade

#### Requisitos:

- Todas as respostas da API devem ser JSON 

- Banco de dados H2

- Testes unitários

- Clean Code

## :arrow_forward: Inicializando a aplicação  <a name = "iniciando"></a>

### Requisitos para rodar os códigos são:

* Java 17

## :hammer: Tecnologias/Ferramentas utilizadas <a name = "ferramentas"></a>

### Ambiente de Desenvolvimento:
* [Spring Tools 4](https://spring.io/tools)

### Gerenciador de dependencias e configurações do projeto:
- Maven

### Banco de dados:
- H2 database: banco de dados em memoria, usado para testes

### Testes:
#### Testes unitários:
- Mockito
- JUnit
- Relatório de testes: [Link](https://thalesjab.github.io/attornatus-backend-desafio/)
#### Teste de cobertura:
- [EclEmma](https://marketplace.eclipse.org/content/eclemma-java-code-coverage): ferramenta externa, instalada no Spring Tools Suite, que utiliza a biblioteca do JaCoCo

### Versionamento:
- Git: repositório local
- Github: repositório remoto para compartilhamento

### Dependências do projeto:
- org.springframework.boot:spring-boot-starter
- org.springframework.boot:spring-boot-starter-test (scope: test)
- org.springframework.boot:spring-boot-starter-web
- org.springframework.boot:spring-boot-devtools (scope: runtime, optional: true)
- org.springframework.boot:spring-boot-starter-data-jpa
- com.h2database:h2 (scope: runtime)
- org.modelmapper:modelmapper:3.1.1
- org.springframework.boot:spring-boot-starter-validation




