# File Uploader

This a multi module project for a simple file uploader.


## Important Configuration Options

* fileUploader.media.mediaFiles.useFileExt: Set to true to save a file with its extension to storage such as the file system.
* fileUploader.media.mediaFiles.fileStorage.directory: The directory where files are stored. By default files are saved to the system tmp directory.
* fileUploader.media.mediaFiles.allowedExtValidator: A regular expression such as txt|png that if set will validate the file extension to validate that it is allowed
  

## Building And Running
First clone the repository


### Production
From the directory parent/file-uploader-parent run:

```
mvn clean install
```

The above command will package the frontend and backend into a single jar file. To start the project navigate to the
directory web/fileuploader-web and run

```
java -jar ./target/fileuploader-web-0.0.1-SNAPSHOT.jar
```

Open a browser and go to:

```
http://localhost:8080
```

### Development
Import the project into your IDE of choice. Open the fileloader-web project and run the class 
```FileUploaderApplication``` to start the spring boot application backend.

From the terminal you need to start the frontend. Navigate to web/fileuploader-web/frontend and run:

```
npm start
```

this will start the react in the browser http://localhost:3000


