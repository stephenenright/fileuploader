import React from "react";
import PropTypes from "prop-types";

import Button from "react-bootstrap/cjs/Button";

const FileListEmpty = ({onUpload}) => {
    return (
        <div className="ac-file-list__empty">
            <p>No Files Available</p>
            <Button onClick={onUpload} variant="secondary">Upload File</Button>
        </div>
    )
}

FileListEmpty.propTypes = {
    onUpload: PropTypes.func,
}

export default FileListEmpty;