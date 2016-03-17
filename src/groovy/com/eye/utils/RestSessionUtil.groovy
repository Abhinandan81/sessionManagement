package com.eye.utils

import com.eye.UserManagementService
import com.eye.auth.User
import com.eye.constants.CodeConstants
import grails.plugin.springsecurity.SpringSecurityService

/**
 * Created by KS115 on 3/9/16.
 */
class RestSessionUtil {
    static ServiceContext getServiceContext(def request, SpringSecurityService svc, UserManagementService ums) {

        if(!(svc?.principal instanceof String)) {
            ServiceContext sCtx
            def session = request.session

            if(!session.getAttribute(CodeConstants.SERVICE_CONTEXT_ATTRIBUTE_NAME)) {
                /* Construct the serviceContext */
                sCtx = getServiceContextFromTO( [userAgent : request?.getHeader('user-agent') ,
                                                 host : request?.getHeader("X-Forwarded-For") ?: request?.getRemoteAddr() ,
                                                 principalUsername : svc?.principal?.username ?: "" ,
                                                 principalAuthorities : svc?.principal.authorities ?: [:]], ums)
                sCtx = setServiceContext(request, sCtx)
            } else {
                /* Retrieving cached serviceContext from the session */
                sCtx = session.getAttribute(CodeConstants.SERVICE_CONTEXT_ATTRIBUTE_NAME)
            }
            return sCtx

        }else{
            return null
        }
    }

    static ServiceContext setServiceContext(def request, ServiceContext sCtx) {

        def session = request.session

        /* Cache  the serviceContext in the session  */
        session.setAttribute(CodeConstants.SERVICE_CONTEXT_ATTRIBUTE_NAME, sCtx)

        /*
         * The setMaxInactiveInterval function specifies the time, in seconds, between client requests
         *  before the servlet container will invalidate this session. We have set this threshold to an hour.
         */
        session.setMaxInactiveInterval(300);

        return sCtx
    }

    /**
     * Construct the ServiceContext object
     *
     * @param request	The request object is an instance of the Servlet API's HttpServletRequest interface.
     * @param eps		Entity provisioning service.
     * @return sCtx		ServiceContext instance.
     */
    static ServiceContext getServiceContextFromTO(Map request, UserManagementService ums) {

        String userName = request.principalUsername

        User user = null
        String mainRole = null
        Long companyId = null
        Long siteId = null
        String userTimeZone = null

        ServiceContext undetSCtx = new ServiceContext(
                userId : 'SYSTEM',
                userFullName: "Abhi System"
        )

        user = ums.findUserByUsername(undetSCtx, userName)
        request.principalAuthorities.each { nxtAuth ->

            /*--------------------------------------- ROLE_ADMIN ---------------------------------------*/
            if (nxtAuth.toString() == CodeConstants.ROLE_SUPER_ADMIN) {
                mainRole = CodeConstants.ROLE_SUPER_ADMIN
            }
            /*--------------------------------------- ROLE_COMPANY_ADMIN ---------------------------------------*/
            else if (nxtAuth.toString() == CodeConstants.ROLE_ADMIN) {
                mainRole = CodeConstants.ROLE_ADMIN
//                companyId = user.companyId
            }
            /*--------------------------------------- SPOKE USER ---------------------------------------*/
            else if (nxtAuth.toString() == CodeConstants.ROLE_INVENTORY_MANAGER) {
                mainRole = CodeConstants.ROLE_INVENTORY_MANAGER
//                companyId = user.companyId
//                siteId = user.siteId
            }
            /*---------------------------------------  ROLE_SITE_USER ---------------------------------------*/
            else if (nxtAuth.toString() == CodeConstants.ROLE_EYE_OWNER) {
                mainRole = CodeConstants.ROLE_EYE_OWNER
//                companyId = user.companyId
//                siteId = user.siteId
            }
        }

        def userFullName = user.userRealName

        /* Initialize the ServiceContext instance */
        ServiceContext sCtx = new ServiceContext(
                userAgent					: request.userAgent,
                host						: request.host,
//                companyId 					: companyId,
//                siteId                      : siteId,
                userId 						: userName,
                mainRole					: mainRole,
//                emailId						: "",
                userFullName 				: userFullName
        )
        return sCtx
    }

}
