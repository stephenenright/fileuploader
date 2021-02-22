import React from "react";
import PropTypes from "prop-types";

import PageNotification from "./PageNotification";

const PageNotifications = ({notifications, autoHide}) => {
    if (!notifications || notifications.length ===0) {
        return null;
    }

    const resolvedNotifications = notifications.map((n, index) => {
        if(n.error && n.error.message) {
            return (
                <PageNotification key={index} autoHide={autoHide} variant="danger" message={n.error.message} id={n.id} />
            )
        } else if (n.success && n.success.message) {
            return (
                <PageNotification key={index} autoHide={autoHide} variant="success" message={n.success.message} />
            )
        } else {
            return null;
        }
    });

    return (
        <div className="ac-page-notifications">
            {resolvedNotifications}
        </div>
     );
}


PageNotifications.propTypes = {
  notifications: PropTypes.array,
  autoHide: PropTypes.number,
};


export default PageNotifications;