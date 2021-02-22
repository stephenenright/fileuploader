import React from "react";
import PropTypes from "prop-types";

import Panel from "../../../../components/panel";
import {Form, Col, Spinner, Button} from "react-bootstrap/cjs";
import Uploader from "../../../../components/uploader";
import {resolveError} from "../../../../libs/error/ErrorLib";
import DateTimeControl from "../../../../components/input/datetime/DateTimeControl";

const UploadForm = ({dataset, onChange, onUpload, validationErrors, uploadProgress, loading}) => {
    return (
        <Form onSubmit={onUpload}>
            <Panel>
                <Panel.Header title="File Information"/>
                <Panel.Body>
                    <Form.Row>
                        <Form.Group as={Col} md="6">
                            <FormField label="Title" name="title" validationErrors={validationErrors} required>
                                <Form.Control
                                    name="name"
                                    value={dataset.name}
                                    onChange={(e) => onChange("title", e.target.value)}
                                    type="text"
                                    placeholder="Enter Title"
                                    disabled={loading}
                                />
                            </FormField>
                        </Form.Group>
                        <Form.Group as={Col} md="6">
                            <FormField label="Creation Date" name="creationDate" validationErrors={validationErrors} required>
                                <DateTimeControl
                                    name="creationDate"
                                    value={dataset.creationDate || new Date()}
                                    onChange={(e) => onChange("creationDate", e)}
                                    disabled={loading}
                                />
                            </FormField>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="12">
                            <FormField label="Description" name="description" validationErrors={validationErrors}>
                                <Form.Control
                                    name="description"
                                    value={dataset.description}
                                    onChange={(e) => onChange("description", e.target.value)}
                                    as="textarea"
                                    rows="5"
                                    placeholder="Enter Description"
                                    disabled={loading}
                                />
                            </FormField>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="12">
                            <Uploader
                                progress={uploadProgress}
                                error={resolveError(validationErrors, "file")}
                                onUpload={(files) => {onChange("files", files)}}/>
                        </Form.Group>
                    </Form.Row>
                </Panel.Body>
                <Panel.Footer align="center">
                    <UploadButton type="submit" variant="primary" loading={loading}/>
                </Panel.Footer>
            </Panel>
        </Form>
    );
}

UploadForm.propTypes = {
    dataset: PropTypes.object,
    onChange: PropTypes.func,
    validationErrors: PropTypes.object,
    uploadProgress: PropTypes.number
}


const FormField = ({required, label, name, validationErrors = {}, children}) => {
    const validationError = validationErrors[name]

    const control = React.Children.only(React.cloneElement(
        children, {
            ...validationError && {isInvalid: true},
        }
    ))

    return (
        <React.Fragment>
            <Form.Label aria-label={label} htmlFor={name}>{required && <span className="ac-form-required">*</span>} {label}</Form.Label>
            {control}

            {validationError && (
                <Form.Control.Feedback type="invalid">
                    {validationError.errors[0] || `${name} is invalid`}
                </Form.Control.Feedback>
            )}
        </React.Fragment>
    )
}

const UploadButton = ({loading, ...rest}) => {
    return (
        <Button {...rest} disabled={loading}>
            {loading && (
                <>
                    <Spinner as="span"
                             animation="border"
                             size="sm"
                             role="status"
                             aria-hidden="true"
                    /> <span>Uploading ...</span>
                </>
            )}
            {!loading && 'Upload'}
        </Button>
    )
}


export default UploadForm;