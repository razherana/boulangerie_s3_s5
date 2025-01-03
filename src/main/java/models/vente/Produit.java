package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;
import mg.daoherana.relations.HasMany;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;

import main.java.models.production.Recette;
import main.java.models.production.RecetteLigne;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.dao.utils.Reflect;

@Table(name = "Vente_Produit")
@BelongsTo(model = Recette.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "recette")
@HasMany(model = PrixProduit.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "prixProduit")
public class Produit extends DaoHerana {
  @Column(isPK = true, name = "idProduit")
  private int id;

  @Column
  private String nom;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public String getNom() { return nom; }

  public void setNom(String nom) { this.nom = nom; }

  /*
   * Efa reglé, tsy nisy @Table ilay RecetteLigne
   */
  public Recette getRecette(Connection connection) { return belongsTo("recette", connection); }

  public PrixProduit getPrixProduitLazy(Timestamp date, Connection connection) {
    String t = new Reflect().getTableName(new PrixProduit());
    return Arrays
        .stream((PrixProduit[]) new PrixProduit().query(
            "SELECT * FROM " + t + " WHERE idProduit  = ? and Date_ajout <= ? order by date_ajout desc limit 1",
            new Object[] { getId(), date }, getEagerLoads().toArray(new String[0]), connection))
        .findFirst().orElse(null);
  }

  public PrixProduit getPrixProduit(Timestamp date, Connection connection) {
    return Arrays.stream(getPrixProduit(connection)).filter((a) -> a.getDate().before(date) || a.getDate().equals(date))
        .sorted((b, a) -> a.getDate().compareTo(b.getDate())).findFirst().orElse(null);
  }

  public PrixProduit[] getPrixProduit(Connection connection) {
    return hasMany("prixProduit", connection).toArray(new PrixProduit[0]);
  }

  public double getPrixRevient(Timestamp date, Connection connection) {
    double prix = 0;
    for (RecetteLigne recetteLigne : getRecette(connection).getRecetteLigne(connection)) {
      prix += recetteLigne.getQuantite()
          * recetteLigne.getMatierePremiere(connection).getPrix(date, connection).getPrix();
    }
    return prix;
  }
}