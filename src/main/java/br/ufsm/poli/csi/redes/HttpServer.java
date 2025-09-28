package br.ufsm.poli.csi.redes;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    // String estática para o cabeçalho da resposta HTTP
    private static String header = "HTTP/1.1 200 OK\r\n" + //deu certo
            "Content-Type: text/html;charset=ISO-8859-1\r\n" + //enviando arquivo HTML
            "Pragma: No-cache\r\n" +
            "Cache-Control: no-cache\r\n" + //navegador não vai guardar cópia (cache) da página
            "Connection: close\r\n" + //servidor vai fechar a conexão assim que a resposta for enviada
            "Content-Language: pt-BR\r\n\r\n"; //linha em branco (separa o cabeçalho do conteúdo do arquivo)

    public static void main(String[] args) throws IOException {
        //cria Socket na porta 8080, fica ouvindo esperando clientes
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");

        //loop infinito para o servidor ficar sempre rodando e aceitando novos clientes
        while (true) {
            //programa para e espera um navegador se conectar
            Socket s = serverSocket.accept(); //Socket s criado para comunicação com o novo cliente (só atende 1 cliente por vez)
            System.out.println("New connection from " + s.getRemoteSocketAddress());

            //prepara um espaço (array de bytes) para ler a requisição do cliente
            byte[] bReq = new byte[1024];
            int nReq = s.getInputStream().read(bReq);

            //convertes os bytes lidos em uma String
            String req = new String(bReq, 0, nReq);

            //quebra a mensgame de texto (requisição) em linhas
            String[] linhas = req.split("\n");
            //quebra a 1a linha em um array de palavras, ex: "GET", "/", "HTTP/1.1"
            String[] linha0 = linhas[0].split(" ");

            //pega a 1a palavra (metodo) e a 2a (recurso, arquivo)
            String metodo = linha0[0];
            String recurso = linha0[1];
            System.out.println("metodo: " + metodo);
            System.out.println("recurso: " + recurso);


            //se o metodo é GET
            if (metodo.equals("GET")) {
                String arquivo;
                //se o recurso é "/" assume que é index.html
                if (recurso.equals("/")) { //localhost:8080
                    arquivo = "/index.html";
                //se não, usa o nome que veio na requisição
                } else {
                    arquivo = recurso;
                }

                //cria um objeto File que aponta para o arquivo no disco
                File arquivoFile = new File("html" + arquivo); //assume que existe uma pasta html no mesmo diretório onde o servidor está rodando

                //verifica se o arquivo existe
                if (arquivoFile.exists()) {
                    //se existe, abre para leitura
                    FileInputStream fis = new FileInputStream(arquivoFile);

                    //le o conteudo do arquivo para um array de bytes
                    byte[] fileData = fis.readAllBytes();

                    //monta a resposta: cabeçalho + conteúdo do arquivo lido (recurso)
                    String resp = header + new String(fileData) + "\r\n\r\n";

                    //envia resposta para o navegador pelo canal de saída do Socket
                    s.getOutputStream().write(resp.getBytes());

                //se o arquivo nao existe
                } else {
                    //envia mensagem de erro 404 not found
                    s.getOutputStream().write("404 Not Found\r\n\r\n".getBytes());
                }

            //se o metodo não for GET
            } else {
                //envia mensagem de erro 400 bad request
                s.getOutputStream().write("400 Bad Request\r\n\r\n".getBytes());
            }

            //fecha conexão com o cliente atual e libera os recursos
            s.close();
        }

    }

}
