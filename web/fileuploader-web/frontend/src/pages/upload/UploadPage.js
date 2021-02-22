import React from "react";
import PropTypes from "prop-types";

import Breadcrumbs from "../../components/layout/breadcrumbs";
import PageLayout from "../../components/layout/page";
import UploadForm from "./components/upload-form/UploadForm";
import ContentWrapper from "../../components/layout/wrapper";
import PageNotifications from "../../components/notifications/page/PageNotifications";
import {PAGE_FILE_MANAGER} from "../../app/appConstants";
import {usePage} from "../../context/page-context";


export const UploadPage = ({notifications, onFormChange, ...rest}) => {

    const {setPage} = usePage();

    const breadcrumbs = [
        {label: "Files", onClick: () => setPage(PAGE_FILE_MANAGER)},
        {label: "Upload"}
    ];

    return (
        <PageLayout>
            <Breadcrumbs breadcrumbs={breadcrumbs} />
            <ContentWrapper>
                <PageLayout.Title title="Upload File" />
                <PageLayout.Content>
                    <PageNotifications notifications={notifications} />
                    <UploadForm
                        {...rest}
                        onChange={onFormChange}
                    />
                </PageLayout.Content>
            </ContentWrapper>
        </PageLayout>
    )
}

UploadPage.propTypes = {
    dataset: PropTypes.object,
    onFormChange: PropTypes.func,
    onUpload: PropTypes.func,
    uploadProgress: PropTypes.number,
    notifications: PropTypes.array,
    validationErrors: PropTypes.object,
    loading: PropTypes.bool
}

UploadPage.defaultProps = {
    notifications: [],
    validationErrors: {}
}

export default UploadPage;