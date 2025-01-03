package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.HasMany;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Table(name = "Vente_commande")
@HasMany(model = DetailCommande.class, parentKeyGetter = "getId", foreignKeyGetter = "getCommande", relationName = "detailcommande")
public class Commande extends DaoHerana {
  @Column(isPK = true, name = "idCommande")
  private int id;

  @Column(name = "idClient")
  private int client;

  @Column(name = "isSaled")
  private boolean saled;

  private double total;

  public double getTotal() { return total; }

  public void setTotal(double total) { this.total = total; }

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getClient() { return client; }

  public void setClient(int client) { this.client = client; }

  public boolean isSaled() { return saled; }

  public void setSaled(boolean saled) { this.saled = saled; }

  public DetailCommande[] getDetailCommandes(Connection conn) {
    return hasMany("detailcommande", conn).toArray(new DetailCommande[0]);
  }

  public double getAddition(Connection conn) throws Exception {
    total = 0;
    Commande example = new Commande();
    example.setMapLoads(Map.ofEntries(Map.entry(Produit.class.getName(), List.of("prixProduit"))));
    for (DetailCommande detailCommande : getDetailCommandes(conn)) {
      total += detailCommande.getQuantite()
          * detailCommande.getProduit(conn).getPrixProduit(detailCommande.getDate(), conn).getPrix();
    }
    return total;
  }

  public double prixRevient(Connection connection) {
    Commande example = new Commande();
    example.setMapLoads(Map.ofEntries(Map.entry(Produit.class.getName(), List.of("recette"))));
    double prix = 0;
    for (DetailCommande detailCommande : example.getDetailCommandes(connection)) {
      prix += detailCommande.getProduit(connection).getPrixRevient(detailCommande.getDate(), connection)
          * detailCommande.getQuantite();
    }
    return prix;
  }
}
