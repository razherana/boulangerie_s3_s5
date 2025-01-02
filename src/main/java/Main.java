package main.java;
import java.sql.Connection;

import main.java.connexion.Base;
import main.java.models.stock.MatierePremiere;
import main.java.models.stock.MouvMatierePremiere;
import main.java.models.vente.Commande;
import main.java.models.vente.DetailCommande;

public class Main {
  public static void main(String[] args) throws Exception {
    Connection connection = Base.PsqlConnect();
    Commande commande = new Commande();
    commande.setId(1);

    DetailCommande[] detailCommandes =  commande.getCommandes(connection);
    for (DetailCommande detailCommande: detailCommandes) {
      System.out.println(detailCommande.getCommande()+"-"+detailCommande.getProduit()+"-"+detailCommande.getQuantite());

    }
  }
}
