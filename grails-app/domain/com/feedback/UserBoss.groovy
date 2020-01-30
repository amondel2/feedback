package com.feedback

import org.apache.commons.lang.builder.HashCodeBuilder

class UserBoss extends GemericDomainObject {

    User employee
    User boss
    Boolean defaultBoss = true

    static belongsTo = [employee:User,boss:User]

   static constraints = {
        id display:true
        employee unique: 'boss'
        boss unique: 'employee'
    }

    def afterInsert() {
        removeOtherDefaults()
    }

    def afterUpdate() {
        removeOtherDefaults()
    }

    def removeOtherDefaults() {
        if(this.defaultBoss) {
            def e = this.employee
            def b = this.boss
            def query = UserBoss.where {
                defaultBoss == true
                employee == e
                boss != b
            }
            query.updateAll(defaultBoss:false)
        }
    }

    public String toString(){
        if(this.defaultBoss) {
            return this.boss?.toString() + " is the offical boss of  " + this.employee?.toString()
        }
        return this.boss?.toString() + " is the lined boss of  " + this.employee?.toString()
    }

    static mapping = {
        version false
        id generator:'assigned'
    }
}