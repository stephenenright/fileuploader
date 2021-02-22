import React from "react";
import PropTypes from "prop-types";

import SvgIcon from "./SvgIcon";

const FileIcon = (props) => {
    const {
        stroke
    } = props;


    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='File' {...props}>
            <path fill='none' stroke={stroke} strokeWidth='2' d='M1,23 L16,23 L23,16 L23,1 L1,1 L1,23 Z M15,23 L15,15 L23,15' />
        </SvgIcon>
    )
};


FileIcon.propTypes = {
    className: PropTypes.string,
    stroke: PropTypes.string,
    a11yTitle: PropTypes.string,
    overwriteSize: PropTypes.bool,
    size: PropTypes.oneOf(['small', 'medium', 'large', 'xlarge'])
}

const AddIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Add' {...props}>
            <path fill='none' strokeWidth='2' d='M12,22 L12,2 M2,12 L22,12' />
        </SvgIcon>
    )
};


AddIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};


const DownloadIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Download' {...props}>
            <path fill='none' strokeWidth='2' d='M1,17 L1,23 L23,23 L23,17 M12,2 L12,19 M5,12 L12,19 L19,12' />
        </SvgIcon>
    )
};


DownloadIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};


const PreviousIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Previous' {...props}>
            <polyline fill='none' strokeWidth='2' points='9 6 15 12 9 18' transform='matrix(-1 0 0 1 24 0)' />
        </SvgIcon>
    )
};


PreviousIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};

const NextIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Next' {...props}>
            <polyline fill='none'  strokeWidth='2' points='9 6 15 12 9 18' />
        </SvgIcon>
    )
};


NextIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};


const TrashIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Trash' {...props}>
            <path fill='none' strokeWidth='2' d='M4,5 L20,5 L20,23 L4,23 L4,5 Z M1,5 L23,5 M9,1 L15,1 L15,5 L9,5 L9,1 Z M9,1 L15,1 L15,5 L9,5 L9,1 Z M15,9 L15,19 M9,9 L9,19' />
        </SvgIcon>
    )
};


TrashIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};

const CloseIcon = (props) => {
    return (
        <SvgIcon viewBox='0 0 24 24' a11yTitle='Close' {...props}>
            <path fill='none' strokeWidth='2' d='M7,7 L17,17 M7,17 L17,7' />
        </SvgIcon>
    )
};

CloseIcon.propTypes = {
    className: PropTypes.string,
    a11yTitle: PropTypes.string,
    viewBox:   PropTypes.string,
    overwriteSize: PropTypes.bool,
    size:      PropTypes.oneOf(['small', 'medium', 'large', 'xlarge']),
};


export {
    FileIcon,
    AddIcon,
    DownloadIcon,
    PreviousIcon,
    NextIcon,
    TrashIcon,
    CloseIcon
}