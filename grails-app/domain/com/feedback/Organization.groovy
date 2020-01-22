package com.feedback

class Organization extends GemericDomainObject{

    static constraints = {
        name nullable:false,blank:false,unique:true
        parent nullable: true
    }

    static mapping = {
        id generator: 'assigned'
        version false
        jobs cascade: "all-delete-orphan"
        children cascade: "all-delete-orphan"
    }

    static hasMany = [jobs:JobOrg,children:Organization]
    static belongsTo = [parent:Organization]

    @Override
    public String toString(){
        return this.name
    }

    Organization parent
    String name
}
