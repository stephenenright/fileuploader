import React, {useState} from "react";

import FileManagerPage from "./FileManagerPage";
import FileAPI from "../../api/file/FileAPI";
import useAsync from "../../libs/hooks/async/useAsync";
import {sanitizeError} from "../../libs/error/ErrorLib";

const FileManagerPageContainer = (props) => {
    const [page, setPage] = useState(0);
    const [pageData, setPageData] = useState({loading: false, notifications: []});
    const {loading, notifications} = pageData;

    const { data, isRejected, isPending, retry } = useAsync(
        (signal) => {
            setPageData({...pageData, notifications: []});
            return FileAPI.list(page, signal)
        },
        [page]
    );

    const onPageChange = (page) => setPage(page);

    const onRefresh = () => {
        setPage(0);
        setPageData({loading: false, notifications: []});
        retry();
    }

    const onDelete = (id) => {
        setPageData({loading: true, notifications: []})
        FileAPI.delete(id)
            .then((response) => {
                setPageData({loading: false, notifications: [response]});
                setPage(0);
                retry();
            })
            .catch(e => {
                const {data} =  sanitizeError(e);
                const errorData = data.error || {};
                setPageData({loading: false, validationErrors: errorData.validationErrors || {}, notifications: [data]});
        });
    }

    return (
        <FileManagerPage
            error={isRejected}
            loading={isPending || loading}
            files={data ? data.data : []}
            page={data ? data.pageNumber : 0}
            totalPages={data ? data.numberOfPages : 0}
            pageSize={data ? data.numberOfItemsPerPage : 0}
            totalFiles={data ? data.totalResults : 0}
            notifications={notifications}
            onPageChange={onPageChange}
            onRefresh={onRefresh}
            onRetry={retry}
            onDelete={onDelete}
        />
    )
}

export default FileManagerPageContainer;