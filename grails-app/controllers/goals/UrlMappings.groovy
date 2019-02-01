package goals

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller:"home",params:params)
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
