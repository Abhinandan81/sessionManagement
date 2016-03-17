package com.eye.auth

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import grails.transaction.Transactional
import groovy.time.TimeCategory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Transactional
class AuthenticationTokenStorageService implements TokenStorageService, GrailsApplicationAware {

    GrailsApplication grailsApplication
    UserDetailsService userDetailsService

    /**
     * Get the username for a token and token that's expired based on the configuration will
     * be treated like a non-existing token.
     *
     * @param tokenValue
     * @return
     * @throws grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
     */
    @Override
    UserDetails loadUserByToken(String tokenValue) throws TokenNotFoundException {

        def conf = SpringSecurityUtils.securityConfig
        String usernamePropertyName = conf.rest.token.storage.gorm.usernamePropertyName
        String dateCreatedPropertyName = conf.rest.token.storage.gorm.dateCreatedPropertyName
        def existingToken = findExistingToken(tokenValue)

        if (existingToken) {

            def username = existingToken."${usernamePropertyName}"
            //A token that's past it's expiration time is the same as one that doesn't exist.
            def expiryMinutes = conf.rest.token.expiry as Integer

            if( expiryMinutes ) {
                use(TimeCategory) {

                    if( (existingToken."${dateCreatedPropertyName}" + expiryMinutes.minutes) <= new Date() ) {
                        throw new TokenNotFoundException( "Token with the value: ${tokenValue} has expired." )
                    }
                }
            }

            return userDetailsService.loadUserByUsername(username)

        }

        throw new TokenNotFoundException("Token ${tokenValue} not found")

    }

    /**
     * Store or update a token for a user.
     *
     * @param tokenValue
     * @param principal
     */
    @Override
    void storeToken(String tokenValue, UserDetails principal) {

        def conf = SpringSecurityUtils.securityConfig
        String tokenClassName = conf.rest.token.storage.gorm.tokenDomainClassName
        String tokenValuePropertyName = conf.rest.token.storage.gorm.tokenValuePropertyName
        String usernamePropertyName = conf.rest.token.storage.gorm.usernamePropertyName
        String dateCreatedPropertyName = conf.rest.token.storage.gorm.dateCreatedPropertyName
        def dc = grailsApplication.getClassForName(tokenClassName)

        //TODO check at startup, not here
        if (!dc) {
            throw new IllegalArgumentException("The specified token domain class '$tokenClassName' is not a domain class ")
        }

        dc.withTransaction { status ->

            def existingUser = findExistingUser(principal.username)
            if (existingUser) {
                existingUser."${tokenValuePropertyName}" = tokenValue
                existingUser."${dateCreatedPropertyName}" = new Date()
                existingUser.save()
            } else {
                def newTokenObject = dc.newInstance((tokenValuePropertyName): tokenValue, (usernamePropertyName): principal.username)
                newTokenObject.save()
            }
        }
    }

    /**
     * Remove a token.
     *
     * @param tokenValue
     * @throws TokenNotFoundException
     */
    @Override
    void removeToken(String tokenValue) throws TokenNotFoundException {

        def conf = SpringSecurityUtils.securityConfig
        String tokenClassName = conf.rest.token.storage.gorm.tokenDomainClassName
        def existingToken = findExistingToken(tokenValue)

        if (existingToken) {

            def dc = grailsApplication.getClassForName(tokenClassName)
            dc.withTransaction() {
                existingToken.delete()
            }
        } else {
            throw new TokenNotFoundException("Token ${tokenValue} not found")
        }

    }

    /**
     * Find token by token value from AuthenticationToken
     *
     * @param   tokenValue
     * @return  AuthenticationToken object
     */
    private findExistingToken(String tokenValue) {

        def conf = SpringSecurityUtils.securityConfig
        String tokenClassName = conf.rest.token.storage.gorm.tokenDomainClassName
        String tokenValuePropertyName = conf.rest.token.storage.gorm.tokenValuePropertyName
        def dc = grailsApplication.getClassForName(tokenClassName)
        //TODO check at startup, not here
        if (!dc) {
            throw new IllegalArgumentException("The specified token domain class '$tokenClassName' is not a domain class")
        }

        dc.withTransaction() { status ->
            return dc.findWhere((tokenValuePropertyName): tokenValue)
        }
    }

    /**
     * Find user by username from AuthenticationToken
     *
     * @param   userName
     * @return  AuthenticationToken object
     */
    private findExistingUser(String userName) {

        def conf = SpringSecurityUtils.securityConfig
        String tokenClassName = conf.rest.token.storage.gorm.tokenDomainClassName
        String usernamePropertyName = conf.rest.token.storage.gorm.usernamePropertyName
        def dc = grailsApplication.getClassForName(tokenClassName)

        //TODO check at startup, not here
        if (!dc) {
            throw new IllegalArgumentException("The specified user domain class '$tokenClassName' is not a domain class")
        }

        dc.withTransaction() { status ->
            return dc.findWhere((usernamePropertyName): userName)
        }
    }
}
