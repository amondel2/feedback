package com.feedback

class IssueCommand  implements  grails.validation.Validateable  {
    String id
    IssueType issueType
    String issueDescription
    String issueResponse

    static constraints = {
        issueType nullable: false
        issueDescription nullable: false
        issueResponse nullable: true
        id nullable: true
    }
}
