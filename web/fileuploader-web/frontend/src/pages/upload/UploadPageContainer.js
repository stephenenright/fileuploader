import React, {useState} from "react";
import moment from "moment";

import FileStorageAPI from "../../api/file/FileStorageAPI";
import UploadPage from "./UploadPage";
import {sanitizeError, addValidationError} from "../../libs/error/ErrorLib";
import {isEmpty} from "../../libs/type/Object";
import {usePage} from "../../context/page-context";
import {PAGE_FILE_MANAGER} from "../../app/appConstants";

const UploadPageContainer = () => {
    const [dataset, setDataset] = useState({creationDate: moment()});
    const [progress, setProgress] = useState(0)
    const [pageData, setPageData] = useState({loading: false, validationErrors: {}, notifications: []})

    const onChange = (key, value) => {
        setDataset({...dataset, [key]: value})
    }
    const {loading, validationErrors, notifications} = pageData;

    const {setPage} = usePage();


    const onUpload = async (e) => {
        e.preventDefault();
        const {isValid, formErrors} = validateForm(dataset);

        if (!isValid) {
            setPageData({
                ...pageData,
                notifications: [{error: {message: 'Validation Failed'}}],
                validationErrors: formErrors
            })

            return;
        }

        if (!loading) {
            setPageData({loading: true, validationErrors: {}, notifications: []});
            await FileStorageAPI.upload(dataset, setProgress)
                .then((response) => {
                    setPageData({...pageData, validationErrors: {}, notifications: [response]})
                    setTimeout(() => setPage(PAGE_FILE_MANAGER), 1000);
                })
                .catch(e => {
                    const {data} =  sanitizeError(e);
                    const errorData = data.error || {};
                    setPageData({loading: false, validationErrors: errorData.validationErrors || {}, notifications: [data]});
            });
        }
    };

    return (
        <UploadPage
            dataset={dataset}
            notifications={notifications}
            validationErrors={validationErrors}
            loading={loading}
            uploadProgress={progress}
            onUpload={onUpload}
            onFormChange={onChange}
        />
    )

}

const validateForm = (dataset = {}) => {
    let validationErrors = {};

    if (!dataset.title || !dataset.title.trim()) {
        validationErrors = addValidationError(validationErrors, "title", "Must not be empty");
    } else if(dataset.title && dataset.title.length > 255) {
        validationErrors = addValidationError(validationErrors, "title", "Max length of 255 exceeded ");
    }

    if(dataset.description && dataset.description.length > 255) {
        validationErrors = addValidationError(validationErrors, "description", "Max length of 255 exceeded ");
    }

    if (!dataset.creationDate || !moment.isMoment(dataset.creationDate)) {
        validationErrors = addValidationError(validationErrors, "creationDate", "A valid date is required");
    }

    if (!dataset.files || dataset.files.length ===0) {
        validationErrors = addValidationError(validationErrors, "file", "Please select a file to upload");
    }

    return {
        isValid: isEmpty(validationErrors),
        formErrors: validationErrors
    }
}



export default UploadPageContainer;