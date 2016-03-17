import com.eye.auth.AuthenticationTokenStorageService
import com.eye.auth.DynamicRoleVoterService

// Place your Spring DSL code here
beans = {
    //For Dynamic role mapping
    closureVoter(DynamicRoleVoterService)

    // To create our own token storage system
    tokenStorageService(AuthenticationTokenStorageService) {
        grailsApplication = ref('grailsApplication')
        userDetailsService = ref('userDetailsService')
    }

}
