
CREATE TABLE Vente_Unite (
    idUnite SERIAL,
    nom VARCHAR(50) NOT NULL,
    PRIMARY KEY (idUnite)
);

CREATE TABLE Vente_Produit (
    idProduit Serial,
    nom VARCHAR(50) NOT NULL,
    PRIMARY KEY (idProduit)
);

CREATE TABLE Stock_matierePremiere (
    idMatierePremiere SERIAL PRIMARY KEY,
    idUnite INT NOT NULL REFERENCES Vente_Unite (idUnite)
    nom VARCHAR(50) NOT NULL,
    quantite DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE Stock_Mouvement_matierePremiere (
    idMouvement Serial,
    status VARCHAR(50) NOT NULL,
    quantite decimal(10, 2),
    date_mouvement TIMESTAMP,
    idMatierePremiere INT NOT NULL,
    PRIMARY KEY (idMouvement),
    FOREIGN KEY (idMatierePremiere) REFERENCES Stock_matierePremiere (idMatierePremiere)
);

CREATE TABLE Prod_Recette (
    idRecette SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    idProduit INT REFERENCES Vente_Produit(idProduit),
    nbrPersonne INT DEFAULT 1
);

CREATE TABLE Prod_Recette_Ligne (
    idRecetteLigne SERIAL PRIMARY KEY
    idRecette INT REFERENCES Prod_Recette (idRecette),
    idMatierePremiere INT REFERENCES Stock_matierePremiere (idMatierePremiere),
    quantite DECIMAL(10, 2) NOT NULL
);

create table Vente_PrixProduit (
    idPrixProduit int primary key,
    idProduit int REFERENCES Vente_Produit (idProduit),
    prix decimal(10, 2),
    date_ajout timestamp
);

CREATE TABLE Stock_Mouvement_produit (
    idMouvement INT,
    status VARCHAR(50) NOT NULL,
    quantite decimal(10, 2),
    date_mouvement TIMESTAMP,
    idProduit INT NOT NULL,
    PRIMARY KEY (idMouvement),
    FOREIGN KEY (idProduit) REFERENCES Vente_Produit (idProduit)
);

CREATE TABLE Vente_client (
    idClient SERIAL PRIMARY KEY,
    nom varchar(256) NOT NULL,
    username varchar(256),
    password varchar(256)
);

create table Vente_commande (
    idCommande SERIAL primary key,
    idClient int references Vente_client (idClient),
    isSaled boolean
);

create table Vente_detailsCommande (
    idDetailsCommande SERIAL primary key,
    idCommande int references Vente_commande (idCommande),
    idProduit int references Vente_Produit (idProduit),
    quantite decimal(10, 2),
    date_ajout TIMESTAMP
);

/*
GESTION DE FINANCE
*/

CREATE TABLE ChiffresAffaires (
    idChiffresAffaires serial primary key,
    mois int,
    annee int,
    valeur decimal(10, 2)
);

/** BABABABABA **/

/*
GESTION DE PERSONNELS
*/

CREATE TABLE Personnal_Poste (
    idPoste SERIAL PRIMARY KEY,
    nom VARCHAR(256) NOT NULL
);

CREATE TABLE Personnal_Employe (
    idEmploye SERIAL PRIMARY KEY,
    nom VARCHAR(256) NOT NULL,
    idPoste int references Personnal_Poste (idPoste),
    taux_horaire decimal(10, 2)
);