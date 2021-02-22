import React, {useState} from "react";


import AppHeader from "./layout/header/Header";
import FileManagerPageContainer from "../pages/file-manager/FileManagerPageContainer";
import UploadPageContainer from "../pages/upload/UploadPageContainer";
import PageContext from "../context/page-context";
import AppErrorBoundary from "./AppErrorBoundary";
import {PAGE_FILE_MANAGER} from "./appConstants";

function App() {
    const [page, setPage] = useState(PAGE_FILE_MANAGER);

    return (
        <AppErrorBoundary onReset={() => setPage(PAGE_FILE_MANAGER)}>
            <PageContext.Provider value={{page, setPage}} >
                <div className="app-container">
                    <AppHeader/>
                        <div className="app-content">
                            {page === PAGE_FILE_MANAGER ? (
                                <FileManagerPageContainer/>
                            ) : (
                                <UploadPageContainer />
                            )}
                        </div>
                </div>
            </PageContext.Provider>
        </AppErrorBoundary>
    );
}

export default App;
