<%@ page language="java" contentType="text/html;charset=UTF-8" import="main.java.models.vente.Unite" %>
<%
    Unite[] unites = (Unite[]) request.getAttribute("unites");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Unités - Boulangerie</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            height: 100vh;
            flex-direction: column;
            padding: 20px;
        }
        header {
            background-color: #ff5c5c;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 8px;
            width: 100%;
            margin-bottom: 20px;
        }
        .content {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            width: 100%;
            max-width: 1200px;
        }
        table {
            width: 60%;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            border-collapse: collapse;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        th {
            background-color: #ff5c5c;
            color: white;
        }
        td {
            border-bottom: 1px solid #ddd;
        }
        tr:hover {
            background-color: #f7f7f7;
        }
        form {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 35%;
        }
        label {
            font-weight: bold;
            margin-bottom: 10px;
            display: block;
        }
        input, select {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border: 2px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            transition: border 0.3s ease-in-out;
        }
        input:focus, select:focus {
            border: 2px solid #ff5c5c;
        }
        button {
            background-color: #ff5c5c;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            width: 100%;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #e04c4c;
        }
        .message {
            text-align: center;
            color: green;
            font-weight: bold;
            margin-top: 15px;
        }
        .error {
            width: 100vw;
            height: 100vh;
            background-color: rgba(0, 0, 0, 0.3);
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .error > div {
            width: 350px;
            height: 150px;
            background-color: rgba(210, 210, 210, 0.7);
            color: black;
            
        }
    </style>
</head>
<body>

    <% 
      String error = request.getParameter("error");
      if(error != null) { %>
        <div class="error">
          <div><%= error %></div>
        </div>
      <% } %>

    <header>
        <h1>Liste des Employés de la Boulangerie</h1>
    </header>

    <div class="content">

        <form id="employeeForm" method="POST" action="create.uniteController">
            <h2>Ajouter une nouvelle unité</h2>
            <label for="nom">Nom de l'unité :</label>
            <input type="text" id="nom" name="nom" required>

            <button type="submit">Ajouter l'unité</button>
        </form>

        <table id="employeeTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                </tr>
            </thead>
            <tbody>
                <% if(unites != null) for(Unite unite : unites) { %>
                    <tr>
                        <td><%= unite.getId() %></td>
                        <td><%= unite.getNom() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
