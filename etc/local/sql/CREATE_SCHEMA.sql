
DROP TABLE  IF EXISTS recipe;

CREATE TABLE recipe (
   id UUID,
   name TEXT NOT NULL,
   servings_size INTEGER,
   vegetarian BOOLEAN,
   ingredients VARCHAR[] NOT NULL,
   instructions VARCHAR[] NOT NULL,
   PRIMARY KEY (id)
);

insert into recipe(id, name, servings_size, vegetarian, ingredients, instructions)
values ('09aad778-18cd-44d1-9050-58154525d013', 'Chesscake', 2, false, '{"cheese", "apple"}', '{"1 cup of milk", "1 hour in oven"}');

insert into recipe(id, name, servings_size, vegetarian, ingredients, instructions)
values ('09aad778-18cd-44d1-9050-58154525d015', 'Apple cake', 1, true, '{"apple"}', '{"1 liter of water"}');
