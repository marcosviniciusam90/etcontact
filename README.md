# ETContact
> API REST para cadastro de pessoas e contatos

## Links
- Coleção e variáveis de ambiente do Postman: [Link](https://github.com/marcosviniciusam90/etcontact/tree/master/backend/doc)

### API em produção
- Endereço: https://mvam-etcontact.herokuapp.com
- Swagger JSON: https://mvam-etcontact.herokuapp.com/api-docs
- Swagger UI: https://mvam-etcontact.herokuapp.com/swagger-ui/index.html?url=/api-docs

### API local
- Endereço: http://localhost:8080
- Swagger JSON: http://localhost:8080/api-docs
- Swagger UI: http://localhost:8080/swagger-ui/index.html?url=/api-docs

<h2 id="autorizacao">Como obter autorização para realizar requisições pelo Swagger?</h2>

No swagger, clique no botão **Authorize** e informe os dados:
- username/password: `marcos@gmail.com/marcos` ou `admin@gmail.com/admin`
- client_id/client_secret: **myclientid/myclientsecret**<br/><br/>
Obs: o usuário **marcos** é um visitante (pode buscar a lista de usuários, usuário por id e adicionar contatos), já o usuário **admin** possui todas as permissões.

## O que foi utilizado?
- Java
- Spring
- REST
- Hibernate/JPA (Query methods, controle de transação, etc)
- H2 Database
- MapStruct (para converter entidade <-> DTO)
- Lombok
- Bean Validation
- Exceptions Handler
- Spring Security (OAuth2 + JWT com controle de permissões)
- Spring Events
- Spring Profiles
- Spring OpenAPI (Swagger UI)
- Spring MockMvc (para testar requisições REST)
- Mockito