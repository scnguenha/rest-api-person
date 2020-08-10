# rest-api-person

Trata-se de dois projectos **demorestapi** e **RestAPI** que comunicam entre si através de serviços web.

## demorestapi
É uma simples API REST desenvolvida em JavaScript permite efectuar um **CRUD**
sobre um **JSON** tomado como "base de dados". A API é construída usando Node.js com as bibliotecas:
- **Express** - é um framework para aplicações web para Node.js [Documentação](https://expressjs.com/).
- **Joi** - é uma poderosa biblioteca de validação de dados para JavaScript [Documentação](https://www.npmjs.com/package/joi).
- **Nodemon** - é uma ferramenta que ajuda a desenvolver aplicações baseadas em node.js, 
reiniciando automaticamente a aplicação quando são detectadas alterações [Documentação](https://www.npmjs.com/package/nodemon).


## RestAPI
É uma aplicação Android simples criada para ser cliente da API REST **demorestapi** que consome os dados disponibilizados pela API, 
é a interface criada para visualizar, criar, editar e apagar dados da **demorestapi**. Para consumir dados da API usaram-se as seguintes bibliotecas:
- **Jackson** - é uma biblioteca baseada em java muito popular e eficiente para serializar ou mapear objetos java para JSON e vice-versa.
- **HttpClient**


## Como Testar?
**1.** Clonar os projectos;

**2.** Ter o Node.js instalado [ver](https://nodejs.org/).

**3.** Abrir o terminal na pasta do projecto **demorestapi** e digitar o seguinte comando para iniciar o servidor da API
```bash
node person.js
```

**4.** Conectar o dispositivo android a mesma rede que o computador.

**5.** Verificar o IP do servidor (computador) e coloca-lo nos métodos disponíveis na classe *PersonService.java*

**6.** Instalar e executar a aplicação **RestAPI** no dispositivo android.

## Demo
