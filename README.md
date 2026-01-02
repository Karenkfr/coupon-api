# Coupon API

API REST para gerenciamento de cupons de desconto, desenvolvida como desafio técnico, seguindo rigorosamente o contrato OpenAPI fornecido via Apidog.

---

## 🧾 Contrato da API

O contrato oficial da API foi disponibilizado via Apidog:

https://n1m0i5k0zu.apidog.io/

Todos os endpoints, campos, tipos e status HTTP foram implementados exatamente conforme especificado no contrato.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- Spring Validation
- Springdoc OpenAPI (Swagger)
- JUnit 5

---

## 📦 Endpoints Implementados

| Método | Endpoint | Descrição |
|------|---------|-----------|
| POST | /coupon | Criação de cupom |
| GET | /coupon/{id} | Busca de cupom por ID |
| DELETE | /coupon/{id} | Exclusão lógica (soft delete) |

---

## 📄 Documentação Local

A aplicação expõe documentação Swagger para facilitar testes locais e validação da implementação:

http://localhost:8080/swagger-ui.html

> O Swagger reflete a implementação da API e permite validar a aderência ao contrato fornecido.

---

## 🧠 Regras de Negócio

- Código do cupom:
  - Alfanumérico
  - 6 caracteres
  - Caracteres especiais são removidos automaticamente
- Valor mínimo de desconto: 0.5
- Data de expiração não pode estar no passado
- Exclusão lógica via status `DELETED`
- Não é permitido deletar um cupom já deletado

As regras estão encapsuladas no domínio, garantindo separação de responsabilidades.

---

## 🧪 Testes

O projeto possui testes cobrindo:

- Regras de domínio
- Camada de service
- Integração real via API

Cobertura superior a 80% das regras de negócio.

Para rodar os testes:
```bash
mvn test
