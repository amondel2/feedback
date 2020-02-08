package com.feedback

class UserUats extends GemericDomainObject {

        User user
        UATSession uats
        String machinenName

        static hasMany = [response:UserUATResponse]
        static belongsTo = [user:User,uats:UATSession]

        static constraints = {
                user unique: 'uats'
                uats unique: 'user'
                machinenName nullable: false, blank: false
        }

        static mapping = {
                id generator: 'assigned'
                version false
        }
}
