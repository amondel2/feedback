package feedback

import com.feedback.Organization
import com.feedback.User
import com.feedback.Role
import com.feedback.UserRole
import com.feedback.Job
import com.feedback.JobOrg
import com.feedback.UserJob

class BootStrap {

    def init = { servletContext ->
        String pass = System.getProperty("DB_PASSWORD")?.toString() ?: System.getenv("DB_PASSWORD")?.toString()
        Role r
        Role r1
        Role.withTransaction {
            r = Role.findOrCreateByAuthority("ROLE_ADMIN")
            r.save(failOnError: true)
            r1 = Role.findOrCreateByAuthority("ROLE_USER")
            r1.save(failOnError: true)
        }

        User ua
        User uu
        User.withTransaction {
            ua = User.findByUsername("admin")
            if (!ua) {
                ua = new User()
                ua.username = "admin"
                ua.password = pass
                ua.enabled = true
                ua.firstName = "Admin"
                ua.lastName = "Admin"
                ua.employeeId = "54321"
                ua.accountExpired = false
                ua.accountExpired = false
                ua.email = "ideaadmin@foo.com"
                ua.hireDate = new Date()
                ua.save(failOnError: true)
            }
        }
        User.withTransaction {
            uu = User.findByUsername("aaron")
            if (!uu) {
                uu = new User()
                uu.username = "aaron"
                uu.password = pass
                uu.firstName = "Aaron"
                uu.lastName = "User"
                uu.employeeId = "23122"
                uu.enabled = true
                uu.hireDate = new Date()
                uu.accountExpired = false
                uu.accountExpired = false
                uu.email = "amondelblatt@foo.com"
                uu.save(failOnError: true)
            }
        }

        UserRole.withTransaction {
            UserRole ur = UserRole.findOrCreateByUserAndRole(uu,r1)
            ur.save()
            UserRole ur1 = UserRole.findOrCreateByUserAndRole(ua,r)
            ur1.save()
            UserRole ur2 = UserRole.findOrCreateByUserAndRole(ua,r1)
            ur2.save()
        }


        Organization o1
        Organization o2
        Organization o3
        Organization o4
        Organization o5
        Organization o6
        Organization o7, o8
        Organization.withTransaction {
            o1 =  Organization.findOrCreateByName("Top")
            o1.save()
            o2 =  Organization.findOrCreateByName("Top_second")
            o2.save()
            o3 =  Organization.findOrCreateByName("MiddleTop")
            o3.parent = o1
            o3.save()
            o4 =  Organization.findOrCreateByName("MiddleTop_second")
            o4.parent = o1
            o4.save()
            o5 =  Organization.findOrCreateByName("TOP_second_Middle")
            o5.parent = o2
            o5.save()
            o6 =  Organization.findOrCreateByName("Bottom_TOP_Second")
            o6.parent = o3
            o6.save()
            o7 =  Organization.findOrCreateByName("MiddleTop_Bottom")
            o7.parent = o5
            o7.save()
            o8 =  Organization.findOrCreateByName("Bottom")
            o8.parent = o7
            o8.save()
        }

        Job superv,writter,actor
        Job.withTransaction {
            superv = Job.findOrCreateByName("Supervisior")
            writter = Job.findOrCreateByName("Writter")
            actor = Job.findOrCreateByName("Actor")
            superv.save()
            writter.save()
            actor.save()
        }

        JobOrg t1Super,mtSupper,m4Supper, m4Writter, mtactor,m4topoemps
        JobOrg.withTransaction {
            t1Super = JobOrg.findOrCreateByOrgAndJob(o1,superv)
            t1Super.save()
            mtSupper = JobOrg.findOrCreateByOrgAndJob(o3,superv)
            mtSupper.save()
            m4Supper =  JobOrg.findOrCreateByOrgAndJob(o8,superv)
            m4Supper.save()
            m4Writter = JobOrg.findOrCreateByOrgAndJob(o8,writter)
            m4Writter.save()
            m4topoemps = JobOrg.findOrCreateByOrgAndJob(o7,writter)
            m4topoemps.save()
            mtactor= JobOrg.findOrCreateByOrgAndJob(o3,actor)
            mtactor.save()
        }

        UserJob t1SuperJob,mtSupperJob,m4SuperJob,mtactorJob,m4WritterJob
        UserJob.withTransaction {
            t1SuperJob = UserJob.findOrCreateByUserAndJob(ua,t1Super)
            t1SuperJob.save()
            mtSupperJob = UserJob.findOrCreateByUserAndJob(ua,mtSupper)
            mtSupperJob.save()
            m4SuperJob = UserJob.findOrCreateByUserAndJob(ua,m4Supper)
            m4SuperJob.save()
            mtactorJob = UserJob.findOrCreateByUserAndJob(uu,mtactor)
            mtactorJob.save()
            m4WritterJob = UserJob.findOrCreateByUserAndJob(uu,m4Writter)
            m4WritterJob.save()
        }



    }
    def destroy = {
    }
}
