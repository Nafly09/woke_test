## <b> 🛠 Tecnologias utilizadas </b>

#### Java/Kotlin SDK Version

- OpenJDK Runtime Environment (build 17.0.10+13-LTS)

<br>

#### Framework

- Spring Boot

<br>

## 🛠 Instalação

<p>Caso queira instalar a API para rodar os testes localmente em sua máquina, siga os seguintes passos:</p>

1 - Tenha o [docker e docker compose]("https://docs.docker.com/desktop/") instalado em sua máquina. (A aplicação possui um compose para facilitar a configuiração do ambiente)

2 - Após garantir que está tudo instalado certinho, só navegar para a pasta do projeto e rodar o seguinte comando not terminal:

```
$ docker compose up -d
```

Se tudo tiver dado certo você terá buildado sua imagem docker da apliação e terá um container postgres rodando com todas as dependência necessária:


3 - Agora bastar dar play em sua aplicação pela própria IDE, o repositório foi subido com as variáveis de ambiente pré setadas para facilitar porém em um ambiente de produção elas seria adicionadas em cada ambiente.

4 - Ao rodar sua aplicação sera rodada uma migração usando flyway que já adicionará 6 empresas testes na aplicação, porém a sua conta de acesso é incentivada que seja criada por você mesmo ao iniciar a aplicação.

<hr>
<br>

## <b> 🌄 Inicialização da API </b>

Para começar a utilizar a API, copie a URL base da aplicação (Se estiver rodando localmente provável que será localhost:8080/api) e use-a na sua ferramenta cliente de API de preferência (recomendo o Postman), complementando a URL com os endopints da aplicação, explicados a seguir.

<br>

<br>

## <b> 🔚 Endpoints </b>

Existem 5 endpoints nessa aplicação:

<br>

<br>

## <b> > Usuário </b>

<br>

### <b> Registro </b>

<i> POST /sign_up </i>

Essa rota serve para registrar um novo usuário no banco de dados, sendo obrigatório passar no corpo da requisição o nome, email, telefone, senha (será salva como hash na base) e data de aniversário. <br>
Exemplo de requisição:

```json
{
  "name": "John Doe",
  "email": "john@email.com",
  "password": "doe.john",
  "phone": "919191919",
  "birthday": "2002-01-01"
}
```

Dessa requisição é esperado um retorno com os dados do usuário cadastrado, como mostrado a seguir:

```json
{
  "message": "User Created Successfully",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@email.com",
    "birthday": "2002-01-01",
    "phone": 919191919,
    "companies": []
  }
}
```

<br>

### <b> Login </b>

<i> POST /user/login </i>

Essa rota serve para fazer login de um usuário já cadastrado no banco de dados, sendo obrigatório passar no corpo da requisição o email, e senha do usuário. <br>
Exemplo de requisição:

```json
{
  "email": "john@email.com",
  "password": "doe.john"
}
```

Dessa requisição é esperado um retorno com o token de acesso do usuário, como mostrado a seguir:

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY0NjQxODA3NCwianRpIjoiNWE0ZDgzODMtZThlNS00MWYzLWEwMDItN2ZlODQzOTg0YzI5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6eyJ1c2VyX2lkIjo1LCJuYW1lIjoiSm9obiBEb2UiLCJlbWFpbCI6ImpvaG5AZW1haWwuY29tIiwicGFzc3dvcmRfaGFzaCI6InBia2RmMjpzaGEyNTY6MjYwMDAwJGEwTHBMOTJkdWE4ZkVTJDhlMDI3NzgwZTI5NzBiZDkxYTdiMWRjOTg0YWY4ZmJlODdkN2NjODNhODcwMWZhNzY5OWNhOTlhNjY2NWExY2UiLCJjcGYiOiI1NTU1NTU1NTU1NSIsImFkZHJlc3NlcyI6W10sIm9yZGVycyI6W119LCJuYmYiOjE2NDY0MTgwNzQsImV4cCI6MTY0NjUwNDQ3NH0.6X5CEa9cCiauP3qjy7eKvDsVMHr2DGpkPFrRI3YFtRw"
}
```

<br>

### <b> Listagem </b>

<i> GET /get_user_info </i>

Essa rota é usada para obter os dados do usuário que está logado, cadastrado no banco de dados e suas relações com outras empresas. <br>
Aqui não é necessário passar nenhum dado no corpo da requisição, apenas uma autorização do tipo bearer token, obtida no login do usuário. <br>
Exemplo de resposta dessa rota:

```json
{
  "message": "User Information Available",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@email.com",
    "birthday": "2002-01-01",
    "phone": 919191919,
    "companies": []
  }
}
```

<br>

### <b> Criar relação com empresas </b>

<i> POST /assign_companies </i>

Essa rota serve para simular um relação entre um usuário e empresas cadastrados, para manter histórico entre os dois. <br>
Você deve passar somente uma lista de IDs das empresas e o token do usuário no header que a endpoint cuidará do resto:

```json
{
  "companyIds": [1,2]
}
```

<br>

### <b> Listagem de empresa relacionadas com o usuário </b>

<i> GET /companies </i>

Essa rota é usada para obter os dados de empresas ainda disponíveis para relação daquele usuário logado. <br>
Aqui não é necessário passar nenhum dado no corpo da requisição, apenas uma autorização do tipo bearer token, obtida no login do usuário. <br>
PS: A rota já realiza o filtro excluindo do retorno aquelas contas no qual o usuário já tem relação. <br>
Exemplo de resposta dessa rota:

```json
{
  "companies": [
    {
      "id": 4,
      "name": "Nexxera",
      "companyEmail": "nexxera@company.com"
    }
  ]
}
```

<br>

