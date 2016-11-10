CREATE DATABASE persistenceDb;

CREATE TABLE category{
  id UNSIGNED INT NOT NULL PRIMARY KEY,
  parentId UNSIGNED INT NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion UNSIGNED INT NOT NULL,
  FOREIGN KEY(parentId) REFERENCES category(id);
};

CREATE TABLE item{
  id UNSIGNED INT NOT NULL PRIMARY KEY,
  categoryId UNSIGNED INT NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  lastModifiedDateTime TIMESTAMP NOT NULL,
  createDateTime TIMESTAMP NOT NULL,
  optimisticLockVersion UNSIGNED INT NOT NULL,
  FOREIGN KEY(categoryId) REFERENCES category(id)
};