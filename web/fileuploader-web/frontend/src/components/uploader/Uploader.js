import React from 'react';
import PropTypes from 'prop-types';

import Dropzone from 'react-dropzone';
import ProgressBar from "react-bootstrap/cjs/ProgressBar";
import classNames from "classnames";

const Uploader = ({error, loading, onUpload, progress}) => {

    const renderDropZone = () => {
        if (error) {
            return (
                <div className="error-text">{error}</div>
            );
        } else if (loading) {
            return (
                <div>
                    <div className="ac-uploader-loading-text">File Uploading...</div>
                    <div className="ac-uploader-progress">
                        <ProgressBar now={progress} label={`${progress}%`}/>
                    </div>
                </div>
            );
        } else if (!loading && progress === 100) {
            return (
                <div>
                    <div className="ac-uploader-start-text">File Uploaded</div>
                    <div className="ac-uploader-progress">
                        <ProgressBar now={progress} label={`${progress}%`}/>
                    </div>
                </div>
            );
        } else {
            return (
                <div className="ac-uploader-start-text">
                    <div>Click or drop file here to upload</div>
                </div>
            );
        }
    }

    return (
        <div className="ac-uploader__container">
            <Dropzone
                data-testid="uploader-dropzone"
                className={
                    classNames('ac-uploader', {
                        'ac-uploader--error': error
                    })}
                disableClick={loading}
                multiple={false}
                onDrop={files => onUpload(files)}>
                <div className="ac-uploader__inner">
                    {renderDropZone()}
                </div>
            </Dropzone>
        </div>
    );
}

Uploader.propTypes = {
    loading: PropTypes.bool,
    error: PropTypes.string,
    errorMessage: PropTypes.string,
    progress: PropTypes.number,
    onUpload: PropTypes.func
};

Uploader.defaultProps = {
    uploadProgress: 0,
};


export default Uploader;


