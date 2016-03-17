package com.eye

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.test.spock.IntegrationSpec
import org.springframework.web.context.request.RequestContextHolder

class ProvisionIntegrationSpec extends IntegrationSpec {

    ProvisionController provisionController
    def springSecurityService

    def setup()
    {
        provisionController = new ProvisionController()
        provisionController.springSecurityService = springSecurityService
    }

   /* def "test for checking admin authentication"()
    {
        when:

        SpringSecurityUtils.doWithAuth('eye_owner') {
            provisionController.managerACL()
        }

     *//*   springSecurityService.reauthenticate("eye_owner")
        RequestContextHolder.currentRequestAttributes().session.User
        provisionController.managerACL()*//*

        then:
        provisionController.response.text == 'You have role ROLE_ADMIN or SUPER_ADMIN'

    }*/

    def "test create method with required role"() {
        setup:
        // tell Spring to behave as if the user has the required role(s)
        //SpringSecurityUtils.metaClass.static.ifAllGranted('ROLE_EYE_OWNER')

        when:
        if(SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
            provisionController.managerACL()
        }

        then:
        println "***************Coming Here**************"
        // with the required role(s), what does the controller return?
        provisionController.response.status == 200
//        controller.response.mimeType.name == "application/json"
        provisionController.response.getText() == 'You have role ROLE_ADMIN or SUPER_ADMIN'

        cleanup:
        SpringSecurityUtils.metaClass = null
    }
}
