package main.java.models.stock;

import java.sql.Timestamp;

import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;

@Table(name = "stock_PrixMatierePremiere")
@BelongsTo(model = MatierePremiere.class, parentKeyGetter = "getMatierePremiere", foreignKeyGetter = "getId", relationName = "matierePremiere")
public class PrixMatierePremiere extends DaoHerana {
  @Column(name = "idPrixMatierePremiere", isPK = true)
  private int id;

  @Column(name = "idMatiere")
  private int matierePremiere;

  @Column
  private double prix;

  @Column(name = "date_ajout")
  private Timestamp date;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getMatierePremiere() { return matierePremiere; }

  public void setMatierePremiere(int matierePremiere) { this.matierePremiere = matierePremiere; }

  public double getPrix() { return prix; }

  public void setPrix(double prix) { this.prix = prix; }

  public Timestamp getDate() { return date; }

  public void setDate(Timestamp date) { this.date = date; }
  
}
