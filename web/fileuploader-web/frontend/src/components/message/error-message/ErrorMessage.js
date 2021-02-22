import React from 'react';
import PropTypes from 'prop-types';

import Button from "react-bootstrap/cjs/Button";
import classNames from "classnames";

const classNamePrefix = "ac-error-message";

const ErrorMessage = ({className, errorMessage, onRetry, variant}) => {
    return (
        <div className={
            classNames(classNamePrefix, {
                [`${classNamePrefix}--${variant}`]: variant,
                [className]: true
            })}>
            <div className="ac-error-message__box">
                <h4 className="ac-error-message__title">Oops..</h4>
                <div className="ac-error-message__body">
                    {errorMessage || 'An unknown error occurred'}
                </div>
                {onRetry && (
                    <div className="ac-error-message__retry">
                        <Button onClick={onRetry} variant="secondary">
                            Try Again
                        </Button>
                    </div>
                )}
            </div>
        </div>
    )
};

ErrorMessage.propTypes = {
    errorMessage: PropTypes.string,
    variant: PropTypes.string,
    onRetry: PropTypes.func,
};

ErrorMessage.defaultProps = {
    variant: 'danger'
};


export default ErrorMessage;


