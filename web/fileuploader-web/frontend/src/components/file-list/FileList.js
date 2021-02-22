import React from "react";
import PropTypes from "prop-types";

import Panel from "../panel";
import FileListEmpty from "./FileListEmpty";
import FileListItem from "./FileListItem";


const FileList = ({loading, error, onRetry, files =[], onUpload, onDelete}) => {

    const resolvedFiles = files.map((file, index) => {
        return (
            <FileListItem key={index} file={file} onDelete={onDelete} />
            )

    })

    const hasFiles = resolvedFiles.length > 0;

    return (
        <Panel className="ac-file-list">
            <Panel.Header title="Files" />
            <Panel.Body className={hasFiles ? 'ac-file-list__panel-body' : ''} loading={loading} error={error} onRetry={onRetry}>
                {!hasFiles  ? (
                    <FileListEmpty onUpload={onUpload} />
                ) : (
                        <div className="ac-files-list__items">
                            {resolvedFiles}
                        </div>
                )}
            </Panel.Body>
        </Panel>
    )
}

FileList.propTypes = {
    loading: PropTypes.bool,
    error: PropTypes.bool,
    files: PropTypes.array,
    onRetry: PropTypes.func,
    onUpload: PropTypes.func,
    onDelete: PropTypes.func,
}

export default FileList;



