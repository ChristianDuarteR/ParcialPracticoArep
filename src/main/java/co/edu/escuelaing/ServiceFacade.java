package co.edu.escuelaing;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

public class ServiceFacade {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            if (!in.ready()) {
                break;
            }
            String[] tokens = inputLine.split(" ");
            String method = tokens[0];
            String service = tokens[1];
            if (method.equals("GET")){
                doGet(service, out);
            }
        }
        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Mi propio mensaje</h1>\n"
                + "</body>\n"
                + "</html>\n";

        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    private static void doGet(String service, PrintWriter out) throws InvocationTargetException, IllegalAccessException {
        if (service.equals("/calculadora")){
            out.println("index.html");
            out.format("index.html");
            out.write("index.html");
        } else if(service.contains("/computar")) {
            out.println(ReflexCalculator.calculate(service));
        }
    }
}