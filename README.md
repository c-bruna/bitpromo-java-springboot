# BitPromo

Aplicação web para cadastro e gerenciamento de produtos por empresa.

---

## Pré-requisitos

Antes de executar a aplicação, verifique se você possui instalado em sua máquina:

- Java 17+  
- Maven  
- Node.js e NPM  
- MySQL (ou outro banco compatível)  

---

## Configuração inicial

Antes de rodar a aplicação, abra o arquivo `application.properties` e configure os dados do banco de dados e a chave do token JWT:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bitpromo
spring.datasource.username={SEU_USUARIO}
spring.datasource.password={SUA_SENHA}

spring.jpa.hibernate.ddl-auto=update
server.port=8080
spring.application.name=bitpromo

api.security.token.secret=minha_chave_secreta
````

> Certifique-se de que o banco de dados `bitpromo` exista no MySQL.

---

## Executando a aplicação

### 1. Backend (API)

1. Abra um terminal na raiz do projeto.
2. Execute o comando:

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`.

---

### 2. Frontend

1. Abra outro terminal na raiz do projeto.
2. Execute o comando:

```bash
npx serve .
```

3. Abra o navegador e acesse a aplicação:

```
http://localhost:3000/frontend/
```

---

## Observações

* O frontend e backend estão em portas diferentes, então **CORS está configurado no backend** para permitir requisições do frontend.
* Para acessar o cadastro de produtos, primeiro faça login com uma conta de empresa.
* Sempre abra o frontend via **servidor local (`serve`)**, **não diretamente pelo `file://`**, para evitar problemas de CORS.

---

## Tecnologias utilizadas

* **Backend:** Java, Spring Boot, Spring Security, Maven
* **Frontend:** HTML, JavaScript, Bootstrap
* **Banco de dados:** MySQL
* **Autenticação:** JWT
* **Servidor local frontend:** `serve` (Node.js)

```

---

Se você quiser, posso criar uma **versão ainda mais enxuta e elegante**, tipo documentação “profissional” que ficaria ótima no GitHub, com badges, links rápidos para login e cadastro, e estilo visual limpo.  

Quer que eu faça essa versão?
```
