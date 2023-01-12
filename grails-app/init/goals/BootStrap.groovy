package goals
import com.amondel2.techtalk.*
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional

@Transactional
class BootStrap {


    GrailsApplication grailsApplication

    def init = { servletContext ->
        update()
    }

    @Transactional
    def update() {
        Company c = Company.findOrCreateByName(grailsApplication.config.getProperty('appparams.defaultcompany'))
        c.save(flush:true,failOnError:true)
        Role r = Role.findOrCreateByAuthority('ROLE_USER')
        r.save()
        Role r1 = Role.findOrCreateByAuthority('ROLE_ADMIN')
        r1.save()
        Role r3 = Role.findOrCreateByAuthority('ROLE_REPORTER')
        r3.save()

        User u = User.findByUsername('aaron')
        if(!u) {
            u = new User()
            u.username = 'aaron'
            u.password = 'splatt66'
            u.save(failOnError: true)

            UserRole ur = new UserRole()
            ur.user = u
            ur.role = r1
            ur.save()

            UserRole ur1 = new UserRole()
            ur1.user = u
            ur1.role = r
            ur1.save()
        }

        User u1 = User.findByUsername('aaron_user')
        if(!u1) {
            u1 = new User()
            u1.username = 'aaron_user'
            u1.password = 'splatt66'
            u1.save(failOnError: true)


            UserRole ur11 = new UserRole()
            ur11.user = u1
            ur11.role = r
            ur11.save()

        }
    }

    def destroy = {
    }
}
