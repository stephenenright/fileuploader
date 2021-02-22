import React from "react";
import PropTypes from "prop-types";
import {ErrorBoundary} from 'react-error-boundary'

import ErrorMessage from "../components/message/error-message/ErrorMessage";

export const AppErrorBoundary = ({onReset, children}) => {
    return (
        <ErrorBoundary
            FallbackComponent={({resetErrorBoundary}) =>
                <div className="app-error-boundary">
                    <ErrorMessage
                        variant="danger"
                        onRetry={() => {
                            resetErrorBoundary();
                        }}
                    />
                </div>
            }
            onReset={onReset}
        >
            {children}
        </ErrorBoundary>
    )
}

AppErrorBoundary.propTypes = {
    onReset: PropTypes.func,
}


export default AppErrorBoundary;