-- INITIAL DATA LOAD FOR TESTING PURPOSES

-- users
INSERT INTO users (name, age) VALUES ('Test user', 33);
INSERT INTO users (name, age) VALUES ('Other user', 13);

-- orders
INSERT INTO orders (id, price, vouncher, status) VALUES ('17953683_bd84fe278c6152c821a80ee6038effe6', 38.58, 'AZX33F', 1);
INSERT INTO orders (id, price, vouncher, status) VALUES ('17953690_bd84fe278c6152c821a80ee6038effe6', 16.91, 'RMFEV3', 2);

-- equipos
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Real Madrid C.F.', 1902, 1);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('F.C. Barcelona', 1899, 2);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Atlético de Madrid', 1903, 3);
INSERT INTO teams (name, foundation_year, ranking_position) VALUES ('Málaga C.F.', 1948, 4);

-- estadios
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Santiago Bernabeu', 85454, 'Madrid', 5000);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Camp Nou', 99354, 'Barcelona', 5001);
INSERT INTO stadiums (name, capacity, city, team_id) VALUES ('Vicente Calderón', 54851, 'Madrid', 5002);

-- jugadores
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Cristiano Ronaldo', 28, 'Portugal', 172, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Xabi Alonso', 32, 'España', 12, 5000);
INSERT INTO players (name, age, country, goals, team_id) VALUES ('Sergio Ramos', 27, 'España', 24, 5000);


