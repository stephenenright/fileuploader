import React from "react";
import { render, screen } from '@testing-library/react';

import FileListItem from "./FileListItem";


test('renders file list item', () => {
    const file = {
        id: 1,
        title: "File 1 Title",
        description: "File 2 Description",
        size: 3376,
        extension: 'txt',
        downloadUrl: "https://localhost:8080/api/files/storage/1",
        creationDate: new Date(2021, 1, 18, 19 , 24, 0)
    }
    render(<FileListItem file={file} />);
    expect(screen.getByText('File 1 Title')).toBeDefined();
    expect(screen.getByText('File 2 Description')).toBeDefined();
    expect(screen.getByText((_, node) => node.textContent === 'File Size:3.30KB')).toBeDefined();
    expect(screen.getByText((_, node) => node.textContent === 'Extension:txt')).toBeDefined();
    expect(screen.getByTitle('Download File').getAttribute("href")).toBe("https://localhost:8080/api/files/storage/1")
});
