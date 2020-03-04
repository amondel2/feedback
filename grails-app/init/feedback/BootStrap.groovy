package feedback

import com.feedback.Answer
import com.feedback.Issue
import com.feedback.IssueType
import com.feedback.Program
import com.feedback.UATSession
import com.feedback.UATSessionQuestions
import com.feedback.UserUats
import com.feedback.Organization
import com.feedback.Question
import com.feedback.QuestionType
import com.feedback.User
import com.feedback.Role
import com.feedback.UserBoss
import com.feedback.UserRole
import com.feedback.Job
import com.feedback.JobOrg
import com.feedback.UserJob
import com.lsdatabase.MName

class BootStrap {

    def init = { servletContext ->
        String pass = System.getProperty("DB_PASSWORD")?.toString() ?: System.getenv("DB_PASSWORD")?.toString()
        Role r
        Role r1
        Role r2
        Role.withTransaction {
            r = Role.findOrCreateByAuthority("ROLE_ADMIN")
            r.save(failOnError: true)
            r1 = Role.findOrCreateByAuthority("ROLE_USER")
            r1.save(failOnError: true)
            r2 = Role.findOrCreateByAuthority("ROLE_UAT_ADMIN")
            r2.save(failOnError: true)
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

        UserBoss.withTransaction {
            UserBoss us = UserBoss.findOrCreateByBossAndEmployee(ua,uu)
            us.save(failOnError:true)
        }

        UserRole.withTransaction {
            UserRole ur = UserRole.findOrCreateByUserAndRole(uu,r1)
            ur.save(failOnError:true)
            UserRole ur1 = UserRole.findOrCreateByUserAndRole(ua,r)
            ur1.save(failOnError:true)
            UserRole ur2 = UserRole.findOrCreateByUserAndRole(ua,r1)
            ur2.save(failOnError:true)
        }

        MName m1,m2
        MName.withTransaction {
            m1 = MName.findOrCreateByMachineNameAndUserName("7xder","aaron")
            m2 = MName.findOrCreateByMachineNameAndUserName("2x45612","admin")
            m1.save(failOnError:true)
            m2.save(failOnError:true)
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
            o1.save(failOnError:true)
            o2 =  Organization.findOrCreateByName("Top_second")
            o2.save(failOnError:true)
            o3 =  Organization.findOrCreateByName("MiddleTop")
            o3.parent = o1
            o3.save(failOnError:true)
            o4 =  Organization.findOrCreateByName("MiddleTop_second")
            o4.parent = o1
            o4.save(failOnError:true)
            o5 =  Organization.findOrCreateByName("TOP_second_Middle")
            o5.parent = o2
            o5.save(failOnError:true)
            o6 =  Organization.findOrCreateByName("Bottom_TOP_Second")
            o6.parent = o3
            o6.save(failOnError:true)
            o7 =  Organization.findOrCreateByName("MiddleTop_Bottom")
            o7.parent = o5
            o7.save(failOnError:true)
            o8 =  Organization.findOrCreateByName("Bottom")
            o8.parent = o7
            o8.save(failOnError:true)
        }

        Job superv,writter,actor
        Job.withTransaction {
            superv = Job.findOrCreateByName("Supervisior")
            writter = Job.findOrCreateByName("Writter")
            actor = Job.findOrCreateByName("Actor")
            superv.save(failOnError:true)
            writter.save(failOnError:true)
            actor.save(failOnError:true)
        }

        JobOrg t1Super,mtSupper,m4Supper, m4Writter, mtactor,m4topoemps
        JobOrg.withTransaction {
            t1Super = JobOrg.findOrCreateByOrgAndJob(o1,superv)
            t1Super.save(failOnError:true)
            mtSupper = JobOrg.findOrCreateByOrgAndJob(o3,superv)
            mtSupper.save(failOnError:true)
            m4Supper =  JobOrg.findOrCreateByOrgAndJob(o8,superv)
            m4Supper.save(failOnError:true)
            m4Writter = JobOrg.findOrCreateByOrgAndJob(o8,writter)
            m4Writter.save(failOnError:true)
            m4topoemps = JobOrg.findOrCreateByOrgAndJob(o7,writter)
            m4topoemps.save(failOnError:true)
            mtactor= JobOrg.findOrCreateByOrgAndJob(o3,actor)
            mtactor.save(failOnError:true)
        }

        UserJob t1SuperJob,mtSupperJob,m4SuperJob,mtactorJob,m4WritterJob
        UserJob.withTransaction {
            t1SuperJob = UserJob.findOrCreateByUserAndJob(ua,t1Super)
            t1SuperJob.save(failOnError:true)
            mtSupperJob = UserJob.findOrCreateByUserAndJob(ua,mtSupper)
            mtSupperJob.save(failOnError:true)
            m4SuperJob = UserJob.findOrCreateByUserAndJob(ua,m4Supper)
            m4SuperJob.save(failOnError:true)
            mtactorJob = UserJob.findOrCreateByUserAndJob(uu,mtactor)
            mtactorJob.save(failOnError:true)
            m4WritterJob = UserJob.findOrCreateByUserAndJob(uu,m4Writter)
            m4WritterJob.save(failOnError:true)
        }
        Question q1,q2,q3,q4,q5;
        Question.withTransaction {
            q1 = Question.findOrCreateByQuestionAndQuestionType("Did You Execute a Save? ", QuestionType.BooleanPassFail)
            q2 = Question.findOrCreateByQuestionAndQuestionType("Please Add Any Additional Comments ", QuestionType.Open)
            q3 = Question.findOrCreateByQuestionAndQuestionType("Please Choose How Long you have used the software", QuestionType.MultiChoice)
            q4 = Question.findOrCreateByQuestionAndQuestionType("Please Rate the Software on Statisfaction", QuestionType.Likert)
            q5 = Question.findOrCreateByQuestionAndQuestionType("Chose The Best Choice?", QuestionType.Dropdown)
            [q1,q2,q3,q4,q5].each { it.save(failOnError:true)}
        }

        Answer a14,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a15,a16
        Answer.withTransaction {
            a15  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q1,"Pass",1)
            a16  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q1,"Fail",1)
            a14  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q2,"open",1)
            a1  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q3,"Less than 3 months",1)
            a2  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q3,"3-10 months",2)
            a3  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q3,"10 months to 3 years",3)
            a4  = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q3,"More than four years",4)
            a5 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q4,"Exceeds All Expections",1)
            a6 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q4,"Exceeds Some Expections",2)
            a7 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q4,"Meets Expections",3)
            a8 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q4,"Doesn't Meet Some Expections",4)
            a9 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q4,"Doesn't Meet Any Expections",5)
            a10 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q5,"Fries",1)
            a11 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q5,"Onion Rings",2)
            a12 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q5,"Beer",3)
            a13 = Answer.findOrCreateByQuestionAndAnswerAndOrderNumber(q5,"Wings",4)
            [a14,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a15,a16].each { it.save(failOnError:true)}
        }

        Program p1,p2,p3
        Program.withTransaction {
            p1 = Program.findOrCreateByName("Foo")
            p2 = Program.findOrCreateByName("Bar")
            p3 = Program.findOrCreateByName("Jane")
            [p1,p2,p3].each { it.save(failOnError:true)}
        }


        UATSession uas1, uas2
        UATSession.withTransaction {
            uas1 = UATSession.findOrCreateByTitleAndProgram("Test Foo UAT",p1)
            uas2 = UATSession.findOrCreateByTitleAndProgram("Test Bar UAT",p2)
            if(uas1.startDate.equals(null)) {
                uas1.startDate = new Date()
            }
            if(uas2.startDate.equals(null)) {
                uas2.startDate = new Date()
            }
            [uas1, uas2].each { it.save(failOnError:true)}
        }

        Issue.withTransaction {
            Issue is1 = Issue.findOrCreateByUatSessionAndEmployeeAndIssueTypeAndIssueDescription(uas1,uu, IssueType.Question,"Hello World")
            is1.save(failOnError:true)
        }

        UATSessionQuestions uasq1,uasq2,uasq3,uasq4,uasq5,uasq6,uasq7,uasq8
        UATSessionQuestions.withTransaction {
            uasq1 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q1,uas1,1)
            uasq2 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q2,uas1,5)
            uasq3 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q3,uas1,3)
            uasq4 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q4,uas1,4)
            uasq5 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q5,uas1,2)
            uasq6 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q1,uas2,1)
            uasq7 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q2,uas2,3)
            uasq8 = UATSessionQuestions.findOrCreateByQuestionAndSessionAndOrderNumber(q3,uas2,2)
            [uasq1,uasq2,uasq3,uasq4,uasq5,uasq6,uasq7,uasq8].each { it.save(failOnError:true)}
        }

        UserUats uut1,uut2,uut3
        UserUats.withTransaction {
            uut1 = UserUats.findOrCreateByUserAndUatsAndMachinenName(uu,uas1,m1.machineName)
            uut2 = UserUats.findOrCreateByUserAndUatsAndMachinenName(uu,uas2,m1.machineName)
            uut3 = UserUats.findOrCreateByUserAndUatsAndMachinenName(ua,uas1,m2.machineName)
            [uut1,uut2,uut3].each { it.save(failOnError:true,flush:true)}
        }

    }
    def destroy = {
    }
}
