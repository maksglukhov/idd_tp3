DROP TABLE Personne CASCADE CONSTRAINT;
DROP TABLE Livre CASCADE CONSTRAINT;
DROP TABLE Exemplaire CASCADE CONSTRAINT;

DROP SEQUENCE SEQ_Personne;
DROP SEQUENCE SEQ_Livre;
DROP SEQUENCE SEQ_Exemplaire;

CREATE TABLE Personne (
	id number(8,0),
	nom varchar2(32),
	prenom varchar2(32),
	CONSTRAINT pk_Personne PRIMARY KEY(id)
);

-- S?quence ? utiliser pour valuer la colonne id de la table Personne
CREATE SEQUENCE SEQ_Personne START WITH 1;

CREATE TABLE Livre (
	ISBN number(8,0),
	titre varchar2(32),
	CONSTRAINT pk_Livre PRIMARY KEY(ISBN)
);

-- S?quence ? utiliser pour valuer la colonne ISBN de la table Livre
CREATE SEQUENCE SEQ_Livre START WITH 1;

CREATE TABLE Exemplaire (
	id number(8,0),
	prix number(10,2),
	duLivre number(8,0) not null,
	emprunteur number(8,0), 
	CONSTRAINT pk_Exemplaire PRIMARY KEY(id),
	CONSTRAINT fk_emprunteur FOREIGN KEY(emprunteur) REFERENCES Personne(id),
	CONSTRAINT fk_duLivre FOREIGN KEY(duLivre) REFERENCES Livre(ISBN)
);

-- S?quence ? utiliser pour valuer la colonne id de la table Exemplaire
CREATE SEQUENCE SEQ_Exemplaire START WITH 1;

-- DATA

-- Insertion de donn?es dans le mod?le logique
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'La Terre entre nos mains');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'Le mage du Kremlin');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'Le Passager sans visage');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'Tout le bleu du ciel');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'Une vie');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'Billy Summers');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'le hussard');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'le hussard');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'le hussard');
INSERT INTO Livre VALUES (SEQ_Livre.nextval, 'le hussard');

INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Faubert', 'H?l?ne');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Ankaoua', 'Maud');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Taplin', 'Sam');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Cazenove', 'Christophe');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Tesson', 'Sylvain');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Blain', 'Christophe');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Bocquet', 'Jos?-Louis');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Beauvais', 'Sofia');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Gilbert', 'Muriel');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Hoover', 'Colleen');
INSERT INTO Personne VALUES (SEQ_Personne.nextval, 'Bailey', 'Ella');

INSERT INTO Exemplaire VALUES (1, 26.50 , 1, 8);
INSERT INTO Exemplaire VALUES (2, 26.50 , 1, NULL);
INSERT INTO Exemplaire VALUES (3, 26.50 , 1, NULL);
INSERT INTO Exemplaire VALUES (4, 26.50 , 1, NULL);
INSERT INTO Exemplaire VALUES (5, 26.50 , 1, NULL);
INSERT INTO Exemplaire VALUES (6, 24, 2, 8);
INSERT INTO Exemplaire VALUES (7, 24, 2, 9);
INSERT INTO Exemplaire VALUES (8, 24, 2, NULL);
INSERT INTO Exemplaire VALUES (9, 24, 2, NULL);
INSERT INTO Exemplaire VALUES (10, 24.50 , 2, NULL);
INSERT INTO Exemplaire VALUES (11, 22, 3, NULL);
INSERT INTO Exemplaire VALUES (12, 22, 3, NULL);
INSERT INTO Exemplaire VALUES (13, 22, 3, NULL);
INSERT INTO Exemplaire VALUES (14, 23.50, 4, NULL);
INSERT INTO Exemplaire VALUES (15, 32.50, 5, NULL);


drop sequence SEQ_Exemplaire;
create sequence SEQ_Exemplaire start with 15 increment by 1;
select SEQ_Exemplaire.nextval from dual;
select SEQ_Exemplaire.currval from dual;
