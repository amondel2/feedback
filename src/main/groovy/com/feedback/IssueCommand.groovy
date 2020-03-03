package com.feedback

class IssueCommand  implements  grails.validation.Validateable  {
    String id
    IssueType issueType
    String issueDescription
    String issueResponse
}
