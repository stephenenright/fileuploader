import React from "react";

import Navbar from "react-bootstrap/cjs/Navbar";
import {FileIcon} from "../../../components/icons/AppIcons";


const AppHeader = () => {

    return (
        <Navbar className="app-header" expand="lg">
            <Navbar.Brand href="#home">
                <FileIcon size="medium" stroke="#FFF"/>
                File Manager
            </Navbar.Brand>
        </Navbar>
    )
}

export default AppHeader;