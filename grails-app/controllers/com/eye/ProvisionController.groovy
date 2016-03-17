package com.eye

import com.eye.devicemap.Categories
import com.eye.devicemap.DeviceInterpretation
import com.eye.inventory.EyeDetails
import com.eye.utils.CommonUtils
import com.eye.utils.RestSessionUtil
import com.eye.utils.ServiceContext
import grails.converters.JSON
import grails.util.Holders
import org.apache.commons.io.FileUtils
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

@Secured(['permitAll'])
class ProvisionController {

    def springSecurityService
    def userManagementService
    def deviceManagementService
    def commonUtils

    @Secured(['permitAll'])
    def index() {
        println  'You are authenticated'
        userManagementService.addRoles()
    }

   @Secured(['ROLE_EYE_OWNER', 'ROLE_ADMIN'])
    def eye() {
        render 'You have role EYE or ROLE_ADMIN or SUPER_ADMIN'
    }

    @Secured(['ROLE_ADMIN'])
    def managerACL() {
        render 'You have role ROLE_ADMIN or SUPER_ADMIN'
    }

    @Secured(['ROLE_EYE_OWNER'])
    def owner() {
        render 'You have role EYE_OWNER or SUPER_ADMIN'
    }

    @Secured(['ROLE_SUPPORT_STAFF'])
    def supportStaff() {
        render 'You have SUPPORT_STAFF or SUPER_ADMIN'
    }

    @Secured(['ROLE_INSTALLATION_ENGINEER'])
    def installationEngineer() {
        render 'You have role INSTALLATION_ENGINEER or SUPER_ADMIN'
    }

    /**
     * For testing the file upload to server
     */
    @Secured(['ROLE_SUPER_ADMIN'])
    def fileUploadTest() {   }

    /**
     * For Uploading the image to the server
     * @return
     */
    @Secured(['ROLE_SUPER_ADMIN'])
    def uploadFile() {
        commonUtils = new CommonUtils()
        List results = commonUtils.uploadFile(request)
        render results as JSON
    }


    /**
     * For testing graph download
     */
    @Secured(['permitAll'])
    def downLoadGraphTest() {

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def downLoadChart() {
        String chartID = params.chartId
        String outputFormat = params.outputFormat
        String resourcePath = params.path

        "phantomjs /home/KS148/dev/krixi/grails_projects/eye/web-app/js/chartDownLoad/report.js http://localhost:8080/eye/${resourcePath} ${chartID} /home/KS148/dev/krixi/dougnutChart.${outputFormat}".execute()
        render "done"
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def deviceMapCreation() {   }

    /**
     * For fetching the existing device categories
     * @return : category -> Category list
     */
    @Secured(['ROLE_SUPER_ADMIN'])
    def fetchDeviceCategories() {
        def category = Categories.getAll().categoryName
        render category as JSON
    }

    /**
     * Assigning new device map to an existing Device
     * @return : rendering newly created device and it's interpretation String
     */
    @Secured(['permitAll'])
    def setDeviceMap() {
        String deviceCategory   = 'Solar Pump'
        String deviceMap        = 'name:abhi'
        def deviceInterpretation= deviceManagementService.persistingDeviceInterpretation(deviceCategory, deviceMap)
        render deviceInterpretation
    }

    /**
     * Fetching existing device interpretations
     * @return : rendering deviceInterpretationList
     */
    @Secured(['permitAll'])
    def fetchDeviceMap() {
        List deviceInterpretationList   =   deviceManagementService.fetchDeviceInterpretations()
        render deviceInterpretationList as JSON
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def fetchUniqueFirmwares(){
        ServiceContext sCtx = RestSessionUtil.getServiceContext(request, springSecurityService, userManagementService)
        println "Role :"+sCtx.mainRole
        List firmware    =   deviceManagementService.fetchingUniqueFirmwares()
        render firmware as JSON
    }
}
