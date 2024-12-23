package main.java.models.finance;

import mg.daoherana.DaoHerana;
import mg.dao.annotation.Column;
import mg.dao.annotation.Table;

@Table(name = "ChiffresAffaires")
public class ChiffreAffaire extends DaoHerana {
  @Column(name = "idChiffresAffaires")
  private int id;

}
