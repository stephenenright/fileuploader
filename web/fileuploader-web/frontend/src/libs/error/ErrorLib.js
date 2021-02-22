import {isString} from "../type/Object";


const createErrorObject = (message) => {
    return {
        message
    }
}

export {createErrorObject}

const errorMessage = "An unexpected error occurred";

const sanitizeError = (e) => {
    const {response = {}} = e;
    console.error(e);

    let {data, status} = response;

    if(!status) {
        status = 500;
    }

    if(!data || isString(data) ) {
        data = {error: createErrorObject(errorMessage)}
    }

    if (!data.error) {
        data.error = createErrorObject(errorMessage);
    }

    if (!data.error.message) {
        data.error.message = errorMessage;
    }


    return {data, status}

}

export {sanitizeError};


const addValidationError = (validationErrors = {}, key, error) => {
    let validationError = validationErrors[key];

    if (!validationError) {
        validationError = {
            label: key,
            errors: [error]
        }
    } else {
        let errors = validationError.errors || [];
        errors.push(error);
    }

    return {
        ...validationErrors,
        [key]: validationError
    }
}

export {addValidationError}

const resolveError = (validationErrors ,key) => {
    if (!validationErrors) {
        return null;
    }

    const validationError = validationErrors[key];

    if (!validationError) {
        return null;
    }

    if (validationError.errors && validationError.errors.length > 0) {
        return validationError.errors[0];
    }

    return null;
}

export {resolveError}