package com.feedback

class OrgJobCommnad implements  grails.validation.Validateable  {

    String id
    String name
    String parentId
    boolean hasChildren

    OrgJobCommnad() {}

    OrgJobCommnad(json) {
        this.id = json.id
        this.name = json.job?.name
        this.parentId = json.orgId
    }

    def getDataForJSTree(){
        return [
                id: this.id,
                text: this.name,
                children: this.hasChildren,
                data: [
                        id: this.id,
                        name: this.name,
                        parentId: this.parentId
                ]
        ]
    }

}
