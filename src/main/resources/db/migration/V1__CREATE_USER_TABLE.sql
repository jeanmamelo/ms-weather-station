CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    spotify_id VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    followers INTEGER NOT NULL,
    image VARCHAR(250) NOT NULL,
    PRIMARY KEY(id)
);