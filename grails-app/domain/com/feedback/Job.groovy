package com.feedback

class Job extends GemericDomainObject {

    String name

    @Override
    String toString() {
        return this.name
    }

    static hasMany = [orgs:JobOrg,users:UserJob]

    static mapping = {
        version false
        id generator:'assigned'
    }

    static constraints = {
        name nullable: false, unique: true, minSize: 3, maxSize: 1500
    }
}
