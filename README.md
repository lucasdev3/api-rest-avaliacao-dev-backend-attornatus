# api-rest-avaliacao-dev-backend-attornatus

### Avaliação API Rest Desenvolvedor Back-end Attornatus

## Desenvolvedor - Lucas Souza

#### Qualificações
* Java Core
* Maven
* Spring Boot
* JPA
* JDBC
* Oracle DB, MySQL, PostgresSQL
* Linux
* Git e CVS
* Javascript

#### Contatos:
* Linkedin - https://www.linkedin.com/in/lucas-souza-478a031ab/
* GitHub - https://www.github.com/lucasdev3/

### Desafio Java

Usando Spring Boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir:

* Criar uma pessoa
* Editar uma pessoa
* Consultar uma pessoa
* Listar pessoas
* Criar endereço para pessoa
* Listar endereços para pessoa
* Poder informar qual endereço é o principal da pessoa

Uma Pessoa deve ter os seguintes campos

* Nome
* Data de Nascimento
* Endereço:
    * Logradouro
    * CEP
    * Numero

### DESENVOLVIMENTO

#### Tecnologias utilizadas:

* Sistema Operacional: Windows (Compativel com linux, testado nas distribuições Ubuntu e Fedora).
* Java 11 Eclipse Adoptium jdk-11.0.17.8-hotspot.
* Maven 3.8.6.
* Banco de dados H2 (em memória).
* Spring Boot 2.7.2
* JPA para persistencia e manipulação de dados.
* Bean validation para validações de atributos com validação global para do tipo '
  MethodArgumentNotValidException'.
* Swagger para documentação e testes de requisições na API disponivel na
  url: http://localhost:8080/swagger-ui/
* Log4j para logs em runtime e criação de um arquivo de logs no diretorio:
  /log-attornatus-lucasdev3-avaliacao/tmp/attornatus.log

#### Pontos de atenção

* Verifique a versão do Java
* Verifique a versão do Maven
* Verifique a versão do Spring Boot
* Verifique a existencia do diretório de logs do Log4j. Caso a aplicação não crie automaticamente,
  crie-o manualmente (mais informações no arquivo log4j.properties na raiz do projeto).
* A API fica em execução na porta 8080 local. Garanta que a mesma esteja livre para subir a aplicação.

#### Regras criadas pelo desenvolvedor

* Não é possivel ter mais de uma pessoa com o mesmo nome no banco.
* Não é possivel cadastrar/atualizar uma pessoa no banco sem a lista de endereços
* Não é possivel cadastrar/atualizar uma pessoa sem que tenha pelo menos um endereço com o atributo
  'tabelaPrincipal' retornando 'true (boolean)'.

#### Exemplo de cadastro de pessoa - PessoaDTO

* Metodo: POST - Rota: /pessoas/salvar
* JSON a ser enviado:
    ```
    {
     "nome": "teste5",
     "dataNascimento": "teste4",
     "enderecos": [
         {
           "logradouro": "teste4",
           "cep": "12123123",
           "numero": "44",
           "enderecoPrincipal": false
         },
         {
           "logradouro": "teste44",
           "cep": "14444444",
           "numero": "444",
           "enderecoPrincipal": true
         }
     ]
    }
    ```
* JSON de resposta - Exemplo de sucesso (200 - Ok):
    ```
    {
      "date": "17/02/2023 01:50:43:271",
      "message": "Pessoa cadastrada com sucesso!"
    }
    ```
* JSON de resposta - Exemplo de falha (400 - BadRequest):
  ```
    {
      "date": "17/02/2023 01:52:07:430",
      "message": "Nome já cadastrado no banco!"
    }
  ```

#### Exemplo de listagem de pessoas

* Metodo: GET - Rota: /pessoas
  * Retorno:
    ```
    [
        {
            "nome": "teste5",
            "dataNascimento": "teste4",
            "enderecos": [
                {
                    "logradouro": "teste44",
                    "cep": "14444444",
                    "numero": "444",
                    "enderecoPrincipal": true
                },
                {
                    "logradouro": "teste4",
                    "cep": "12123123",
                    "numero": "44",
                    "enderecoPrincipal": false
                }
            ]
        }
    ]
    ```
