-- insert admin (username a, password aa)
INSERT INTO IWUser (id, enabled, reported, imgcolor, numgames, roles, username, password)
VALUES (1, TRUE, FALSE, 'orange', 0, 'ADMIN,USER', 'a',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
INSERT INTO IWUser (id, enabled, reported, imgcolor, numgames, roles, username, password)
VALUES (2, TRUE, FALSE, 'pink', 0, 'USER', 'b',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
    INSERT INTO IWUser (id, enabled, reported, imgcolor, numgames, roles, username, password)
VALUES (3, TRUE, FALSE, 'blue', 0, 'USER', 'e',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');

-- start id numbering from a value that is larger than any assigned above
ALTER SEQUENCE "PUBLIC"."GEN" RESTART WITH 1024;