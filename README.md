# minsaitChallenge

# README - Sistema de Fluxo de Caixa

Este é um sistema de fluxo de caixa baseado em uma arquitetura de microsserviços. Ele consiste em quatro microserviços: Autorização e Autenticação, Gateway, Report e Transaction Manager. Este documento fornece informações sobre como executar e testar a aplicação.

## Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- Java JDK (versão 17 ou superior)
- Maven
- Docker
- Docker Compose

## Executando a aplicação
Siga as etapas abaixo para executar o sistema de fluxo de caixa:

1. Clone o repositório para o seu ambiente local:

```
git clone <url do repositório>
```

2.Navegue até a pasta raiz do projeto:

```cd sistema-fluxo-caixa```

3. Compile as quatro aplicações utilizando o Maven. Navegue até cada uma das pastas dos microserviços e execute o seguinte comando:

```cd authorization-authentication
mvn install

cd ../gateway
mvn install

cd ../report
mvn install

cd ../transactionManager
mvn install

cd ../authentication-authorization
mvn install
```

4. Após a instalação bem-sucedida dos quatro microserviços, volte para a pasta raiz e inicie os containers Docker usando o Docker Compose:

```docker-compose up -d```

Isso iniciará os containers Docker para cada microserviço, configurando a infraestrutura necessária para a execução do sistema.

5. Aguarde até que todos os serviços sejam iniciados corretamente. Verifique o log de cada container para garantir que não haja erros.

6. Uma vez que os containers estejam em execução, você pode realizar chamadas de teste para o sistema utilizando o arquivo JSON exportado do Postman. O arquivo está localizado na raiz do projeto e chama-se **minsaitChallenge.postman.json.** Importe esse arquivo no Postman para ter acesso às chamadas de teste pré-configuradas.

## Estrutura do Projeto
- *authorization-authentication*: Microserviço responsável pela autorização e autenticação do sistema.
- *gateway*: Microserviço responsável pelo controle de rotas do sistema.
- *report*: Microserviço responsável pela geração de relatórios consolidados diários.
- *transactionManager*: Microserviço responsável pelas validações, persistência e processamento das transações.

## Contribuição
Sinta-se à vontade para contribuir com melhorias para este projeto. Você pode enviar pull requests com suas alterações e correções. Faremos uma revisão e incorporaremos as alterações relevantes ao repositório principal.

Agradecemos sua contribuição!

Contato
Se você tiver alguma dúvida ou precisar de mais informações, entre em contato conosco pelo email: __*leonardo.abernardino@gmail.com*__

