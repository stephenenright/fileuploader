import React from "react";
import PropTypes from "prop-types";

import Spinner from "react-bootstrap/cjs/Spinner";
import ErrorMessage from "../message/error-message/ErrorMessage";
import classNames from "classnames";

const Panel = ({className, children}) => {

    return (
        <div className={
            classNames("ac-panel", {
                [className]: true
            })
        }>
            {children}
        </div>
    );
};


Panel.propTypes = {
    className: PropTypes.string,
}

const PanelTitle = ({title, className, icon, level}) => {

    const Element = `h${level}`

    if (title) {
        return (
            <div className={
                classNames("ac-panel__header-title", {
                    [className]: true
                })}>
                <Element level={level}>
                    {icon} {title}
                </Element>
            </div>
        )
    }

    return icon;
};

PanelTitle.propTypes = {
    level: PropTypes.number,
    title: PropTypes.string,
    className: PropTypes.string,
    icon: PropTypes.element
}

PanelTitle.defaultProps = {
    level: 2
}

const PanelHeader = ({className,level,title,icon,childrenOnly,children }) => {
    return (
        <div  className={
            classNames("ac-panel__header", {
                [className]: true
            })}>
            {!childrenOnly && (
                <PanelTitle level={level} title={title} icon={icon}/>
            )}
            {children}
        </div>
    );
};


PanelHeader.propTypes = {
    level: PropTypes.number,
    title: PropTypes.string,
    className: PropTypes.string,
    icon: PropTypes.element,
    childrenOnly: PropTypes.bool
}

PanelHeader.defaultProps = {
    level: 4
}


const PanelLoader = ({className, loader}) => {
    let Loader = loader;

    if(!loader) {
        Loader = (
            <Spinner animation="border" size="medium" variant="info">
                <span className="sr-only">Loading...</span>
            </Spinner>
        );

    }

    return (
        <div className={
            classNames("ac-panel__loader", {
                [className]: true
            })}>
            {Loader}
        </div>
    );
};


PanelLoader.propTypes = {
    className: PropTypes.string,
    loader: PropTypes.element
}


const PanelBody = ({className,loading,loader, children, error,errorMessage,onRetry}) => {
    const classes = classNames("ac-panel__body", {
        [className]: true
    });


    if (loading) {
        return (
            <div className={classes}>
                <div className="ac-panel__body-loader">
                    <PanelLoader loader={loader} />
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className={classes}>
                <ErrorMessage errorMessage={errorMessage} onRetry={onRetry} />
            </div>
        );
    }

    return (
        <div className={classes}>
            {children}
        </div>
    );
};


PanelBody.propTypes = {
    className: PropTypes.string,
    loading: PropTypes.bool,
    loader: PropTypes.element,
    error: PropTypes.bool,
    onRetry: PropTypes.func,
    errorMessage: PropTypes.string,
};

const PanelFooter = ({className,align, children }) => {
    const classNamePrefix = "ac-panel__footer";


    return (
        <div  className={
            classNames(classNamePrefix, {
                [`${classNamePrefix}--align-${align}`]: align,
                [className]: true
            })}>
            {children}
        </div>
    );
};


PanelFooter.propTypes = {
    className: PropTypes.string,
    align: PropTypes.oneOf(['start','center','end'])
}

Panel.Header = PanelHeader;
Panel.Title = PanelTitle
Panel.Body = PanelBody;
Panel.Footer = PanelFooter;
Panel.Loader = PanelLoader;

export default Panel;
