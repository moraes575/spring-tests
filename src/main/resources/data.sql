INSERT INTO categories (name) VALUES
('Ação'),
('Romance'),
('Documentário'),
('Comédia'),
('Animação');

INSERT INTO movies (title, director, released_date, category_id) VALUES
('Velozes e Furiosos 7', 'James Wan', '2015-04-02', 1),
('Projeto Gemini', 'Ang Lee', '2019-10-10', 1),
('Meu Passado Me Condena', 'Julia Rezende', '2013-10-25', 4),
('Até que a Sorte nos Separe', 'Roberto Santucci', '2012-10-05', 4),
('Toy Story 2', 'John Lasseter', '1999-12-17', 5),
('Carros 3', 'Brian Fee', '2017-07-13', 5);

INSERT INTO addresses (street, city, state, zip_code) VALUES
('Rua das Neves', 'São Paulo', 'São Paulo', '48845-510'),
('Rua das Flores', 'São Caetano do Sul', 'São Paulo', '09845-814'),
('Rua Fernandes', 'Mauá', 'São Paulo', '90044-602'),
('Rua Tupiniquim', 'São Paulo', 'São Paulo', '47122-076'),
('Rua Pires', 'Ribeirão Pires', 'São Paulo', '62884-841');

INSERT INTO members (name, birth_date, address_id) VALUES
('Matheus Moraes', '2003-03-15', 5),
('João Nóbrega', '2000-07-20', 2),
('Maria Clara', '1995-10-12', 1),
('Mário Silveira', '2000-01-16', 4),
('Rodrigo Rodrigues', '1994-09-30', 3);

INSERT INTO rentals (transaction_date, member_id, movie_id) VALUES
('2020-07-31', 1, 2),
('2020-07-25', 2, 1),
('2020-07-26', 3, 2),
('2020-07-27', 4, 3),
('2020-07-31', 5, 5);