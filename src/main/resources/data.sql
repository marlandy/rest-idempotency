-- CARGA DE DATOS INICIALES

-- users
INSERT INTO users (name, age) VALUES ('Pepe', 54);
INSERT INTO users (name, age) VALUES ('Luis', 23);

INSERT INTO orders (id, price, vouncher, status) VALUES ('999992_bd84fe278c6152c821a80ee6038effe6', 38.58, 'AZX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999999_bd84fe278c6152c821a80ee6038effe6', 16.91, 'RMFEV3', 2);

INSERT INTO orders (id, price, vouncher, status) VALUES ('999982_bd84fe278c6152c821a80ee6038effe6', 28.48, 'ACX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999979_bd84fe278c6152c821a80ee6038effe6', 46.31, 'PFFEV3', 2);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999962_bd84fe278c6152c821a80ee6038effe6', 68.28, 'CZX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999959_bd84fe278c6152c821a80ee6038effe6', 76.11, 'RRFEV3', 2);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999942_bd84fe278c6152c821a80ee6038effe6', 88.98, 'BBX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999939_bd84fe278c6152c821a80ee6038effe6', 96.71, 'NUFEV3', 2);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999922_bd84fe278c6152c821a80ee6038effe6', 8.56, 'PZX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('999919_bd84fe278c6152c821a80ee6038effe6', 6.94, 'KKFEV3', 2);

-- equipos
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Real Madrid C.F.', 1902, 1);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('F.C. Barcelona', 1899, 2);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Atlético de Madrid', 1903, 3);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Málaga C.F.', 1948, 4);

-- estadios
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Santiago Bernabeu', 85454, 'Madrid', 5000);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Camp Nou', 99354, 'Barcelona', 5001);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Vicente Calderón', 54851, 'Madrid', 5002);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('La Rosaleda', 28963, 'Málaga', 5003);

-- jugadores
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Cristiano Ronaldo', 28, 'Portugal', 172, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Xabi Alonso', 32, 'España', 12, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Sergio Ramos', 27, 'España', 24, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Andrés Iniesta', 29, 'España', 5, 5001);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Xavi Hernández', 33, 'España', 3, 5001);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Leo Messi', 26, 'Argentina', 16, 5001);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('David Villa', 32, 'España', 16, 5002);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Francisco Portillo', 23, 'España', 2, 5003);


