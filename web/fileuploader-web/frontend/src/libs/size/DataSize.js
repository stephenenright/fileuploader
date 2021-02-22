const formatBytes = (size) => {
    let sizes = 1024;

    if (Math.abs(size) < sizes) {
        return size + 'B';
    }

    let units = ['KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    let u = -1;

    do {
        size /= sizes;
        ++u;
    } while (Math.abs(size) >= sizes && u < units.length - 1);

    return new String(size.toFixed(2)) +  units[u];
}

export {formatBytes}


