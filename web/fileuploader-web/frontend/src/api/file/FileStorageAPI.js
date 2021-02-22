import axios from "axios";
import moment from "moment";

const BASE_URL = '/api/storage/files';

const FileStorageAPI = {
    upload: async ({files, title, description,creationDate}, progressCallback) => {
        const onUploadProgress =  progressEvent => {
            let progress = parseInt((progressEvent.loaded * 100) / progressEvent.total);
            progressCallback(progress);
        }

        const options = {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress
        }

        let file = files && files.length > 0 ? files[0] : null;
        let formData = new FormData();
        formData.append("file", file);
        formData.append("title", title)
        formData.append("description", description)

        if (creationDate && moment.isMoment(creationDate)) {
            formData.append("creationDate", creationDate.toISOString());
        }

        const { data } = await axios.post(BASE_URL, formData, options);
        return data;
    }
}

export default FileStorageAPI
