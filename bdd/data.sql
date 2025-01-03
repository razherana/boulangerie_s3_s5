-- Insertion dans la table Vente_Unite
INSERT INTO Vente_Unite (nom) VALUES 
('Kilogramme'),
('Litre'),
('Pièce'),
('Grammes');

-- Insertion dans la table Vente_Produit
INSERT INTO Vente_Produit (nom) VALUES 
('Pain'),
('Croissant'),
('Baguette'),
('Brioche');

-- Insertion dans la table Stock_matierePremiere
INSERT INTO Stock_matierePremiere (idUnite, nom, quantite) VALUES 
(1, 'Farine', 5000), 
(2, 'Lait', 100), 
(3, 'Oeuf', 200),
(4, 'Sucre', 1000);

-- Insertion dans la table Stock_Mouvement_matierePremiere
INSERT INTO Stock_Mouvement_matierePremiere (status, quantite, date_mouvement, idMatierePremiere) VALUES 
('Entrée', 1000, '2025-01-01 10:00:00', 1), -- Ajout de 1000 kg de farine
('Sortie', 500, '2025-01-02 14:00:00', 1), -- Retrait de 500 kg de farine
('Entrée', 50, '2025-01-01 11:00:00', 2); -- Ajout de 50 litres de lait

-- Insertion dans la table Prod_Recette
INSERT INTO Prod_Recette (nom, idProduit, nbrPersonne) VALUES 
('Recette Pain', 1, 10),
('Recette Croissant', 2, 6);

-- Insertion dans la table Prod_Recette_Ligne
INSERT INTO Prod_Recette_Ligne (idRecette, idMatierePremiere, quantite) VALUES 
(1, 1, 500),
(1, 2, 20),
(1, 3, 5),
(2, 1, 300),
(2, 2, 30),
(2, 3, 10);

-- Insertion dans la table Vente_PrixProduit (Prix en Ariary)
INSERT INTO Vente_PrixProduit (idPrixProduit, idProduit, prix, date_ajout) VALUES 
(1, 1, 15000, '2025-01-01 10:00:00'), -- Pain
(2, 2, 20000, '2025-01-01 10:00:00'), -- Croissant
(3, 3, 12000, '2025-01-01 10:00:00'), -- Baguette
(4, 4, 25000, '2025-01-01 10:00:00'); -- Brioche

-- Insertion dans la table Stock_Mouvement_produit
INSERT INTO Stock_Mouvement_produit (idMouvement, status, quantite, date_mouvement, idProduit) VALUES 
(1, 'Entrée', 50, '2025-01-01 12:00:00', 1), -- Production de 50 pains
(2, 'Sortie', 20, '2025-01-01 14:00:00', 1), -- Vente de 20 pains
(3, 'Entrée', 30, '2025-01-02 08:00:00', 2); -- Production de 30 croissants

-- Insertion dans la table Vente_client
INSERT INTO Vente_client (nom, username, password) VALUES 
('Rakoto', 'rakoto_user', 'password123'),
('Rasoava', 'rasoava_user', 'password456');

-- Insertion dans la table Vente_commande
INSERT INTO Vente_commande (idClient, isSaled) VALUES 
(1, TRUE),
(2, FALSE);

-- Insertion dans la table Vente_detailsCommande
INSERT INTO Vente_detailsCommande (idCommande, idProduit, quantite, date_ajout) VALUES 
(1, 1, 10, '2025-01-01 15:00:00'),
(1, 2, 5, '2025-01-01 15:30:00'),
(2, 3, 3, '2025-01-02 09:00:00');

-- Insertion dans la table ChiffresAffaires
INSERT INTO ChiffresAffaires (mois, annee, valeur) VALUES 
(1, 2025, 50000),
(2, 2025, 75000);

-- Insertion dans la table Personnal_Poste
INSERT INTO Personnal_Poste (nom) VALUES 
('Boulanger'),
('Pâtissier'),
('Caissier');

-- Insertion dans la table Personnal_Employe
INSERT INTO Personnal_Employe (nom, idPoste, taux_horaire) VALUES 
('Andrianina', 1, 5000),
('Randria', 2, 5500),
('Rakoto', 3, 4000);

INSERT INTO stock_PrixMatierePremiere (idPrixMatierePremiere, idMatiere, prix, date_ajout) VALUES 
(1, 1, 1000, '2024-12-20 09:00:00'), -- Farine : 1000 Ariary/kg
(2, 2, 3000, '2024-12-21 10:30:00'), -- Lait : 3000 Ariary/litre
(3, 3, 500, '2024-12-22 11:00:00'), -- Œuf : 500 Ariary/pièce
(4, 4, 2000, '2024-12-23 14:45:00'); -- Sucre : 2000 Ariary/kg


-- Ajout de nouveaux prix après les commandes
INSERT INTO stock_PrixMatierePremiere (idPrixMatierePremiere, idMatiere, prix, date_ajout) VALUES 
(5, 1, 1100, '2025-01-01 16:00:00'), -- Farine : Augmentation à 1100 Ariary/kg après les commandes
(6, 2, 3100, '2025-01-01 17:00:00'), -- Lait : Augmentation à 3100 Ariary/litre après les commandes
(7, 3, 450, '2025-01-02 10:00:00'), -- Œuf : Baisse à 450 Ariary/pièce après les commandes
(8, 4, 2100, '2025-01-02 12:00:00'); -- Sucre : Augmentation à 2100 Ariary/kg après les commandes
