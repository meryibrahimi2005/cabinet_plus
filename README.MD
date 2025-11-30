pour executer base de donne sur docker :
**Docker Commands**:
```bash
# Start PostgreSQL from src/docker/
cd src/docker && docker-compose up -d

# Access database
docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db



pour cree une image dans docker posgress.yaml 

docker compose -f posgress.yaml up -d  

problem : le conteneur cree mais n est pas demare 

docker start caboinet_postress

les tableaux sur cabinet_db : 

CREATE TABLE patient (
id SERIAL PRIMARY KEY,
nom VARCHAR(100),
prenom VARCHAR(100),
username VARCHAR(50) UNIQUE,
password VARCHAR(50),
date_naissance DATE,
telephone VARCHAR(20),
email VARCHAR(100),
adresse TEXT,
numero_dossier VARCHAR(20) UNIQUE
);

CREATE TABLE medecin (
id SERIAL PRIMARY KEY,
nom VARCHAR(100),
prenom VARCHAR(100),
username VARCHAR(50) UNIQUE,
password VARCHAR(50)
);

CREATE TABLE rendezvous (
id SERIAL PRIMARY KEY,
date_rdv DATE,
heure TIME,
motif TEXT,
patient_id INTEGER REFERENCES patient(id),
statut VARCHAR(20)
);

CREATE TABLE consultation (
id SERIAL PRIMARY KEY,
patient_id INTEGER REFERENCES patient(id),
numero_dossier VARCHAR(20),
date_consultation DATE,
prix DOUBLE PRECISION,
note TEXT
);


connexion medecin : 

login : medecin 
pass : 1234

pour connecter la base : docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db
pour cree utilisatuer par default : 
docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db -c "INSERT INTO medecin (nom, prenom, username, password) VALUES ('Médecin', 'Test', 'medecin', '1234');"