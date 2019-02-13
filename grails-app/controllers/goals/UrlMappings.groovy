package goals

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/js/*"(uri: "/assets")
        "/"(controller:"home",params:params)
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
