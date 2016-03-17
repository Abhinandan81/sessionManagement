package com.eye.inventory

class EyeDetails {
    String  eyeManufacturingId
    String  eyeFirmwareCategory
    String  eyeFirmwareVersion
    Date    eyeManufactureDate
    String  eyeDescription

    static constraints = {
        eyeManufacturingId nullable: false
        eyeManufactureDate nullable: false
        eyeDescription nullable: false
    }
}
