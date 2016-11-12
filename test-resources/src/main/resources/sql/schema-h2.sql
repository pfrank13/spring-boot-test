CREATE SCHEMA persistenceDb;

CREATE TABLE category(
  id INT NOT NULL IDENTITY(0, 1),
  parentId INT,
  name VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createdDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion INT NOT NULL,
  FOREIGN KEY(parentId) REFERENCES category(id)
);

CREATE TABLE item(
  id INT NOT NULL IDENTITY(0, 1),
  categoryId INT NOT NULL,
  name VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createdDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion INT NOT NULL,
  FOREIGN KEY(categoryId) REFERENCES category(id)
);