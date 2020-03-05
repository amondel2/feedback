package com.feedback

class Issue extends GemericDomainObject {

    static constraints = {
        issueType nullable: false
        issueDescription nullable: false, blank: false
        parentIsssue nullable: true
        issueResponse nullable: true
    }

    static mapping = {
        id generator: 'assigned'
        issueDescription type: 'text'
        issueResponse type: 'text'
        version false
    }

    @Override
    String toString() {
        return issueDescription
    }

    static hasMany = [duplicateIssues:Issue]
    static belongsTo = [uatSession:UATSession,parentIsssue:Issue,employee:User]

    IssueType issueType
    UATSession uatSession
    String issueDescription
    String issueResponse
    Issue parentIsssue
    User employee
}