package com.feedback

import org.apache.commons.lang.builder.HashCodeBuilder

class UserJob extends GemericDomainObject {

    User user
    JobOrg job

    static belongsTo = [user:User,job:JobOrg]

    UserJob(User u, Job r) {
        this()
        user = u
        job = r
    }

    static boolean exists(String employeeId, String bossId) {
        UserJob.where {
            user == User.load(employeeId) &&
                    job == JobOrg.load(bossId)
        }.count()
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserJob)) {
            return false
        }

        other.job?.id == job?.id && other.user?.id == user?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (job) builder.append(job.id)
        builder.toHashCode()
    }

    static constraints = {
        id display:true
        job unique: 'user'
        user unique: 'job'
    }



    public String toString(){
        return this.user?.toString() + " IS A  " + this.job?.toString()
    }

    static mapping = {
        version false
        id generator:'assigned'
    }
}
