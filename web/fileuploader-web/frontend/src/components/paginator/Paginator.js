import React from "react";
import PropTypes from "prop-types";

import classNames from "classnames";
import Button from "react-bootstrap/cjs/Button";
import {PreviousIcon, NextIcon} from "../icons/AppIcons";

const classNamePrefix = "ac-paginator";

const Paginator = ({total = 0, page = 0, pageSize = 0, totalPages = 0, align = 'start', onChange}) => {

    let finish = ((page + 1) * pageSize) <= total ? ((page + 1) * pageSize) : total;
    let start = page === 0 ? 1 : pageSize * page + 1;

    return (
        <div className={
            classNames(classNamePrefix, {
                [`${classNamePrefix}--align-${align}`]: align
            })
        }>
            <>
                { `${start} - ${finish} of ${total}` } &nbsp;
                <Button
                    onClick={ e => onChange(page -1) }
                    variant="secondary"
                    size="sm"
                    disabled={ !page }>
                    <PreviousIcon size="medium" />
                </Button>
                <Button
                    onClick={ e => onChange(page + 1) }
                    variant="secondary"
                    size="sm"
                    disabled={ (page + 1) >= totalPages }>
                    <NextIcon size="medium" />
                </Button>
            </>
        </div>
    );
}

Paginator.propTypes = {
    page: PropTypes.number,
    pageSize: PropTypes.number,
    totalPages: PropTypes.number,
    align: PropTypes.oneOf(['start', 'end']),
    onChange: PropTypes.func,
    total: PropTypes.number,
}

export default Paginator;