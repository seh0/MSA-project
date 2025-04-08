import { useState } from "react";

import "./Calendar.css";

const Calendar = () => {
  const [currentDate, setCurrentDate] = useState(new Date());

  const daysInMonth = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth() + 1,
    0
  ).getDate();
  const firstDayOfMonth = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth(),
    1
  ).getDay();

  const handlePrevMonth = () => {
    setCurrentDate(
      new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1)
    );
  };

  const handleNextMonth = () => {
    setCurrentDate(
      new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1)
    );
  };

  const today = new Date();
  const isToday = (day) => {
    const selectedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    return selectedDate.toDateString() === today.toDateString(); // Compare only date part
  };

  const renderDays = () => {
    const days = [];
    for (let i = 0; i < firstDayOfMonth; i++) {
      days.push(<div key={`empty-${i}`} className="empty-day"></div>);
    }
    for (let i = 1; i <= daysInMonth; i++) {
      const isSelected = isToday(i);
      days.push(
        <div key={i} className={`day ${isSelected ? "selected-day" : ""}`}>
          {i}
        </div>
      );
    }
    return days;
  };

  return (
    <div className="calendar">
      <header>
        <button onClick={handlePrevMonth}>-</button>
        <h2>{`${currentDate.getFullYear()}-${currentDate.getMonth() + 1}`}</h2>
        <button onClick={handleNextMonth}>-</button>
      </header>
      <div className="days-of-week">
        <div>일</div>
        <div>월</div>
        <div>화</div>
        <div>수</div>
        <div>목</div>
        <div>금</div>
        <div>토</div>
      </div>
      <div className="calendar-days">{renderDays()}</div>
    </div>
  );
};

export default Calendar;
