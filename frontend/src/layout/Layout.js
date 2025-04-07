import { Outlet } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";

import "./Layout.css"

function Layout() {
  return (
    <div>
      <Header />
      <div className="wrap">
      <Outlet />
      </div>
      <Footer />
    </div>
  );
}

export default Layout;