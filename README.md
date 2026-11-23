# FitTrack

Sistema web para organizar treinos, registrar séries, repetições e cargas, manter o histórico e visualizar a evolução de carga. O backend usa Java 21, Spring Boot, JPA e PostgreSQL.

## Requisitos

- Java 21
- Docker Desktop (recomendado) ou PostgreSQL 16+
- Maven Wrapper já incluso no projeto

## Banco de dados com Docker

No diretório que contém o `docker-compose.yml` do PostgreSQL, execute:

```powershell
docker compose up -d
```

O FitTrack está configurado para esta conexão padrão:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fittrack
spring.datasource.username=user
spring.datasource.password=pass
```

Crie o banco `fittrack` uma vez no pgAdmin ou no terminal do container:

```sql
CREATE DATABASE fittrack;
```

> Se o seu PostgreSQL usar usuário, senha ou porta diferentes, atualize `src/main/resources/application.properties`.

## Executar o sistema

No diretório do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

Abra [http://localhost:8080](http://localhost:8080) para acessar o painel de evolução. Cadastre o primeiro usuário pela rota abaixo e entre no painel usando o e-mail e a senha cadastrados.

Na primeira inicialização, o sistema cria automaticamente um administrador para facilitar os testes:

```text
E-mail: admin@fittrack.com
Senha: admin123
```

Altere essas credenciais em `application.properties` antes de publicar o sistema.

## Testes

```powershell
.\mvnw.cmd test
```

Os testes usam H2 em memória e não dependem do PostgreSQL local.

## Rotas principais

| Método | Rota | Descrição |
| --- | --- | --- |
| POST | `/api/auth/cadastro` | Cadastra usuário com senha criptografada |
| POST | `/api/auth/login` | Valida e-mail e senha |
| GET/PUT/DELETE | `/api/usuarios/{id}` | Consulta, atualiza ou desativa usuário |
| GET/POST/PUT/DELETE | `/api/exercicios` | Gerencia exercícios |
| GET/POST/PUT/DELETE | `/api/treinos` | Gerencia treinos |
| POST | `/api/treinos/{id}/itens` | Adiciona exercício a um treino |
| GET/POST/DELETE | `/api/registros-treino` | Histórico de treinos realizados |
| POST | `/api/registros-treino/{id}/series` | Registra série, carga e repetições |
| GET | `/api/evolucao?usuarioId={id}` | Retorna os dados da evolução de carga |

As rotas protegidas usam HTTP Basic. No painel, a autenticação é feita após o login.

### Exemplo: cadastrar usuário

```json
POST /api/auth/cadastro
{
  "nome": "Davi",
  "email": "davi@email.com",
  "senha": "senha123"
}
```

## Estrutura

```text
model/       Entidades e relacionamentos JPA
repository/  Acesso aos dados
service/     Regras de negócio
controller/  API REST
dto/         Objetos de entrada e resposta
config/      Configuração de segurança
static/      Dashboard de evolução
```
