package main.java;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import main.java.connexion.Base;
import main.java.models.production.Recette;
import main.java.models.production.RecetteLigne;
import main.java.models.stock.MatierePremiere;
import main.java.models.vente.Commande;
import main.java.models.vente.DetailCommande;
import main.java.models.vente.PrixProduit;
import main.java.models.vente.Produit;

public class Main {
  public static void main(String[] args) throws Exception {
    Connection connection = Base.PsqlConnect();

    /*gestion de commande */


    Commande commande = new Commande();
    commande.setId(1);
    List<Object> objects = new Commande().read(" where idCommande = 1;",connection);
    for (Object object : objects) {
      commande = (Commande) object;
    }
    DetailCommande[] detailCommandes =  commande.getCommandes(connection);
//    for (DetailCommande detailCommande: detailCommandes) {
//      Produit produit = detailCommande.getProduit(connection);
//      System.out.println(detailCommande.getCommande()+"-"+produit.getNom()+"-"+detailCommande.getQuantite()+"-"+detailCommande.getDate());
//      PrixProduit prixProduit = produit.getPrixProduit(detailCommande.getDate(),connection);
//      System.out.println("price : " + prixProduit.getPrix());
//    }

//    System.out.println("Client n "+commande.getClient()+"  Total Addition: "+ commande.getAddition(connection));

    /*GESTION DE MATIERES PREMIERES */
    for (DetailCommande detailCommande: detailCommandes) {
      Produit produit = detailCommande.getProduit(connection);
      List<Recette> recettes = produit.getRecettes(connection);
      for (Recette recette: recettes) {
         ArrayList<RecetteLigne> recetteLignes =  recette.getRecetteLigne(connection);
         for (RecetteLigne recetteLigne: recetteLignes) {
           System.out.println(recetteLigne.getMatierePremiere(connection).getNom());
         }
      }
    }



    connection.close();
  }
}
