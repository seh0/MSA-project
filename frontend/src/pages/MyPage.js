import { useState, useEffect } from "react";
import apiClient from "../apiClient";
import { useNavigate } from "react-router-dom";

function MyPage() {
  const [userInfo, setUserInfo] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [nameInput, setNameInput] = useState("");
  const [addressInput, setAddressInput] = useState("");

  const navigate = useNavigate();

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

  const cancelUpdate = () => {
    setNameInput(userInfo.name);
    setAddressInput(userInfo.address);
    setIsEditing(false);
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
    <div>
      {userInfo ? (
        <div>
          <h1>마이페이지</h1>
          <p>이메일: {userInfo.email}</p>
          <p>권한: {userInfo.role}</p>

          {isEditing ? (
            <div>
              <div>
                <label>이름:</label>
                <input
                  type="text"
                  value={nameInput}
                  onChange={nameChange}
                />
              </div>
              <div>
                <label>주소:</label>
                <input
                  type="text"
                  value={addressInput}
                  onChange={addressChange}
                />
              </div>
              <button onClick={updateUserInfo}>수정 완료</button>
              <button onClick={cancelUpdate}>취소</button>
            </div>
          ) : (
            <div>
              <p>이름: {userInfo.name}</p>
              <p>
                주소:{" "}
                {userInfo.address ? (
                  userInfo.address
                ) : (
                  <span style={{ color: "blue" }}>주소를 등록해 주세요</span>
                )}
              </p>
              <button onClick={() => setIsEditing(true)}>정보 수정</button>
              <button onClick={deleteUser}>탈퇴</button>
            </div>
          )}
        </div>
      ) : (
        <p>로딩 중...</p>
      )}
    </div>
  );
}

export default MyPage;
