class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/index")
        "500"(view:'/error')
        "/"(controller: "inventory", action: "/upload")
        "/"(controller: "provision", action: "/fileUploadTest")
	}
}
