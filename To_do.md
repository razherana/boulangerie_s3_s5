## date : 03  janvier 2025
     - test :
        (ok)-  Class : produit.
        (ok)- methode : getTecettes(connection)
                -> erreur :  Cannot invoke "mg.dao.annotation.Table.name()" because "colonne" is null
        -
     -  a faire :
        (ok) - verification DAO : rectification erreur.
        - creation de toutes relations entre les entites via foreing key
             - utilisation hasmany , belongsto
             - entites a relier :
                - Produit , PrixProduit , mouvProduit , recettes , recetteLigne, matiere premieres , mouvMatierePremeres
                commande , client , details Commande
        (ok) - calcul du prix de revient de chaque produit.
        (ok) - calcul du prix de revient dune commande
        (ok) - calcul du benefice par produit
        (ok) - calcul du benefice par commande.
        - verification du  reste de matiere premiere avant chaque commande
        - verification du  reste de produit avant chaque commande
        - Affichage          :
            - insertion stock produit et matiere premiere
            - insertion de nouveau produit ,  recette ,  matiere premiere , prix produit
            - prise de commande , details commande ,  client ,
            - charte de chiffre d affaires
            - tableau mouvement de stock matiere premiere et produits
