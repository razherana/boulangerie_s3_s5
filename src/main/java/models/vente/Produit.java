package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.HasMany;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import main.java.models.production.Recette;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

@Table(name = "Vente_Produit")
@HasMany(model = Recette.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "recette")
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
* misy probleme kely ito methode ito :
*  Cannot invoke "mg.dao.annotation.Table.name()" because "colonne" is null
* tokony verifier na kely
* */
  public List<Recette> getRecettes(Connection connection) { return hasMany("recette", connection); }

  public Optional<Recette> getRecette(String nomRecette, Connection connection) {
    return getRecettes(connection).stream().filter((e) -> e.getNom().equals(nomRecette)).findAny();
  }

  public PrixProduit getPrixProduit(Timestamp date , Connection connection) {
      PrixProduit prix = new PrixProduit();
      List<Object>  list = prix.read("WHERE idProduit  =" + getId() +" and Date_ajout <= '"+ date+"' order by date_ajout desc limit 1", connection);
        for (Object obj : list) {
          prix  = (PrixProduit) obj;
        }
      return prix;
  }

}