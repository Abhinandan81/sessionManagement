$(function () {
    var uploadHandeler;
    var filename;
    $('.file-btn').fileupload({
        dataType: 'json',

        // The regular expression for allowed file types, matches
        // against either file type or file name:
        acceptFileTypes: /(\.|\/)(jpeg|png|bmp|jpg|gif|bpg)$/i,
        // The maximum allowed file size in bytes:
        maxFileSize: 10000000, // 10 MB

        // The minimum allowed file size in bytes:
        minFileSize: undefined, // No minimal file size

        // The limit of files to be uploaded:
        maxNumberOfFiles: 10,

        done: function (e, data) {

            if(data.result.length > 0){
                url = data.result[0]['path'];
                uploadHandeler = data.result[0]['uploaded'];
                filename = data.result[0]['fileName'];

                if(uploadHandeler){
                    console.log("Image uploaded successfully :-"+ filename);
                }
                else{
                    console.log("Fail to upload " +filename);
                }
            }
        },
        error: function (e, data) {
            // doShowAlert("Import fail", "Import fail please check uploaded xml file must compatible with MAT 4.0","krxErrorIcon");
        },
        progressall: function (e, data) {

        }
    }).on('fileuploadprocessalways', function (e, data) {

        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
            // there was an error, do something about it
            console.log("Error : " + currentFile.error);
        }
    });


    $('.file-button').fileupload({
        // The regular expression for allowed file types, matches
        // against either file type or file name:
        acceptFileTypes : /(\.|\/)(xls|xlsx)$/i,
        // The maximum allowed file size in bytes:
        maxFileSize: 10000000, // 10 MB

        // The minimum allowed file size in bytes:
        minFileSize: undefined, // No minimal file size

        // The limit of files to be uploaded:
        maxNumberOfFiles: 10,

        done: function (e, data) {

            if(data.result.length > 0){
                var status = data['textStatus'];
                if( status == "success" ) {
                    console.log("File uploaded successfully");
                } else{
                    console.log("Fail to upload ");
                }
            }
        },
        error: function (e, data) {
            // doShowAlert("Import fail", "Import fail please check uploaded xml file must compatible with MAT 4.0","krxErrorIcon");
        },
        progressall: function (e, data) {

        }
    }).on('fileuploadprocessalways', function (e, data) {

        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
            // there was an error, do something about it
            console.log("Error : " + currentFile.error);
        }
    });

});