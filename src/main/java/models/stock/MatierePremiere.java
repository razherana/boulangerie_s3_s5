package main.java.models.stock;

import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.daoherana.DaoHerana;

@Table(name = "Stock_matierePremiere")
public class MatierePremiere extends DaoHerana {
  @Column(isPK = true, name = "idMatierePremiere")
  private int id;

  @Column(name = "quantite")
  private double quantite;

  @Column(name = "nom")
  private String nom;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public double getQuantite() { return quantite; }

  public void setQuantite(double quantite) { this.quantite = quantite; }

  public String getNom() { return nom; }

  public void setNom(String nom) { this.nom = nom; }

}
