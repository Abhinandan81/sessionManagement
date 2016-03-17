package com.eye

import com.eye.auth.RequestMap
import com.eye.auth.Role
import com.eye.auth.User
import com.eye.auth.UserRole
import com.eye.constants.CodeConstants
import com.eye.devicemap.Categories
import com.eye.utils.ServiceContext
import grails.transaction.Transactional

@Transactional
class UserManagementService {

    void bootstrapSystemRoles(){
        /* Create the SUPER_ADMIN user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_SUPER_ADMIN)) {
            def superAdminRole = new Role(authority: CodeConstants.ROLE_SUPER_ADMIN).save(flush: true)
        }

        /* Create the ADMIN user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_ADMIN)) {
            def adminRole = new Role(authority: CodeConstants.ROLE_ADMIN).save(flush: true)
        }

        /* Create the EYE_OWNER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_EYE_OWNER)) {
            def eyeOwnerRole = new Role(authority: CodeConstants.ROLE_EYE_OWNER).save(flush: true)
        }

        /* Create the EYE_CO_OWNER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_EYE_CO_OWNER)) {
            def eyeCoOwnerRole = new Role(authority: CodeConstants.ROLE_EYE_CO_OWNER).save(flush: true)
        }

        /* Create the ROLE_EYE_VIEWER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_EYE_VIEWER)) {
            def eyeViewerRole = new Role(authority: CodeConstants.ROLE_EYE_VIEWER).save(flush: true)
        }

        /* Create the ROLE_INVENTORY_MANAGER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_INVENTORY_MANAGER)) {
            def inventoryManagerRole = new Role(authority: CodeConstants.ROLE_INVENTORY_MANAGER).save(flush: true)
        }

        /* Create the ROLE_INSTALLATION_ENGINEER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_INSTALLATION_ENGINEER)) {
            def installationEngineerRole = new Role(authority: CodeConstants.ROLE_INSTALLATION_ENGINEER).save(flush: true)
        }

        /* Create the ROLE_INSTALLATION_ENGINEER user role. */
        if(!Role.findByAuthority(CodeConstants.ROLE_MAINTENANCE_ENGINEER)) {
            def maintenanceEngineerRole = new Role(authority: CodeConstants.ROLE_MAINTENANCE_ENGINEER).save(flush: true)
        }

        /* Create a super admin user. */
        User superAdmin = User.findByUsername(CodeConstants.SUPER_ADMIN_USER_NAME)
        if(!superAdmin) {
            superAdmin = new User(
                    username    : CodeConstants.SUPER_ADMIN_USER_NAME,
                    password    : CodeConstants.SUPER_ADMIN_COMPANY_NAME,
                    email       : "superadmin@spanpumps.com",
                    userRealName: CodeConstants.SUPER_ADMIN_USER_NAME,
                    enabled     : true
            )
            superAdmin.save(flush: true)
            def superAdminRole = Role.findByAuthority(CodeConstants.ROLE_SUPER_ADMIN)
            UserRole superAdminUserRole = UserRole.get(superAdmin.id, superAdminRole.id)
            if(!superAdminUserRole) {
                UserRole.create(superAdmin, superAdminRole, true)
            }
        }

        /* Create an admin user. */
        User admin = User.findByUsername(CodeConstants.ADMIN_USER_NAME)
        if(!admin) {
            admin = new User(
                    username    : CodeConstants.ADMIN_USER_NAME,
                    password    : CodeConstants.SUPER_ADMIN_COMPANY_NAME,
                    email       : "admin@spanpumps.com",
                    userRealName: CodeConstants.ADMIN_USER_NAME,
                    enabled     : true
            )
            admin.save(flush: true)
            def adminRole = Role.findByAuthority(CodeConstants.ROLE_ADMIN)
            UserRole adminUserRole = UserRole.get(admin.id, adminRole.id)
            if(!adminUserRole) {
                UserRole.create(admin, adminRole, true)
            }
        }

        /* Create an eye Owner user. */
        User eyeOwner = User.findByUsername(CodeConstants.EYE_OWNER_USER_NAME)
        if(!eyeOwner) {
            eyeOwner = new User(
                    username    : CodeConstants.EYE_OWNER_USER_NAME,
                    password    : CodeConstants.SUPER_ADMIN_COMPANY_NAME,
                    email       : "eyeowner@spanpumps.com",
                    userRealName: CodeConstants.EYE_OWNER_USER_NAME,
                    enabled     : true
            )
            eyeOwner.save(flush: true)
            def eyeRole = Role.findByAuthority(CodeConstants.ROLE_EYE_OWNER)
            UserRole eyeOwnerUserRole = UserRole.get(eyeOwner.id, eyeRole.id)
            if(!eyeOwnerUserRole) {
                UserRole.create(eyeOwner, eyeRole, true)
            }
        }

        /* Create an inventoryManager user. */
        User inventoryManager = User.findByUsername(CodeConstants.INVENTORY_MANAGER_USER_NAME)
        if(!inventoryManager) {
            inventoryManager = new User(
                    username    : CodeConstants.INVENTORY_MANAGER_USER_NAME,
                    password    : CodeConstants.SUPER_ADMIN_COMPANY_NAME,
                    email       : "eyeowner@spanpumps.com",
                    userRealName: CodeConstants.INVENTORY_MANAGER_USER_NAME,
                    enabled     : true
            )
            inventoryManager.save(flush: true)
            def inventoryManagerRole = Role.findByAuthority(CodeConstants.ROLE_INVENTORY_MANAGER)
            UserRole inventoryManagerUserRole = UserRole.get(inventoryManager.id, inventoryManagerRole.id)
            if(!inventoryManagerUserRole) {
                UserRole.create(inventoryManager, inventoryManagerRole, true)
            }
        }

        def firstCategory =  new Categories(categoryName: "Solar Pump").save(flush: true)
        def secondCategory =  new Categories(categoryName: "Solar Home").save(flush: true)


    }

    def addRoles(){
        try {
            //adding new url if it’s not already exist , for which we want to allocate role dynamically
            RequestMap requestMap = new RequestMap(actionUrl : "/inventory/upload")
            //finding role to assign for the URL
            Role role = Role.findByAuthority("ROLE_EYE_OWNER")
//Assigning “ROLE_EYE_OWNER” to URL  "/user/fetchExistingUserDetails"
            requestMap.addToRoles(role)
            requestMap.save(flush: true, failOnError: true)
            return true
            println "inside"
        }catch (Exception e){
            println "exception :"+e.printStackTrace()
            return  false
        }
    }

    User findUserByUsername(ServiceContext sCtx, String userName) {
        return User.findByUsername(userName)
    }
}
