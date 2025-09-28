### Servidor Web em Java
- implementa o protocolo TCP: ```ServerSocket``` e ```Socket```
- o servidor fica escutando a porta 8080 do PC
- quando o cliente abre ```http://localhost:8080```, o navegador manda uma mensagem ao servidor
- o servidor recebe a mensagem (requisição HTTP) e entende qual arquivo o navegador quer (ex: ```index.html```) e localiza esse arquivo no PC
- o servidor envia uma resposta HTTP ao navegador com o conteúdo do arquivo
- o navegador exibe o ```index.html``` ao cliente

#### Limitações
- é monothread (1 cliente por vez)
- processa apenas método GET
- não gera conteúdo dinâmico
- análise de requisção simples

#### Estrutura de arquivos
- ```HttpServer.java``` - implementa "Servidor" do modelo cliente-servidor
- ```index.html``` - recurso default

#### Para rodar
- Run 'HttpServer' → ```'Server started'```
- http://localhost:8080/
- ~~~   
    New connection from [0:0:0:0:0:0:0:1]:52834
    metodo: GET
    recurso: /
  ~~~