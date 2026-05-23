package com.example.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class App {

    static List<String> expenses = new ArrayList<>();
    static double totalExpense = 0;

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new ExpenseHandler());

        server.setExecutor(null);

        System.out.println("Server started at http://localhost:8080");

        server.start();
    }

    static class ExpenseHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String method = exchange.getRequestMethod();

            if (method.equalsIgnoreCase("POST")) {

                String formData = new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );

                String[] pairs = formData.split("&");

                String category = "";
                double amount = 0;

                for (String pair : pairs) {

                    String[] keyValue = pair.split("=");

                    String key = URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");

                    if (key.equals("category")) {
                        category = value;
                    }

                    if (key.equals("amount")) {
                        amount = Double.parseDouble(value);
                    }
                }

                expenses.add(category + " - Rs " + amount);

                totalExpense += amount;
            }

            StringBuilder expenseList = new StringBuilder();

            for (String expense : expenses) {
                expenseList.append("<li>").append(expense).append("</li>");
            }

            String response =
                    "<html>" +
                    "<head>" +
                    "<title>Expense Tracker</title>" +
                    "<style>" +
                    "body{" +
                    "font-family:Arial;" +
                    "background:#f4f4f4;" +
                    "padding:40px;" +
                    "}" +
                    ".container{" +
                    "background:white;" +
                    "padding:20px;" +
                    "border-radius:10px;" +
                    "width:400px;" +
                    "margin:auto;" +
                    "box-shadow:0 0 10px gray;" +
                    "}" +
                    "input,select{" +
                    "width:100%;" +
                    "padding:10px;" +
                    "margin-top:10px;" +
                    "}" +
                    "button{" +
                    "padding:10px;" +
                    "width:100%;" +
                    "background:maroon;" +
                    "color:white;" +
                    "border:none;" +
                    "margin-top:10px;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Daily Expense Tracker</h1>" +

                    "<form method='POST'>" +

                    "<label>Category</label>" +
                    "<select name='category'>" +
                    "<option>Food</option>" +
                    "<option>Travel</option>" +
                    "<option>Shopping</option>" +
                    "<option>Medical</option>" +
                    "<option>Entertainment</option>" +
                    "</select>" +

                    "<label>Amount</label>" +
                    "<input type='number' name='amount' required>" +

                    "<button type='submit'>Add Expense</button>" +

                    "</form>" +

                    "<h2>Total Expense: Rs " + totalExpense + "</h2>" +

                    "<h3>Expense History</h3>" +
                    "<ul>" +
                    expenseList +
                    "</ul>" +

                    "</div>" +
                    "</body>" +
                    "</html>";

            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        }
    }
}
