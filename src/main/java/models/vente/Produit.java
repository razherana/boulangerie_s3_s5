package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;

import java.sql.Connection;

import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

@Table(name = "Vente_Produit")
public class Produit extends DaoHerana {
  @Column(isPK = true, name = "idProduit")
  private int id;

  @Column
  private String nom;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public String getNom() { return nom; }

  public void setNom(String nom) { this.nom = nom; }
}