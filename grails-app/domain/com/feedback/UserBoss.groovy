package com.feedback

import org.apache.commons.lang.builder.HashCodeBuilder

class UserBoss extends GemericDomainObject {

    User employee
    User boss
    Boolean defaultBoss = true

    static belongsTo = [employee:User,boss:User]

    UserBoss(User u, User r) {
        this()
        employee = u
        boss = r
    }

    static boolean exists(String employeeId, String bossId) {
        UserBoss.where {
            employee == User.load(employeeId) &&
            boss == User.load(bossId)
        }.count()
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserBoss)) {
            return false
        }

        other.employee?.id == employee?.id && other.boss?.id == boss?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (employee) builder.append(employee.id)
        if (boss) builder.append(boss.id)
        builder.toHashCode()
    }

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