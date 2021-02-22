import React from "react";
import PropTypes from "prop-types";

import {formatBytes} from "../../libs/size/DataSize";
import Button from "react-bootstrap/cjs/Button";
import {DownloadIcon, TrashIcon} from "../icons/AppIcons";
import FileListProperty from "./FileListProperty";
import useDateTimeFormat from "../../libs/hooks/intl/date-time-format/useDateTimeFormat";

const FileListItem = ({file, onDelete}) => {

    const dateTimeFormat = useDateTimeFormat({
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });


    return (
        <div className="ac-file-list__item">
            <div className="ac-file-list__buttons">
                <Button size="sm" variant="danger" title="Delete File" onClick={() => onDelete(file.id)}><TrashIcon size="medium" /></Button>
                <Button size="sm" variant="secondary" title="Download File" href={file.downloadUrl} ><DownloadIcon size="medium" /></Button>
            </div>
            <div className="ac-file-list__content">
                <h3>{file.title}</h3>
                {file.description && (
                    <p>
                        {file.description}
                    </p>
                )}
            </div>
            <div className="ac-file-list__properties">
                <FileListProperty label="File Size" value={formatBytes(file.size)} />
                <FileListProperty label="Extension" value={file.extension} />
                <FileListProperty label="Created" value={dateTimeFormat.format(new Date(file.creationDate))} />
            </div>
        </div>
    )

}


FileListItem.propTypes = {
    file: PropTypes.object
}


export default FileListItem;