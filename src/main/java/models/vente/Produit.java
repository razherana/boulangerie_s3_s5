package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import main.java.models.production.Recette;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.dao.utils.Reflect;

@Table(name = "Vente_Produit")
@BelongsTo(model = Recette.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "recette")
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

  public PrixProduit getPrixProduit(Timestamp date, Connection connection) {
    String t = new Reflect().getTableName(new PrixProduit());
    return Arrays
        .stream((PrixProduit[]) new PrixProduit().query(
            "SELECT * FROM " + t + " WHERE idProduit  = ? and Date_ajout <= ? order by date_ajout desc limit 1",
            new Object[] { getId(), date }, getEagerLoads().toArray(new String[0]), connection))
        .findFirst().orElse(null);
  }

}