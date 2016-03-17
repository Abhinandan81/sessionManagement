package com.eye

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ProvisionController)
class ProvisionControllerSpec extends Specification {
    def setup() {
    }

  /*  void "test admin role"() {

        when:
        SpringSecurityUtils.reauthenticate 'superAdmin', 'spanPumps'
        controller.eye()

        then:
        assert response.text() == 'premium content'
    }
*/
    def "test create method with required role"() {
        setup:
        // tell Spring to behave as if the user has the required role(s)
        SpringSecurityUtils.metaClass.static.ROLE_EYE_OWNER = { String role ->
            return true
        }

        when:
        controller.managerACL()

        then:
        // with the required role(s), what does the controller return?
        controller.response.status == 200
//        controller.response.mimeType.name == "application/json"
        controller.response.getText() == "You have role ROLE_ADMIN or SUPER_ADMIN"

        cleanup:
        SpringSecurityUtils.metaClass = null
    }
}
