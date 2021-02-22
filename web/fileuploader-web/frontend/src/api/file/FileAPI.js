import axios from 'axios';

import {createCancelToken} from "../../libs/axios/cancellation";

const BASE_URL = '/api/files';

const FileAPI = {
    list: async (page = 0, signal) => {
        const options = {
            cancelToken: createCancelToken(signal),
            params: {
                page: page
            }
        };

        const {data} = await axios.get(BASE_URL, options);
        return data;
    },

    delete: async (id) => {
        const {data} = await axios.delete(`${BASE_URL}/${id}`);
        return data;
    }
}

export default FileAPI