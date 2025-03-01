-- Créer la table utilisateur si elle n'existe pas
CREATE TABLE IF NOT EXISTS utilisateur (
    id SERIAL PRIMARY KEY,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    username TEXT NOT NULL
);

-- Insérer l'utilisateur admin s'il n'existe pas déjà
INSERT INTO utilisateur (id, password, role, username)
VALUES (1, '$2a$10$qK39w9NddL/WJUv.kozqvOEl1fvDAeRjtcLmrlaf24DjjYW9JlKrC', 'Admin', 'toto')
ON CONFLICT (id) DO NOTHING;
