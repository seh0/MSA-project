import { useState, useEffect } from "react";
import apiClient from "../apiClient";
import { useNavigate } from "react-router-dom";
import profileIMG from "../img/profile.PNG";

import "./MyPage.css";

function MyPage() {
  const [userInfo, setUserInfo] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [nameInput, setNameInput] = useState("");
  const [addressInput, setAddressInput] = useState("");

  const navigate = useNavigate();

  const formatDate = (localDateTimeString) => {
    const date = new Date(localDateTimeString);

    const dateFormatter = new Intl.DateTimeFormat("ko-KR", {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      hour12: true,
    });

    let formattedDate = dateFormatter.format(date);
    return formattedDate;
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
      return;
    }

    const fetchUserInfo = async () => {
      try {
        const response = await apiClient.get("/api/users/info", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUserInfo(response.data);
        setNameInput(response.data.name);
        setAddressInput(response.data.address || "");
      } catch (error) {
        console.error(error);
      }
    };

    fetchUserInfo();
  }, [navigate]);

  const updateUserInfo = async () => {
    if (!nameInput) {
      alert("빈칸 확인");
      return;
    }

    const token = localStorage.getItem("token");
    try {
      await apiClient.put(
        "/api/users/update",
        {
          name: nameInput,
          address: addressInput,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("사용자 정보가 성공적으로 수정되었습니다.");
      setUserInfo({
        ...userInfo,
        name: nameInput,
        address: addressInput,
      });
      setIsEditing(false);
    } catch (error) {
      console.error(error);
    }
  };

  const nameChange = (event) => {
    setNameInput(event.target.value);
  };

  const addressChange = (event) => {
    setAddressInput(event.target.value);
  };

  const editUserInfo = () => {
    if (isEditing) {
      setNameInput(userInfo.name);
      setAddressInput(userInfo.address);
    }
    setIsEditing(!isEditing);
  };

  const deleteUser = async () => {
    const confirmed = window.confirm("탈퇴 하시겠습니까?");

    if (!confirmed) return;

    const token = localStorage.getItem("token");

    try {
      await apiClient.delete("/api/users/delete", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert("사용자 정보가 삭제되었습니다.");
      localStorage.removeItem("token");
      navigate("/");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="my-page-container">
      {userInfo ? (
        <div className="my-page-content">
          <div className="my-page-left-section">
            <div className="my-page-profile-image-section">
              <div className="my-page-profile-image-container">
                <img
                  src={profileIMG}
                  alt="Profile"
                  className="my-page-profile-image"
                />
              </div>
              <p className="my-page-profile-name">{userInfo.name} 님</p>
            </div>

            <div className="my-page-action-buttons">
              <button
                onClick={editUserInfo}
                className="my-page-btn-edit my-page-button"
              >
                정보 수정
              </button>
            </div>
          </div>

          <div className="my-page-right-section">
            <h2>마이페이지</h2>
            <table className="my-page-user-info-table">
              <thead>
                <th
                  colSpan="2"
                  style={{
                    textAlign: "center",
                    fontSize: "18px",
                    fontWeight: "bold",
                  }}
                >
                  내 정보
                </th>
              </thead>
              <tbody>
                <tr>
                  <td>이메일</td>
                  <td>{userInfo.email}</td>
                </tr>
                <tr>
                  <td>이름</td>
                  <td>
                    {isEditing ? (
                      <input
                        type="text"
                        value={nameInput}
                        onChange={nameChange}
                        className="my-page-input-field"
                      />
                    ) : (
                      userInfo.name
                    )}
                  </td>
                </tr>
                <tr>
                  <td>주소</td>
                  <td>
                    {isEditing ? (
                      <input
                        type="text"
                        value={addressInput}
                        onChange={addressChange}
                        className="my-page-input-field"
                      />
                    ) : userInfo.address ? (
                      userInfo.address
                    ) : (
                      <span className="my-page-address-prompt">
                        주소를 등록해 주세요
                      </span>
                    )}
                  </td>
                </tr>
                <tr>
                  <td>가입일</td>
                  <td>{formatDate(userInfo.createdAt)}</td>
                </tr>
                <tr>
                  <td>권한</td>
                  <td>{userInfo.role}</td>
                </tr>
              </tbody>
            </table>

            {isEditing && (
              <div className="my-page-action-buttons">
                <div className="my-page-top-buttons">
                  <button
                    onClick={updateUserInfo}
                    className="my-page-btn-save my-page-button"
                  >
                    수정 완료
                  </button>
                </div>
                <button
                  onClick={deleteUser}
                  className="my-page-btn-delete my-page-button"
                >
                  탈퇴
                </button>
              </div>
            )}
            <h2>예매 목록</h2>
            <div className="ticket-list">
              <div>
                <p>티켓1</p>
                <p>티켓2</p>
                <p>티켓3</p>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <p>로딩 중...</p>
      )}
    </div>
  );
}

export default MyPage;
