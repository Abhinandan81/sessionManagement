package com.eye

import com.eye.devicemap.Categories
import com.eye.devicemap.DeviceInterpretation
import com.eye.inventory.EyeDetails
import grails.transaction.Transactional

@Transactional
class DeviceManagementService {

    /**
     * Persisting Device interpretation
     * @param deviceName
     * @param interpretation
     * @return : newly create deviceInterpretation
     */
    def persistingDeviceInterpretation(String deviceName, String interpretation) {
        def category = Categories.findByCategoryName(deviceName)
        def deviceInterpretation = new DeviceInterpretation(deviceMap: interpretation, categoryID : category.id).save(flush: true, failOnError: true)
        return deviceInterpretation
    }

    /**
     * Fetching existing device interpretations
     * @return : List of deviceInterpretations
     */
    List fetchDeviceInterpretations(){
        List deviceInterpretation   =   DeviceInterpretation.getAll()
        return deviceInterpretation
    }

    List fetchingUniqueFirmwares(){
        List firmwareList    =   EyeDetails.withCriteria {
            projections{
                distinct(['eyeFirmwareCategory', 'eyeFirmwareVersion'])
            }
        }
        return firmwareList
    }
}
