import { useState } from "react";
import { useNavigate } from "react-router-dom";
import apiClient from "../apiClient";
import "./JoinPage.css";

function JoinPage() {
  const [email, setEmail] = useState("");
  const [authCode, setAuthCode] = useState("");
  const [emailVerified, setEmailVerified] = useState(false);
  const [password, setPassword] = useState("");
  const [validationPassword, setValidationPassword] = useState("");
  const [name, setName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

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

      const res = await apiClient.post("/api/users/register", formData);

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
    <div className="join-page">
      <div className="join-container">
        <h1 className="join-title">회원 가입</h1>
        <form onSubmit={HandleRegister}>
          <div className="input-group">
            <label htmlFor="name">이름</label>
            <input
              type="text"
              id="name"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="이름"
              required
            />
          </div>

          <div className="input-group">
            <label htmlFor="email">이메일</label>
            <div className="email-container">
              <input
                type="email"
                id="email"
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="이메일"
                required
              />
              <button
                type="button"
                onClick={handleSendAuthCode}
                disabled={emailVerified}
              >
                인증요청
              </button>
            </div>
          </div>

          {!emailVerified && (
            <div className="input-group">
              <label htmlFor="authCode">인증번호 입력</label>
              <div className="auth-code-container">
                <input
                  type="text"
                  id="authCode"
                  value={authCode}
                  onChange={(e) => setAuthCode(e.target.value)}
                  placeholder="인증번호 입력"
                />
                <button type="button" onClick={handleVerifyAuthCode}>
                  인증 확인
                </button>
              </div>
            </div>
          )}

          <div className="input-group">
            <label htmlFor="password">비밀번호</label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="비밀번호"
              required
            />
          </div>

          <div className="input-group">
            <label htmlFor="validationPassword">비밀번호 확인</label>
            <input
              type="password"
              id="validationPassword"
              value={validationPassword}
              onChange={(e) => setValidationPassword(e.target.value)}
              placeholder="비밀번호 확인"
              required
            />
          </div>

          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <div className="button-container">
            <button type="submit" className="btn-submit">
              가입
            </button>
            <button
              type="button"
              onClick={() => navigate("/")}
              className="btn-link"
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default JoinPage;
