import React from "react";
import PropTypes from "prop-types";

import {Form} from "react-bootstrap/cjs";
import DateTime from "react-datetime";

const DateTimeControl = ({value, onChange, ...rest}) => {
    return (
        <DateTime
            className="ac-datetime-control"
            value={value}
            onChange={onChange}
            renderInput={(props) => {
                return (<Form.Control
                            autoComplete="off"
                            {...rest}
                            {...props}
                        />
                )
            }}/>
    );
}

export default DateTimeControl;


