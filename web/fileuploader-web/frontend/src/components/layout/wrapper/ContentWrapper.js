import React from "react";

const ContentWrapper = ({children}) => {
    return (
        <div className="ac-content-wrapper">
            {children}
        </div>
    );
}

export default ContentWrapper;