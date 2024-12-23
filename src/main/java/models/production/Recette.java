package main.java.models.production;

import mg.daoherana.DaoHerana;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

@Table(name = "Prod_Recette")
public class Recette extends DaoHerana {
  @Column(name = "idProduit")
  private int produit;

  @Column(name = "idMatierePremiere")
  private int matierePremiere;

  @Column(name = "quantite")
  private double quantite;

  public int getProduit() { return produit; }

  public void setProduit(int produit) { this.produit = produit; }

  public int getMatierePremiere() { return matierePremiere; }

  public void setMatierePremiere(int matierePremiere) { this.matierePremiere = matierePremiere; }

  public double getQuantite() { return quantite; }

  public void setQuantite(double quantite) { this.quantite = quantite; }

}
