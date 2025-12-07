-- ============================================
-- MATERIALIZED VIEWS POUR CABINET PLUS
-- ============================================

-- 1. STATISTIQUES MENSUELLES DES CONSULTATIONS
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS stats_consultations_mensuelles AS
SELECT
    DATE_TRUNC('month', date_consultation) AS mois,
    COUNT(*) AS nombre_consultations,
    SUM(prix) AS revenus_total,
    AVG(prix) AS prix_moyen,
    COUNT(DISTINCT patient_id) AS nombre_patients_uniques
FROM consultation
WHERE date_consultation IS NOT NULL
GROUP BY DATE_TRUNC('month', date_consultation)
ORDER BY mois DESC;

-- Index pour accélérer les requêtes
CREATE INDEX IF NOT EXISTS idx_stats_consultations_mois
ON stats_consultations_mensuelles(mois);

COMMENT ON MATERIALIZED VIEW stats_consultations_mensuelles IS
'Statistiques mensuelles : consultations, revenus, prix moyen';


-- 2. STATISTIQUES DES RENDEZ-VOUS PAR STATUT
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS stats_rendezvous_statut AS
SELECT
    statut,
    COUNT(*) AS nombre_rdv,
    COUNT(DISTINCT patient_id) AS nombre_patients,
    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER(), 2) AS pourcentage
FROM rendezvous
GROUP BY statut;

COMMENT ON MATERIALIZED VIEW stats_rendezvous_statut IS
'Répartition des rendez-vous par statut (PREVU, ANNULE, TERMINE)';


-- 3. TOP 10 DES PATIENTS LES PLUS ACTIFS
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS top_patients_actifs AS
SELECT
    p.id,
    p.nom,
    p.prenom,
    p.numero_dossier,
    COUNT(DISTINCT c.id) AS nombre_consultations,
    COUNT(DISTINCT r.id) AS nombre_rdv,
    COALESCE(SUM(c.prix), 0) AS total_depenses,
    MAX(c.date_consultation) AS derniere_consultation
FROM patient p
LEFT JOIN consultation c ON p.id = c.patient_id
LEFT JOIN rendezvous r ON p.id = r.patient_id
GROUP BY p.id, p.nom, p.prenom, p.numero_dossier
ORDER BY nombre_consultations DESC, total_depenses DESC
LIMIT 10;

COMMENT ON MATERIALIZED VIEW top_patients_actifs IS
'Top 10 des patients avec le plus de consultations';


-- 4. STATISTIQUES HEBDOMADAIRES DES RDV
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS stats_rdv_hebdomadaires AS
SELECT
    DATE_TRUNC('week', date_rdv) AS semaine,
    COUNT(*) AS total_rdv,
    SUM(CASE WHEN statut = 'PREVU' THEN 1 ELSE 0 END) AS rdv_prevus,
    SUM(CASE WHEN statut = 'TERMINE' THEN 1 ELSE 0 END) AS rdv_termines,
    SUM(CASE WHEN statut = 'ANNULE' THEN 1 ELSE 0 END) AS rdv_annules,
    ROUND(
        SUM(CASE WHEN statut = 'ANNULE' THEN 1 ELSE 0 END) * 100.0 /
        NULLIF(COUNT(*), 0),
        2
    ) AS taux_annulation
FROM rendezvous
GROUP BY DATE_TRUNC('week', date_rdv)
ORDER BY semaine DESC;

CREATE INDEX IF NOT EXISTS idx_stats_rdv_semaine
ON stats_rdv_hebdomadaires(semaine);

COMMENT ON MATERIALIZED VIEW stats_rdv_hebdomadaires IS
'Statistiques hebdomadaires des rendez-vous avec taux d''annulation';


-- 5. RÉPARTITION DES MOTIFS DE CONSULTATION
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS stats_motifs_consultation AS
SELECT
    r.motif,
    COUNT(*) AS nombre_occurrences,
    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER(), 2) AS pourcentage
FROM rendezvous r
WHERE r.motif IS NOT NULL AND r.motif != ''
GROUP BY r.motif
ORDER BY nombre_occurrences DESC
LIMIT 20;

COMMENT ON MATERIALIZED VIEW stats_motifs_consultation IS
'Top 20 des motifs de consultation les plus fréquents';


-- 6. DASHBOARD MÉDECIN (Vue Complète)
-- ============================================
CREATE MATERIALIZED VIEW IF NOT EXISTS dashboard_medecin AS
SELECT
    'total_patients' AS metrique,
    COUNT(*)::TEXT AS valeur,
    'Nombre total de patients enregistrés' AS description
FROM patient

UNION ALL

SELECT
    'total_consultations' AS metrique,
    COUNT(*)::TEXT AS valeur,
    'Nombre total de consultations' AS description
FROM consultation

UNION ALL

SELECT
    'total_rdv' AS metrique,
    COUNT(*)::TEXT AS valeur,
    'Nombre total de rendez-vous' AS description
FROM rendezvous

UNION ALL

SELECT
    'rdv_aujourd_hui' AS metrique,
    COUNT(*)::TEXT AS valeur,
    'Rendez-vous prévus aujourd''hui' AS description
FROM rendezvous
WHERE DATE(date_rdv) = CURRENT_DATE AND statut = 'PREVU'

UNION ALL

SELECT
    'revenus_mois' AS metrique,
    COALESCE(SUM(prix), 0)::TEXT AS valeur,
    'Revenus du mois en cours' AS description
FROM consultation
WHERE DATE_TRUNC('month', date_consultation) = DATE_TRUNC('month', CURRENT_DATE)

UNION ALL

SELECT
    'prix_moyen' AS metrique,
    ROUND(AVG(prix), 2)::TEXT AS valeur,
    'Prix moyen d''une consultation' AS description
FROM consultation
WHERE prix > 0;

COMMENT ON MATERIALIZED VIEW dashboard_medecin IS
'Métriques principales pour le tableau de bord médecin';


-- ============================================
-- FONCTION POUR RAFRAÎCHIR TOUTES LES VUES
-- ============================================
CREATE OR REPLACE FUNCTION refresh_all_materialized_views()
RETURNS void AS $$
BEGIN
    REFRESH MATERIALIZED VIEW stats_consultations_mensuelles;
    REFRESH MATERIALIZED VIEW stats_rendezvous_statut;
    REFRESH MATERIALIZED VIEW top_patients_actifs;
    REFRESH MATERIALIZED VIEW stats_rdv_hebdomadaires;
    REFRESH MATERIALIZED VIEW stats_motifs_consultation;
    REFRESH MATERIALIZED VIEW dashboard_medecin;

    RAISE NOTICE 'Toutes les vues matérialisées ont été rafraîchies';
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION refresh_all_materialized_views() IS
'Rafraîchit toutes les vues matérialisées du système';