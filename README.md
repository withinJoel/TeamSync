# TeamSync
Made with 💖 by Joel Jolly.

# About
TeamSync is a comprehensive employee management and project tracking application designed to streamline the way you manage your workforce and projects. With TeamSync, you can effortlessly keep track of employee details, monitor the status of ongoing projects, and manage department information, all from a unified platform.

# To get started
* create a database
```
CREATE DATABASE teamsync;
```
* Enter inside the database
```
USE teamsync_db;
```

* create a table called `department`

```
CREATE TABLE department (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  department_id BIGINT,
  FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE project (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  employee_id BIGINT,
  FOREIGN KEY (employee_id) REFERENCES employee(id)
);
```
# Support Me
If you love Elsa and want to keep me caffeinated for more awesome updates, consider buying me a coffee!

[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-Donate-orange?style=for-the-badge&logo=buy-me-a-coffee)](https://www.buymeacoffee.com/withinjoel)

Made with 💖 by Joel Jolly.
