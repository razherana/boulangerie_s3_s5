package main.java.models.vente;

import java.sql.Connection;
import java.sql.Timestamp;

import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;
import mg.daoherana.relations.EagerLoad;

@Table(name = "Vente_detailsCommande")
@BelongsTo(model = Commande.class, parentKeyGetter = "getCommande", foreignKeyGetter = "getId", relationName = "commande")
@BelongsTo(model = Produit.class, parentKeyGetter = "getProduit", foreignKeyGetter = "getId", relationName = "produit")
@EagerLoad({"produit"})
public class DetailCommande extends DaoHerana {
  @Column(isPK = true, name = "idDetailsCommande")
  private int id;

  @Column(name = "idCommande")
  private int commande;

  @Column(name = "idProduit")
  private int produit;

  @Column
  private double quantite;

  @Column(name = "date_ajout")
  private Timestamp date;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getCommande() { return commande; }

  public void setCommande(int commande) { this.commande = commande; }

  public int getProduit() { return produit; }

  public void setProduit(int produit) { this.produit = produit; }

  public double getQuantite() { return quantite; }

  public void setQuantite(double quantite) { this.quantite = quantite; }

  public Timestamp getDate() { return date; }

  public void setDate(Timestamp date) { this.date = date; }

  public Commande getCommande(Connection connection) { return belongsTo("commande", connection); }

  public Produit getProduit(Connection connection) { return belongsTo("produit", connection); }

}
