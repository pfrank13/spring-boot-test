CREATE SCHEMA persistenceDb;

CREATE TABLE category(
  id INT NOT NULL IDENTITY(1, 1),
  name VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createdDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion INT NOT NULL
);

CREATE TABLE item(
  id INT NOT NULL IDENTITY(1, 1),
  categoryId INT NOT NULL,
  name VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createdDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion INT NOT NULL,
  FOREIGN KEY(categoryId) REFERENCES category(id)
);

INSERT INTO category(name, lastModifiedDateTime, createdDateTime, optimisticLockVersion)
    VALUES('someCategory', NOW(), NOW(), 0);