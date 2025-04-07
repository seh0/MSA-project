CREATE TABLE IF NOT EXISTS users (
 u_id BIGINT PRIMARY KEY,
 u_email varchar(255) UNIQUE NOT NULL,
 u_name VARCHAR(100) NOT NULL,
 u_password VARCHAR(255) NOT NULL,
 u_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 u_role VARCHAR(50),
 u_address VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS performance (
 p_id INT PRIMARY KEY,
 p_title VARCHAR(255) NOT NULL,
 p_manager VARCHAR(100),
 p_genre VARCHAR(100),
 p_date TIMESTAMP NOT NULL,
 p_spot INT,
 p_all_spot INT,
 p_price INT,
 p_place VARCHAR(255),
 p_img VARCHAR(255),
 p_end_time TIMESTAMP
);