INSERT INTO ROLES (NAME)
    VALUES ('STAFF'), ('MANAGER');

INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, ACTIVE, ROLE_ID)
    VALUES ('Michael', 'Scott', 'mscott', '$2a$10$QKiKXFM6c/OoptsA6Bx1aODUxCfNjb9xamAnGuTbey52QuOqboHKG', 1, 1),
           ('Jim', 'Halpert', 'jhalpert', '$2a$10$2SlDSvQyAHaR/yd8giTiU.XJ9ZzXmdbY7oYGag/yU7zqL0Jhekp7a', 1, 2),
           ('Dwight', 'Schrute', 'dschrute', '$2a$10$170xBGVy4JERk8KdJP0Ptu02CyzugY3W7Em.sOx68A6bXG2kv0jXe', 1, 2);

