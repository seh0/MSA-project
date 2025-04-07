import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import apiClient from "../apiClient";

import "./Page.css";

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
      setErrorMessage("다시 확인해 주세요");
      setIsLoggedIn(false);
      console.error(error);
    }
  };

  return (
    <div className="Login">
      <h1>로그인</h1>
      {!isLoggedIn ? (
        <div>
        <form onSubmit={handleLogin}>
          <div>
            <input
              type="text"
              id="email"
              value={email}
              onChange={emailChange}
              placeholder="이메일"
              required
            />
          </div>
          <div>
            <input
              type="password"
              id="password"
              value={password}
              onChange={passwordChange}
              placeholder="비밀번호"
              required
            />
          </div>
          {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
          <button type="submit">로그인</button>
        </form>
        <div className="button-container">
        <button onClick={joinBtn}>회원가입</button>
        <button onClick={backBtn}>뒤로가기</button>
      </div>
        </div>
      ) : (
        <div>
          <h3>이미 로그인 되어있습니다</h3>
          <button onClick={backBtn}>뒤로가기</button>
        </div>
      )}
    </div>
  );
}

export default LoginPage;
