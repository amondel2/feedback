package com.lsdatabase

import com.feedback.GemericDomainObject

class MName extends  GemericDomainObject {

    static constraints = {
        machineName nullable: false, unique: true
        userName nullable: false, unique: ['machineName']
    }

    static mapping = {
        datasource 'lsmsName'
        version false
        id generator:'assigned'
    }

    String machineName
    String userName
}
