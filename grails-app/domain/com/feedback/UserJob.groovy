package com.feedback

import org.apache.commons.lang.builder.HashCodeBuilder

class UserJob extends GemericDomainObject {

    User user
    JobOrg job

    static belongsTo = [user:User,job:JobOrg]

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
