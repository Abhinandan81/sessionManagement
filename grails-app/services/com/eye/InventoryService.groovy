package com.eye

import com.eye.constants.CodeConstants
import com.eye.inventory.EyeDetails
import grails.transaction.Transactional
import jxl.DateCell
import jxl.LabelCell
import jxl.Sheet
import jxl.Workbook

@Transactional
class InventoryService {

    /**
     * persisting Inventory Data From Excel Sheet.
     * @param  : excel file
     * @return : excel file persistence status
     */
    Boolean persistingInventoryDataFromExcel(def file){
        try {
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet(0);

            /* skip first row (row 0) by starting from 1.
               String type data stored in LabelCell, Date type data stored in DateCell and
               Number type data stored in NumberCell.
               Indexing has done for the columns, which helps in identifying cell.
             */
            for (int row = 1; row < sheet.getRows(); row++) {
                LabelCell eyeManufacturingId    =   sheet.getCell(CodeConstants.COLUMN_EYE_MANUFACTURING_ID, row)
                LabelCell eyeFirmwareCategory   =   sheet.getCell(CodeConstants.COLUMN_EYE_FIRMWARE_CATEGORY, row)
                LabelCell eyeFirmwareVersion    =   sheet.getCell(CodeConstants.COLUMN_EYE_FIRMWARE_VERSION, row)
                DateCell  eyeManufactureDate    =   sheet.getCell(CodeConstants.COLUMN_EYE_MANUFACTURING_DATE, row)
                LabelCell eyeDescription        =   sheet.getCell(CodeConstants.COLUMN_EYE_DESCRIPTION, row)

                //Once details of a row retrieved, persist them to database
                new EyeDetails(eyeManufacturingId : eyeManufacturingId.string, eyeFirmwareCategory : eyeFirmwareCategory.string,
                        eyeFirmwareVersion : eyeFirmwareVersion.string, eyeManufactureDate : eyeManufactureDate.date,
                        eyeDescription : eyeDescription.string).save(flush: true, failOnError: true)
            }
            return true
        }catch (Exception e){
            println "Exception while persisting Excel data to Database"
            return false
        }
    }
}
