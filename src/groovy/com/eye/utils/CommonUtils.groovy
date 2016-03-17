package com.eye.utils

import grails.util.Holders
import org.apache.commons.io.FileUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * Created by KS148 on 10/03/16.
 */
class CommonUtils {

    /**
     * For Uploading the image to the server
     */
    def uploadFile(request) {
        List results = []                   //Holds the file storage result
        def uploadFileSize                //Holds the size of file allowed
        if (request instanceof MultipartHttpServletRequest) {

            for(filename in request.getFileNames()) {

                //Retrieve fileName & its extension from request
                MultipartFile file =   request.getFile(filename)
                String fileName    =   file.originalFilename
                def dot            =   fileName.lastIndexOf(".")
                def namePart       =   (dot) ? fileName[0..dot-1] : fileName
                def extension      =   (dot) ? fileName[dot+1..fileName.length()-1] : ""
                fileName           =   "${namePart}"

                uploadFileSize    =   Holders.config.fileSize

                //Check if uploaded file size is less than the max limit specified
                if(file.size <= uploadFileSize)
                {
                    //Store the file content into "fileContentBytes" in Byte format
                    byte[] fileContentBytes = file.bytes

                    //Path of file storage on server
                    String path="${Holders.config.fileStoragePath}${File.separator}${fileName}"+"_"+new Date().format('yyyy-MM-dd hh-mm-ss')+"."+extension

                    //Write file to path
                    FileUtils.writeByteArrayToFile(new File(path), fileContentBytes)
                    results << [
                            fileName: fileName,
                            size: file.size,
                            path:path,
                            uploaded: true
                    ]
                }
                else {
                    results << [
                            fileName: fileName,
                            size: file.size,
                            uploaded: false
                    ]

                }
            }
        }
        return  results
    }
}
