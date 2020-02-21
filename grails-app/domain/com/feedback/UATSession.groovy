package com.feedback

import groovy.transform.Memoized

class UATSession extends GemericDomainObject {

    static constraints = {
        title nullable: false, blank: false
        program nullable: false
        endDate nullable: false
        startDate nullable: false, validator: { Date val, UATSession obj ->
            Calendar gc  = new GregorianCalendar()
            gc.add(Calendar.DAY_OF_MONTH,-1)
            return (val >=  gc.getTime() && ( obj?.endDate.equals(null) ||  obj?.endDate > val ))
        }
    }

    @Memoized
    def getDefaultDate(Date sDate) {
        Calendar gc = new GregorianCalendar()
        gc.setTime(sDate)
        gc.add(Calendar.DAY_OF_MONTH,14)
        gc.getTime()
    }

    @Override
    def beforeValidate() {
        super.beforeValidate()
        if(!endDate || endDate.equals(null)) {
            endDate = this.getDefaultDate(this.startDate)
        }
    }

    @Override
    def beforeInsert() {
        super.beforeInsert()
        if(!endDate || endDate.equals(null)) {
            endDate = this.getDefaultDate(this.startDate)
        }
    }

    static mapping = {
        id generator: 'assigned'
        version false
    }

    static hasMany = [users:UserUats,issues:Issue,questions:UATSessionQuestions]
    static belongsTo = [program:Program]

    Date startDate
    Date endDate
    Program program
    String title

    @Override
    String toString() {
        this.title
    }

}
