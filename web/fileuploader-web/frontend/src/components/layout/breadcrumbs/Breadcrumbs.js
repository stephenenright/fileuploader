import React from "react";
import PropTypes from "prop-types";

import ContentWrapper from "../wrapper";
import classNames from "classnames";

const Breadcrumbs = ({className, breadcrumbs}) => {

    const resolvedBreadcrumbs = breadcrumbs.map(function(bc, index) {
        let span = index > 0 ? <span className="ac-breadcrumb__divider">-</span> : null;
        return (
            <li  key={index}>
                {span}
                {
                    bc.onClick
                        ? <a title={bc.label} onClick={bc.onClick}  className="ac-breadcrumb__link">{bc.label}</a>
                        : <span className="ac-breadcrumb__static">{bc.label}</span>
                }
            </li>
        );
    });

    return (
        <div className={
            classNames("ac-breadcrumbs", {
                [className]: true
            })}>
            <ContentWrapper>
                <ul className="ac-breadcrumbs-list">
                    {resolvedBreadcrumbs}
                </ul>
            </ContentWrapper>
        </div>
    );
}

Breadcrumbs.propTypes = {
    breadcrumbs: PropTypes.array,
    className: PropTypes.string
};

Breadcrumbs.defaultProps = {
    breadcrumbs: []
};

export default Breadcrumbs;