import { useState } from "react";
import { useNavigate } from "react-router-dom";

import apiClient from "../apiClient";

import "./Page.css";

function JoinPage() {
  const [email, setEmail] = useState("");
  const [authCode, setAuthCode] = useState("");
  const [emailVerified, setEmailVerified] = useState(false);
  const [password, setPassword] = useState("");
  const [validationPassword, setValidationPassword] = useState("");
  const [name, setName] = useState("");
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState("");

  const handleSendAuthCode = async () => {
    try {
      await apiClient.post("/api/users/send-code", { email });
      alert("인증번호가 이메일로 발송되었습니다.");
    } catch (error) {
      alert("인증번호 전송 실패!");
    }
  };

  const handleVerifyAuthCode = async () => {
    try {
      await apiClient.post("/api/users/verify-code", {
        email,
        code: authCode,
      });
      alert("이메일 검증완료");
      setEmailVerified(true);
    } catch (error) {
      alert("인증번호가 틀렸습니다.");
    }
  };

  const HandleRegister = async (event) => {
    event.preventDefault();

    if (!emailVerified) {
      setErrorMessage("이메일 인증을 완료해주세요.");
      return;
    }

    if (password !== validationPassword) {
      setErrorMessage("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const formData = new FormData();
      formData.append("email", email);
      formData.append("password", password);
      formData.append("name", name);

      const res = await apiClient.post('/api/users/register', formData);

      if (res.status === 200) {
        alert("회원가입 성공!");
        navigate("/login");
      }
    } catch (error) {
      setErrorMessage("이메일 중복 / 오류");
      console.error(error);
    }
  };

  return (
    <div className="Join">
      <h1>회원 가입</h1>
      <input
        type="text"
        name="name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="이름"
        required
      />
      <input
        type="email"
        name="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="이메일"
        required
      />
      <button onClick={handleSendAuthCode} disabled={emailVerified}>
        인증요청
      </button>
      {!emailVerified && (
        <div>
          <div>
            <input
              type="text"
              value={authCode}
              onChange={(e) => setAuthCode(e.target.value)}
              placeholder="인증번호 입력"
            />
            <button onClick={handleVerifyAuthCode}>인증 확인</button>
          </div>
        </div>
      )}
      <input
        type="password"
        name="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="비밀번호"
        required
      />
      <div>
        <input
          type="password"
          value={validationPassword}
          onChange={(e) => setValidationPassword(e.target.value)}
          placeholder="비밀번호 확인"
        />
      </div>
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      <div className="button-container">
        <button type="button" onClick={HandleRegister}>
          가입
        </button>
        <button onClick={() => window.history.back()}>취소</button>
      </div>
    </div>
  );
}

export default JoinPage;
