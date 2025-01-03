package main.java.models.vente;

import main.java.connexion.Base;
import mg.daoherana.DaoHerana;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

@Table(name = "Vente_commande")
public class Commande extends DaoHerana {
  @Column(isPK = true, name = "idCommande")
  private int id;

  @Column(name = "idClient")
  private int client;

  @Column(name = "isSaled")
  private boolean saled;

  private double total;

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getClient() { return client; }

  public void setClient(int client) { this.client = client; }

  public boolean isSaled() { return saled; }

  public void setSaled(boolean saled) { this.saled = saled; }

  public DetailCommande[] getCommandes(Connection conn) throws Exception {
    int checkConn = 0;
    if (conn == null) {
        conn  = Base.PsqlConnect();
        checkConn = 1;
    }
    List<DetailCommande> commandes = new ArrayList<>();
    String sql = "select * from Vente_detailscommande where idCommande = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, id);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
      DetailCommande commande  = new DetailCommande();
      commande.setId(rs.getInt("idDetailsCommande"));
      commande.setProduit(rs.getInt("idProduit"));
      commande.setCommande(this.getId());
      commande.setQuantite( rs.getDouble("quantite"));
      commande.setDate(rs.getTimestamp("date_ajout"));
      commandes.add(commande);
    }

    if (checkConn == 1) {
      conn.close();
    }

    return commandes.toArray(new DetailCommande[]{});
  }
  public Commande findById(Connection conn) throws Exception {
    String sql = "select * from vente_commande where idcommande = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, this.getId());
    ResultSet rs = pstmt.executeQuery();
    if (rs.next()) {
      Commande commande = new Commande();
      commande.setId(rs.getInt("idDetailsCommande"));
      commande.setClient(rs.getInt("idclient"));
      commande.setSaled(rs.getBoolean("isSaled"));
      return commande;
    }
    return null;
  }

  public double getAddition (Connection conn) throws Exception {
    total = 0;
    if(getCommandes(conn)== null){
      return total;
    }
    for (DetailCommande detailCommande: getCommandes(conn)) {
      total += detailCommande.getQuantite()* detailCommande.getProduit(conn).getPrixProduit(detailCommande.getDate(),conn).getPrix();
    }
    return total;
  }
}
