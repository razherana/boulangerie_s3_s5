package main.java.models.stock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

@Table(name = "Stock_Mouvement_matierePremiere")
@BelongsTo(model = MatierePremiere.class, parentKeyGetter = "getMatierePremiere", foreignKeyGetter = "getId", relationName = "matierePremiere")
public class MouvMatierePremiere extends DaoHerana {

  @Column(isPK = true, name = "idMouvement")
  private int id;

  /** Entrée - Sortie */
  @Column(name = "status")
  private String status;

  @Column
  private double quantite;

  @Column(name = "date_mouvement")
  private Timestamp date;

  @Column(name = "idMatierePremiere")
  private int matierePremiere;

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public String getStatus() { return status; }

  public int getStatusInt() { return mapStatusInt.getOrDefault(getStatus(), -1); }

  private static Map<String, Integer> mapStatusInt = Map.of("Entrée", 1, "Sortie", -1);

  public void setStatus(String status) { this.status = status; }

  public double getQuantite() { return quantite; }

  public void setQuantite(double quantite) { this.quantite = quantite; }

  public Timestamp getDate() { return date; }

  public void setDate(Timestamp date) { this.date = date; }

  public int getMatierePremiere() { return matierePremiere; }

  public void setMatierePremiere(int matierePremiere) { this.matierePremiere = matierePremiere; }

  public MatierePremiere getMatierePremiere(Connection connection) { return belongsTo("matierePremiere", connection); }

  public void insertMouv(int idMatierePremiere, double quantite, Connection connection) throws SQLException {
    MatierePremiere matierePremiere = null;

    if ((matierePremiere = new MatierePremiere().find(idMatierePremiere, null, connection)) == null)
      throw new RuntimeException("Matiere premiere with id %i not found".formatted(idMatierePremiere));

    Map<String, Integer> map = Map.of("Entrée", 1, "Sortie", -1);
    Map<Boolean, String> map2 = Map.of(true, "Entrée", false, "Sortie");

    double res = Arrays.stream(matierePremiere.getMouvMatierePremieres(connection))
        .mapToDouble((e) -> e.getQuantite() * map.getOrDefault(e.getStatus(), 1)).sum();
    res += quantite;

    try {
      connection.setAutoCommit(false);
      connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

      MouvMatierePremiere mouvMatierePremiere = new MouvMatierePremiere();

      mouvMatierePremiere.setMatierePremiere(idMatierePremiere);
      mouvMatierePremiere.setQuantite(quantite);
      mouvMatierePremiere.setStatus(map2.get(quantite >= 0));
      mouvMatierePremiere.setDate(Timestamp.valueOf(LocalDateTime.now()));

      mouvMatierePremiere.create(connection);

      matierePremiere.setQuantite(res);
      matierePremiere.update("", "", connection);

      connection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      connection.rollback();

      throw new SQLException(e);
    }
  }
}
