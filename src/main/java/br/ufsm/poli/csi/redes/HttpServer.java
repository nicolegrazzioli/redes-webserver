package br.ufsm.poli.csi.redes;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static String header = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html;charset=ISO-8859-1\r\n" +
            "Pragma: No-cache\r\n" +
            "Cache-Control: no-cache\r\n" +
            "Connection: close\r\n" +
            "Content-Language: pt-BR\r\n\r\n";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");
        while (true) {
            Socket s = serverSocket.accept();
            System.out.println("New connection from " + s.getRemoteSocketAddress());
            byte[] bReq = new byte[1024];
            int nReq = s.getInputStream().read(bReq);
            String req = new String(bReq, 0, nReq);
            String[] linhas = req.split("\n");
            String[] linha0 = linhas[0].split(" ");
            String metodo = linha0[0];
            String recurso = linha0[1];
            System.out.println("metodo: " + metodo);
            System.out.println("recurso: " + recurso);
            if (metodo.equals("GET")) {
                String arquivo;
                if (recurso.equals("/")) {
                    arquivo = "/index.html";
                } else {
                    arquivo = recurso;
                }
                File arquivoFile = new File("html" + arquivo);
                if (arquivoFile.exists()) {
                    FileInputStream fis = new FileInputStream(arquivoFile);
                    byte[] fileData = fis.readAllBytes();
                    String resp = header + new String(fileData) + "\r\n\r\n";
                    s.getOutputStream().write(resp.getBytes());
                } else {
                    s.getOutputStream().write("404 Not Found\r\n\r\n".getBytes());
                }
            } else {
                s.getOutputStream().write("400 Bad Request\r\n\r\n".getBytes());
            }

            s.close();
        }

    }

}
