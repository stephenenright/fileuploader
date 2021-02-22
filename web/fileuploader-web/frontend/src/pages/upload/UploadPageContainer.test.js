import React from "react";
import {render, fireEvent, screen, act} from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import mockAxios from 'axios';

import UploadPageContainer from "./UploadPageContainer";
import PageContext from "../../context/page-context";


describe('UploadPageContainer' , () => {

    function TestUploadPageContainer(setPage) {
        return (
            <PageContext.Provider value={{page: 0, setPage}}>
                <UploadPageContainer />
            </PageContext.Provider>
        )
    }

    test('upload success', async () => {
        const responseObj = {
            data: {
                success: {
                    message: "Media File Uploaded Successfully"
                }
            }
        };

        mockAxios.post.mockImplementationOnce(() =>
            Promise.resolve(responseObj)
        );

        const setPage = jest.fn();
        render(<TestUploadPageContainer  setPage={setPage} />);


        const file = new File([''], 'test.png', {
            type: 'image/png',
        })

        const fileDropzoneInput = screen.getByTestId('uploader-dropzone').querySelector('input');
        const titleInput = screen.getByLabelText("Title").closest("div").querySelector('input');
        const uploadButton = screen.getByRole('button', {
            type: 'submit'
        });

        await act(async () => {
            //without await the events are out of sync
            await fireEvent.change(fileDropzoneInput, {
                target: { files: [file] },
                preventDefault: () => {},
                persist: () => {},
            });

            userEvent.type(titleInput, "File 1");
            userEvent.type(titleInput, "File 1");
            await fireEvent.click(uploadButton)

            expect(mockAxios.post).toBeCalledTimes(1)
        })

        expect(screen.getByRole('alert').textContent).toBe(responseObj.data.success.message)
    });


    test('Bad upload request renders errors', () => {
        const setPage = jest.fn();
        render(<TestUploadPageContainer setPage={setPage} />);

        const uploadButton = screen.getByRole('button', {
            type: 'submit'
        });

        fireEvent.click(uploadButton);
        //expect(screen.getByText("Validation Faileddsfds")).toBeDefined();
        expect(screen.getByRole('alert').textContent).toBe("Validation Failed");
        expect(screen.getByLabelText("Title").closest("div").querySelector('.invalid-feedback').textContent)
            .toBe('Must not be empty');
        expect(screen.getByText("Please select a file to upload")).toBeDefined();
        expect(setPage).toHaveBeenCalledTimes(0);
    });
});




