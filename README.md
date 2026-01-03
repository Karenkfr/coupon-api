# Coupon API

API REST para gerenciamento de cupons de desconto, desenvolvida como desafio técnico, seguindo rigorosamente o contrato OpenAPI fornecido via Apidog.

---

## 🧾 Contrato da API

O contrato oficial da API foi disponibilizado via Apidog:

[https://n1m0i5k0zu.apidog.io/](https://n1m0i5k0zu.apidog.io/)

Todos os endpoints, campos, tipos e status HTTP foram implementados exatamente conforme especificado no contrato.

---

## 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot 3.3.9  
- Spring Web  
- Spring Data JPA  
- H2 Database (in-memory)  
- Spring Validation  
- Springdoc OpenAPI (Swagger)  
- JUnit 5  
- Docker e Docker Compose  

---

## 📦 Endpoints Implementados

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST   | /coupon | Criação de cupom |
| GET    | /coupon/{id} | Busca de cupom por ID |
| DELETE | /coupon/{id} | Exclusão lógica (soft delete) |

---

## 🧠 Regras de Negócio

- **Código do cupom**:
  - Alfanumérico
  - 6 caracteres
  - Caracteres especiais são removidos automaticamente antes de persistir
- **Valor de desconto**:
  - Mínimo: 0.5
  - Sem máximo
- **Data de expiração**:
  - Não pode estar no passado
- **Exclusão de cupom**:
  - Exclusão lógica via status `DELETED`
  - Não é permitido deletar um cupom já deletado
- **Publicação**:
  - Um cupom pode ser criado como publicado (`published = true`)

As regras estão **encapsuladas no domínio**, garantindo separação de responsabilidades.

---

## 📄 Documentação Swagger

A aplicação possui documentação Swagger, que permite validar os endpoints e os contratos localmente.

- **Local**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- **Via Docker**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)  

---

## 💻 Executando Localmente

Para rodar a aplicação localmente (sem Docker):

```bash
# Build e rodar a aplicação
mvn spring-boot:run
```
A aplicação será acessível em http://localhost:8080

Swagger: http://localhost:8080/swagger-ui.html

```bash
# Para realizar os testes:
mvn test
```

## 🐳 Executando via Docker

O projeto pode ser executado em container usando Docker e Docker Compose.

### Subindo a aplicação

1. Build e subir container:

```bash
docker-compose up --build
```

A aplicação estará acessível na porta 8081:

API: http://localhost:8081

Swagger: http://localhost:8081/swagger-ui.html

Parar containers:
```bash
docker-compose down
```
#### ⚠️ Observação: a porta 8081 foi mapeada no docker-compose.yml para não conflitar com a aplicação rodando localmente na porta 8080.

## 📝 Possíveis melhorias com mais tempo

- Persistência em banco real (PostgreSQL/MySQL);
- Logging estruturado e monitoramento;
- Versionamento da API;
- Listagem e filtros de cupons (por status ou data de expiração);
- Criação de novos endpoints, a depender da necessidade do negócio.
- Utilização de ferramentas de qualidade de código com maior aprofundamento (sonar, mend fortify)
