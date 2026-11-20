
CREATE DATABASE IF NOT EXISTS dropradar
       CHARACTER SET utf8mb4;

USE dropradar;

CREATE TABLE IF NOT EXISTS drops (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    category VARCHAR(60) NOT NULL,
    release_date DATE NOT NULL
);
INSERT INTO drops (name, category, release_date)
SELECT 'Midnatssignaler', 'Musik', CURRENT_DATE + INTERVAL 3 DAY
WHERE NOT EXISTS (SELECT 1 FROM drops WHERE name = 'Midnatssignaler');
