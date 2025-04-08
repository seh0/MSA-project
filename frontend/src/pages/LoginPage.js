import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../apiClient";
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const emailChange = (event) => {
    setEmail(event.target.value);
  };

  const passwordChange = (event) => {
    setPassword(event.target.value);
  };

  const joinBtn = () => {
    navigate("/join");
  };

  const backBtn = () => {
    navigate("/");
  };

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const res = await apiClient.post("/api/users/login", {
        email,
        password,
      });
      const token = res.data;
      localStorage.setItem("token", token);
      navigate("/");
    } catch (error) {
      setErrorMessage("이메일 또는 비밀번호를 다시 확인해 주세요.");
      setIsLoggedIn(false);
      console.error(error);
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h1 className="login-title">로그인</h1>
        {!isLoggedIn ? (
          <form onSubmit={handleLogin}>
            <div className="input-group">
              <label htmlFor="email">이메일</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={emailChange}
                placeholder="이메일"
                required
              />
            </div>
            <div className="input-group">
              <label htmlFor="password">비밀번호</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={passwordChange}
                placeholder="비밀번호"
                required
              />
            </div>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <button type="submit" className="btn-submit">로그인</button>
            <div className="button-container">
              <button type="button" onClick={joinBtn} className="btn-link">
                회원가입
              </button>
              <button type="button" onClick={backBtn} className="btn-link">
                뒤로가기
              </button>
            </div>
          </form>
        ) : (
          <div>
            <h3>이미 로그인 되어있습니다.</h3>
            <button onClick={backBtn} className="btn-link">뒤로가기</button>
          </div>
        )}
      </div>
    </div>
  );
}

export default LoginPage;
