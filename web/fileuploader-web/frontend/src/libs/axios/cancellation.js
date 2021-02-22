import axios from "axios";

function createCancelToken(signal) {
    if (!signal) {
        return;
    }

    const source = axios.CancelToken.source();

    signal.addEventListener('abort', () => {
        source.cancel();
    });

    return source.token;
}

export { createCancelToken };