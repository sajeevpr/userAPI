
CREATE TABLE user (
   user_id varchar(36) NOT NULL,
   email varchar(255)  NOT NULL,
   monthly_expense decimal(19,2)  NOT NULL,
   monthly_income decimal(19,2)  NOT NULL,
   username varchar(255)  NOT NULL,
   PRIMARY KEY (user_id)
);

CREATE UNIQUE INDEX user_email_idx ON user (email);

CREATE TABLE account (
  account_id varchar(36) NOT NULL,
  account_name varchar(255)  NOT NULL,
  account_type varchar(255)  NOT NULL,
  balance decimal(19,2)  NOT NULL,
  currency varchar(255)  NOT NULL,
  user_id varchar(36)  NOT NULL,
  PRIMARY KEY (account_id)
);

ALTER TABLE account ADD CONSTRAINT account_fk FOREIGN KEY (user_id) REFERENCES user(user_id);

CREATE UNIQUE INDEX account_uniq_idx ON account (account_type, account_name);