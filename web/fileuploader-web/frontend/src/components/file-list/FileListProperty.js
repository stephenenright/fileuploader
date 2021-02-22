import React from "react";
import PropTypes from "prop-types";

const FileListProperty = ({label, value}) => {
    return (
        <div className="ac-file-list__property">
            <div aria-label={label} className="ac-file-list__property__label">
                {label}:
            </div>
            <div aria-label={label} className="ac-file-list__property__value">
                {value}
            </div>
        </div>
    )
}

FileListProperty.propTypes = {
    label: PropTypes.string,
    value: PropTypes.any
}


export default FileListProperty;


