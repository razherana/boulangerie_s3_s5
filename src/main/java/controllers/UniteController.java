package main.java.controllers;

import main.java.connexion.Base;
import main.java.models.vente.Unite;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "UniteController", value = "*.uniteController")
public class UniteController extends MereController {
    @CtrlAnnotation(name = "list")
    public void list() throws IOException, SQLException, ServletException {
        Connection connection = Base.PsqlConnect();
        Unite[] unites = new Unite().getAll(connection);
        if (connection != null)
            connection.close();
        request.setAttribute("unites", unites);
        RequestDispatcher rd = request.getRequestDispatcher("/views/unites/list-unites.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "create")
    public void create() throws Exception {
        String nom = request.getParameter("nom");

        Unite unite = new Unite();
        unite.setNom(nom);

        Connection connection = Base.PsqlConnect();
        unite.create(connection);
        if (connection != null)
            connection.close();

        this.list();
    }
}
