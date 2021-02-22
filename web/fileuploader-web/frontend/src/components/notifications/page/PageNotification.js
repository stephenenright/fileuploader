import React from "react";
import PropTypes from "prop-types";

import {Alert} from "react-bootstrap";

const PageNotification = ({variant, message}) => {
    return (
        <div className="ac-page-notification">
            <Alert variant={variant}>
                {message}
            </Alert>
        </div>
    )
}

PageNotification.propTypes = {
    variant: PropTypes.string,
    message: PropTypes.string
}

export default PageNotification;