import { useNavigate } from "react-router-dom";

import apiClient from "../apiClient";

function Header() {
  const navigate = useNavigate();

  const loginBtn = () => {
    navigate("/login");
  };

  const logoutBtn = async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      alert("로그인 정보가 없습니다.");
      return;
    }

    try {
      const response = await apiClient.post(
        "/api/users/logout",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 200) {
        localStorage.removeItem("token");
        alert("로그아웃 성공!");
        navigate("/");
      }
    } catch (error) {
      console.error("로그아웃 오류:", error);
      alert("로그아웃에 실패했습니다.");
    }
  };

  const isAuthenticated = Boolean(localStorage.getItem("token"));

  return (
    <div className="header">
      <h1>
        <a href="/">WEB</a>
      </h1>
      <nav>
        <ul>
          <li>
            <a href="/">Menu1</a>
          </li>
          <li>
            <a href="/">Menu2</a>
          </li>
          <li>
            <a href="/mypage">My Page</a>
          </li>
        </ul>
      </nav>
      <div className="login">
        {isAuthenticated ? (
          <button onClick={logoutBtn}>로그아웃</button>
        ) : (
          <button onClick={loginBtn}>로그인</button>
        )}
      </div>
    </div>
  );
}

export default Header;
