import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

const PageLayout = ({className, children}) => {
    return (
        <div className={
            classNames("ac-page_layout", {
                [className]: true
            })
        }>
            {children}
        </div>
    );
};

PageLayout.propTypes = {
    className: PropTypes.string
};

const PageContent = ({className, children}) => {
    return (
        <div className={
            classNames("ac-page_content", {
                [className]: true
            })
        }>
            {children}
        </div>
    );

};

PageContent.propTypes = {
    className: PropTypes.string
};


const PageTitle = ({ className, renderTitle, title, icon, showBorder, marginBottom, paddingBottom, children}) => {
    const classNamePrefix = "ac-page_title-wrapper";

    return (
        <div className={
            classNames(classNamePrefix, {
                [`${classNamePrefix}--bordered`]: showBorder,
                [`${classNamePrefix}--marginBottom`]: marginBottom,
                [`${classNamePrefix}--paddingBottom`]: paddingBottom,
                [className]: true
            })
        }>
            <div className="ac-page_title">
                {renderTitle && (<React.Fragment>{icon} <h1>{title}</h1></React.Fragment>)}
            </div>
            {children}
        </div>
    );
};

PageTitle.propTypes = {
    className: PropTypes.string,
    renderTitle: PropTypes.bool,
    title: PropTypes.string,
    icon: PropTypes.element,
    showBorder: PropTypes.bool,
    marginBottom: PropTypes.bool,
    paddingBottom: PropTypes.bool,
};

PageTitle.defaultProps = {
    renderTitle: true,
    showBorder: true,
    marginBottom: true,
};


PageLayout.Content = PageContent;
PageLayout.Title = PageTitle;

export default PageLayout;