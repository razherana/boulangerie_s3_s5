package main.java;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import main.java.connexion.Base;
import main.java.models.production.RecetteLigne;
import main.java.models.vente.Commande;
import main.java.models.vente.DetailCommande;
import main.java.models.vente.PrixProduit;
import main.java.models.vente.Produit;

public class Main {
  public static void main(String[] args) throws Exception {
    Connection connection = Base.PsqlConnect();

    /* gestion de commande */

    Commande commande = new Commande().find(1, null, connection);
    commande.setMapLoads(Map.of(DetailCommande.class.getName(), List.of("produit")));
    DetailCommande[] detailCommandes = commande.getDetailCommandes(connection);
    for (DetailCommande detailCommande : detailCommandes) {
      Produit produit = detailCommande.getProduit(connection);
      System.out.println(detailCommande.getCommande() + "-" + produit.getNom() + "-" + detailCommande.getQuantite()
          + "-" + detailCommande.getDate());
      PrixProduit prixProduit = produit.getPrixProduit(detailCommande.getDate(), connection);
      System.out.println("price : " + prixProduit.getPrix());
    }

    // System.out.println("Client n "+commande.getClient()+" Total Addition: "+
    // commande.getAddition(connection));

    /* GESTION DE MATIERES PREMIERES */
    // for (DetailCommande detailCommande: detailCommandes) {
    // Produit produit = detailCommande.getProduit(connection);
    // List<Recette> recettes = produit.getRecettes(connection);
    // for (Recette recette: recettes) {
    // ArrayList<RecetteLigne> recetteLignes = recette.getRecetteLigne(connection);
    // for (RecetteLigne recetteLigne: recetteLignes) {
    // System.out.println(recetteLigne.getMatierePremiere(connection).getNom());
    // }
    // }
    // }

    Produit produit = new Produit().find(1, new Produit().getEagerLoads().toArray(new String[0]), connection);

    for (RecetteLigne recetteLigne : produit.getRecette(connection).getRecetteLigne(connection)) {
      System.out.println(recetteLigne.getMatierePremiere(connection).getNom() + " - " + recetteLigne.getQuantite());
    }

    PrixProduit prixProduit = produit.getPrixProduit(Timestamp.valueOf("2025-01-01 12:00:00"), connection);

    System.out.println(prixProduit.getPrix());

    connection.close();
  }
}
