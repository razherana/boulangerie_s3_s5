package main.java.models.stock;

import main.java.models.vente.Unite;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;
import mg.daoherana.DaoHerana;
import mg.daoherana.relations.BelongsTo;

import java.sql.Connection;
import java.sql.Timestamp;

@Table(name = "Stock_Prixmatierepremiere")
@BelongsTo(model = MatierePremiere.class, parentKeyGetter = "getMatiere", foreignKeyGetter = "getId", relationName = "matierepremiere")
public class PrixMatierePremiere  extends DaoHerana {
    @Column(isPK = true,name = "idPrixMatierePremiere")
    private int idPrixMatierePremiere;
    @Column(name = "idMatiere")
    private int matiere;
    @Column(name = "prix")
    private double prix;
    @Column(name = "date_ajout")
    private Timestamp dateAjout;

    public PrixMatierePremiere() {
    }

    public int getIdPrixMatierePremiere() {
        return idPrixMatierePremiere;
    }

    public void setIdPrixMatierePremiere(int idPrixMatierePremiere) {
        this.idPrixMatierePremiere = idPrixMatierePremiere;
    }

    public int getMatiere() {
        return matiere;
    }

    public void setMatiere(int matiere) {
        this.matiere = matiere;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Timestamp getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Timestamp dateAjout) {
        this.dateAjout = dateAjout;
    }

    public MatierePremiere getMatiere(Connection connection) { return belongsTo("matierepremiere", connection); }

}
