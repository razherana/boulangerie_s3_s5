package main.java.models.stock;

import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.daoherana.DaoHerana;
import mg.daoherana.relations.*;
import main.java.models.vente.Unite;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

@Table(name = "Stock_matierePremiere")
@BelongsTo(model = Unite.class, parentKeyGetter = "getUnite", foreignKeyGetter = "getId", relationName = "unite")
@HasMany(model = PrixMatierePremiere.class, parentKeyGetter = "getId", foreignKeyGetter = "getMatierePremiere", relationName = "prixMatierePremiere")
@HasMany(model = MouvMatierePremiere.class, parentKeyGetter = "getId", foreignKeyGetter = "getMatierePremiere", relationName = "mouvMatierePremiere")
@EagerLoad({ "prixMatierePremiere" })
public class MatierePremiere extends DaoHerana {
  @Column(isPK = true, name = "idMatierePremiere")
  private int id;

  @Column(name = "quantite")
  private double quantite;

  @Column(name = "nom")
  private String nom;

  @Column(name = "idUnite")
  private int unite;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getUnite() { return unite; }

  public Unite getUnite(Connection connection) { return belongsTo("unite", connection); }

  public void setUnite(int unite) { this.unite = unite; }

  public double getQuantite() { return quantite; }

  public void setQuantite(double quantite) { this.quantite = quantite; }

  public String getNom() { return nom; }

  public void setNom(String nom) { this.nom = nom; }

  public ArrayList<PrixMatierePremiere> getAllPrixMatierePremieres(Connection connection) {
    return hasMany("prixMatierePremiere", connection);
  }

  public PrixMatierePremiere getPrix(Connection connection) {
    PrixMatierePremiere none = new PrixMatierePremiere();
    none.setPrix(0);
    return getAllPrixMatierePremieres(connection).stream().sorted((b, a) -> a.getDate().compareTo(b.getDate()))
        .findFirst().orElse(none);
  }

  public PrixMatierePremiere getPrix(Timestamp date, Connection connection) {
    PrixMatierePremiere none = new PrixMatierePremiere();
    none.setPrix(0);
    return getAllPrixMatierePremieres(connection).stream()
        .filter((a) -> a.getDate().before(date) || a.getDate().equals(date))
        .sorted((b, a) -> a.getDate().compareTo(b.getDate())).findFirst().orElse(none);
  }

  public MouvMatierePremiere[] getMouvMatierePremieres(Connection connection) {
    return hasMany("mouvMatierePremiere", connection).toArray(new MouvMatierePremiere[0]);
  }
}
