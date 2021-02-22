import React from 'react';
import PropTypes from "prop-types";

import theme from "../../theme/base";

const classNamePrefix = 'ac-icon-svg';

const iconSizes = {
    'small': theme.global.icons.small,
    'medium': theme.global.icons.medium,
    'large': theme.global.icons.large,
    'xlarge': theme.global.icons.xlarge
};

const parseMetricToNum = string => parseFloat(string.match(/\d+(\.\d+)?/), 10);

const SvgIcon = ({children, color, a11yTitle, size, className, viewBox, overwriteSize, onClick, style}) => {

    let classNames = classNamePrefix;

    if (className) {
        classNames += ` ${className}`
    }

    if (!overwriteSize) {
        const [, , w, h] = (viewBox || '0 0 24 24').split(' ');
        const scale = w / h;
        const dimension = parseMetricToNum(iconSizes[size] || size);

        let width;
        let height;


        if (w < h) {
            width = `${dimension}px`;
            height = `${dimension}px`;
        } else if (h < w) {
            width = `${dimension * scale}px`;
            height = `${dimension}px`;
        } else {
            width = `${dimension}px`;
            height = `${dimension}px`;
        }

        return (
            <svg style={style} width={width} height={height} viewBox={viewBox} className={classNames}
                 aria-label={a11yTitle}>
                {children}
            </svg>
        );
    }

    return (
        <svg style={style} className={classNames} aria-label={a11yTitle} onClick={onClick}>
            {children}
        </svg>
    );

};

SvgIcon.propTypes = {
    className: PropTypes.string,
    color: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox: PropTypes.string,
    overwriteSize: PropTypes.bool,
    size: PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};

SvgIcon.defaultProps = {
    size: 'medium',
    overwriteSize: false,
    viewBox: '0 0 24 24'
};

export default SvgIcon
