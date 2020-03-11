package com.feedback

class UserUats extends GemericDomainObject {

        User user
        UATSession uats
        String machinenName
        Status status = Status.NotStarted

        static hasMany = [response:UserUATResponse]
        static belongsTo = [user:User,uats:UATSession]

        static constraints = {
                status nullable: false
                user unique: 'uats'
                uats unique: 'user'
                machinenName nullable: false, blank: false
        }

        @Override
        String toString() {
                return "${user.toString()} ${uats.toString()}"
        }

        static mapping = {
                id generator: 'assigned'
                version false
        }
}