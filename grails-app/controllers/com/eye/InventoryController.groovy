package com.eye

import com.eye.inventory.EyeDetails
import com.eye.utils.CommonUtils
import grails.plugin.springsecurity.annotation.Secured

class InventoryController {
    def inventoryService
    def commonUtils

    @Secured(['ROLE_INVENTORY_MANAGER'])
    def existingInventoryDetails() {
        redirect(action: "list", params: params)
    }

    @Secured(['ROLE_INVENTORY_MANAGER'])
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [inventoryList: EyeDetails.list(params), inventoryTotal: EyeDetails.count()]
    }

    /**
     * Inventory Excel upload view
     */
    @Secured(['ROLE_INVENTORY_MANAGER'])
    def upload() {
    }

    /**
     * uploading excel file and storing it's content to the database
     * @return : Excel Upload Status - successful or not
     */
    @Secured(['ROLE_INVENTORY_MANAGER'])
    def doUpload() {
        //input excel file
        def file = request.getFile('fileUploader')

        //For saving the file to server directory

        commonUtils = new CommonUtils()

        List result = commonUtils.uploadFile(request)

        println "File Stored"
        //persisting Inventory Data From Excel
        Boolean uploadStatus    =   inventoryService.persistingInventoryDataFromExcel(file)
        redirect (action:'list')

        render uploadStatus
    }
}
