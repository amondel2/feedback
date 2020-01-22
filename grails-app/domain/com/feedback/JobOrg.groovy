package com.feedback

import org.apache.commons.lang.builder.HashCodeBuilder

class JobOrg extends GemericDomainObject {

    Organization org
    Job job

    static belongsTo = [org:Organization,job:Job]

    static hasMany = [employees:UserJob]

    JobOrg(Organization u, Job r) {
        this()
        org = u
        job = r
    }

    static boolean exists(String employeeId, String bossId) {
        JobOrg.where {
            org == Organization.load(employeeId) &&
                    job == Job.load(bossId)
        }.count()
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof JobOrg)) {
            return false
        }

        other.job?.id == job?.id && other.job?.id == job?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (job) builder.append(job.id)
        if (org) builder.append(org.id)
        builder.toHashCode()
    }

    static constraints = {
        id display:true
        org unique: 'job'
        job unique: 'org'
    }

    public String toString(){
           return this.job?.toString() + " under " + this.org?.toString()
    }

    static mapping = {
        version false
        id generator:'assigned'
    }
}
