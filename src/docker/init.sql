-- Cabinet Plus Database Schema and Initial Data
-- PostgreSQL initialization script

-- Create database and tables
CREATE TABLE IF NOT EXISTS medecin (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS patient (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    date_naissance DATE,
    telephone VARCHAR(20),
    email VARCHAR(100),
    adresse VARCHAR(255),
    numero_dossier VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS rendezvous (
    id SERIAL PRIMARY KEY,
    date_rdv TIMESTAMP NOT NULL,
    motif VARCHAR(255),
    patient_id BIGINT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
    statut VARCHAR(50) DEFAULT 'PREVU'
);

CREATE TABLE IF NOT EXISTS consultation (
    id SERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
    numero_dossier VARCHAR(50),
    date_consultation TIMESTAMP,
    prix DECIMAL(10, 2),
    note TEXT
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_patient_username ON patient(username);
CREATE INDEX IF NOT EXISTS idx_medecin_username ON medecin(username);
CREATE INDEX IF NOT EXISTS idx_rendezvous_patient ON rendezvous(patient_id);
CREATE INDEX IF NOT EXISTS idx_rendezvous_date ON rendezvous(date_rdv);
CREATE INDEX IF NOT EXISTS idx_consultation_patient ON consultation(patient_id);


INSERT INTO medecin (nom, prenom, username, password) 
VALUES ('CABINET', 'Médecin', 'medecin', '1234')
ON CONFLICT (username) DO NOTHING;

INSERT INTO patient (nom, prenom, username, password, date_naissance, telephone, email, adresse, numero_dossier)
VALUES ('patient', 'patient', 'patient', '1234', '1990-05-15', '06-12-34-56-78', 'pierre.martin@email.com', '123 Rue de la Paix', 'DOS-001')
ON CONFLICT (username) DO NOTHING;
