function isString(value) {
    return !!(value === '' || (value && value.charCodeAt && value.substr));
}

export {isString};

function isEmpty(obj) {
    return Object.keys(obj).length === 0;
}

export {isEmpty};