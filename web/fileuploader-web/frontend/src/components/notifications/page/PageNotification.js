import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";

import {Alert} from "react-bootstrap";

const PageNotification = ({variant, message, autoHide}) => {
    const [show, setShow] = useState(true);

    useEffect(() => {
        if (autoHide && show) {
            //TODO ANIMATE
            setTimeout(() => {
                setShow(false)
            }, autoHide)
        }
    })

    if (!show) {
        return null;
    }

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
    message: PropTypes.string,
    autoHide: PropTypes.number,
}

export default PageNotification;