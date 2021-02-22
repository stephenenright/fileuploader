import React from "react";
import PropTypes from "prop-types";

import Button from "react-bootstrap/cjs/Button";
import PageLayout from "../../components/layout/page";
import Breadcrumbs from "../../components/layout/breadcrumbs";
import ContentWrapper from "../../components/layout/wrapper";
import PageNotifications from "../../components/notifications/page/PageNotifications";
import FileList from "../../components/file-list/FileList";
import Toolbar from "../../components/toolbar/Toolbar";
import Paginator from "../../components/paginator";
import {usePage} from "../../context/page-context";
import {PAGE_UPLOAD} from "../../app/appConstants";

const FileManagerPage = ({
                             notifications,
                             loading,
                             error,
                             files = [],
                             page = 0,
                             pageSize = 0,
                             totalFiles = 0,
                             totalPages = 0,
                             onRefresh,
                             onRetry,
                             onDelete,
                             onPageChange,
}) => {

    const {setPage} = usePage();

    const onUpload = () => {
        setPage(PAGE_UPLOAD);
    }

    const hasFiles = files && files.length > 0;

    const breadcrumbs = [
        {label: "Files"},
    ];

    return (
        <PageLayout>
            <Breadcrumbs breadcrumbs={breadcrumbs} />
            <ContentWrapper>
                <PageLayout.Title title="File Manager" />
                <PageLayout.Content>
                    <PageNotifications notifications={notifications} />
                    <Toolbar>
                        <Button onClick={onRefresh} variant="secondary">Refresh</Button>
                        <Button onClick={onUpload} variant="secondary">Upload</Button>
                    </Toolbar>
                    {hasFiles && (
                        <Paginator onChange={onPageChange} page={page} pageSize={pageSize} total={totalFiles} totalPages={totalPages} />
                    )}
                    <FileList
                        files={files}
                        loading={loading}
                        error={error}
                        onUpload={onUpload}
                        onDelete={onDelete}
                        onRetry={onRetry}>
                    </FileList>
                    {hasFiles && (
                        <Paginator align="end" onChange={onPageChange} page={page} pageSize={pageSize} total={totalFiles} totalPages={totalPages} />
                    )}
                </PageLayout.Content>
            </ContentWrapper>
        </PageLayout>
    )
}

FileManagerPage.propTypes = {
    files: PropTypes.array,
    notifications: PropTypes.array,
    loading: PropTypes.bool,
    error: PropTypes.bool,
    onRetry: PropTypes.func,
    onRefresh: PropTypes.func,
    onDelete: PropTypes.func,
    onPageChange: PropTypes.func,
    page: PropTypes.number,
    pageSize: PropTypes.number,
    totalPages: PropTypes.number,
    totalFiles: PropTypes.number,

}

FileManagerPage.defaultProps = {
    files: []
}

export default FileManagerPage;