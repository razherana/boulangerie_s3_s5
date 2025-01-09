package main.java.models.vente;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;
import mg.daoherana.relations.HasMany;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.java.models.production.Recette;
import main.java.models.production.RecetteLigne;
import main.java.models.stock.MouvMatierePremiere;
import main.java.models.stock.MouvProduit;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.dao.utils.Reflect;

@Table(name = "Vente_Produit")
@BelongsTo(model = Recette.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "recette")
@HasMany(model = PrixProduit.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "prixProduit")
@HasMany(model = MouvProduit.class, parentKeyGetter = "getId", foreignKeyGetter = "getProduit", relationName = "mouvProduit")
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

  // Here start jan 9

  public static class MissingStockException extends Exception {
    public MissingStockException(String message) { super(message); }
  }

  public ArrayList<MouvProduit> getMouvProduits(Connection connection) { return hasMany("mouvProduit", connection); }

  private static Map<Boolean, Integer> mapBoolean = Map.of(true, 1, false, -1);

  /**
   * Not lazy if added eagerLoad "mouvProduit"
   */
  public double getCurrentQuantite(Connection connection) {
    return getMouvProduits(connection).stream()
        .mapToDouble((e) -> e.getQuantite() * mapBoolean.getOrDefault(e.getStatusBoolean(), 1)).sum();
  }

  /**
   * If can produce some quantity of produit - 0 : false - 1 : true, no need to
   * create - 2 : true but need to produit Needs eagerLoad : ["mouvProduit",
   * "recette"]
   */
  public int canCreate(double quantite, Connection connection) {
    double currentQuantite = getCurrentQuantite(connection);

    if (currentQuantite >= quantite)
      return 1;

    int creatableProduct = getRecette(connection).getProduitDispo(connection);

    if (currentQuantite + creatableProduct >= quantite)
      return 2;

    return 0;
  }

  void createProduit0(int quantite, Connection connection) {
    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    for (var recetteLigne : getRecette(connection).getRecetteLigne(connection)) {
      MouvMatierePremiere mouvMatierePremiere = new MouvMatierePremiere();
      mouvMatierePremiere.setDate(timestamp);
      mouvMatierePremiere.setMatierePremiere(recetteLigne.getMatierePremiere());
      mouvMatierePremiere.setQuantite(recetteLigne.getQuantite() * quantite);
      mouvMatierePremiere.setStatus("Sortie");
      mouvMatierePremiere.create(connection);
    }

    MouvProduit mouvProduit = new MouvProduit();
    mouvProduit.setProduit(getId());
    mouvProduit.setDate(timestamp);
    mouvProduit.setQuantite(quantite);
    mouvProduit.setStatus("Entrée");
    mouvProduit.create(connection);
  }

  void removeProduit0(int quantite, Connection connection) {
    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    MouvProduit mouvProduit = new MouvProduit();
    mouvProduit.setProduit(getId());
    mouvProduit.setDate(timestamp);
    mouvProduit.setQuantite(quantite);
    mouvProduit.setStatus("Sortie");
    mouvProduit.create(connection);
  }

  public void buyProduit(int quantite, Connection connection) throws MissingStockException, SQLException {
    int canCreate = canCreate(quantite, connection);
    switch (canCreate) {
    case 2:
      System.err.println("[Info] : Missing produit but enough MatierePremiere...");
      int toCreate = getRecette(connection).getProduitDispo(connection);
      createProduit0(toCreate, connection);
    case 1:
      removeProduit0(quantite, connection);
      break;
    case 0:
      throw new MissingStockException("Stock de produit et stock de matiere premiere manquante");
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Produit other = (Produit) obj;
    if (id != other.id)
      return false;
    return true;
  }

  // Here ends Jan 9

  public double getBenefice(Timestamp date, Connection connection) {
    return getPrixProduit(date, connection).getPrix() - getPrixRevient(date, connection);
  }
}