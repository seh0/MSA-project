INSERT INTO users (u_id, u_email, u_name, u_password, u_created_at, u_role, u_address) VALUES
 (1, 'cheolsu@example.com', '김철수', 'password123', CURRENT_TIMESTAMP, 'admin', '서울특별시 강남구'),
 (2, 'younghee@example.com', '이영희', 'securepass', CURRENT_TIMESTAMP, 'user', '부산광역시 해운대구'),
 (3, 'minsu@example.com', '박민수', 'mypassword', CURRENT_TIMESTAMP, 'user', '인천광역시 남동구'),
 (4, 'daeun@example.com', '최다은', 'strongpass', CURRENT_TIMESTAMP, 'admin', '대구광역시 수성구'),
 (5, 'jihoon@example.com', '송지훈', 'helloWorld', CURRENT_TIMESTAMP, 'user', '대전광역시 유성구');

INSERT INTO performance (p_id, p_title, p_manager, p_genre, p_date, p_spot, p_all_spot, p_price, p_place, p_img, p_end_time) VALUES
(1, '뮤지컬 캣츠', '홍길동', '뮤지컬', CURRENT_TIMESTAMP, 150, 200, 50000, '서울특별시 강남구', 'cats.jpg', CURRENT_TIMESTAMP),
(2, '연극 햄릿', '이순신', '연극', CURRENT_TIMESTAMP, 100, 150, 40000, '부산광역시 해운대구', 'hamlet.jpg', CURRENT_TIMESTAMP),
(3, '콘서트 아이유', '박지성', '콘서트', CURRENT_TIMESTAMP, 300, 500, 80000, '인천광역시 남동구', 'iu.jpg', CURRENT_TIMESTAMP),
(4, '오페라 라 트라비아타', '김연아', '오페라', CURRENT_TIMESTAMP, 80, 120, 60000, '대구광역시 수성구', 'traviata.jpg', CURRENT_TIMESTAMP),
(5, '발레 백조의 호수', '손흥민', '발레', CURRENT_TIMESTAMP, 120, 150, 45000, '대전광역시 유성구', 'swanlake.jpg', CURRENT_TIMESTAMP);