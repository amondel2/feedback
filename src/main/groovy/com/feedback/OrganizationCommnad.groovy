package com.feedback

class OrganizationCommnad implements  grails.validation.Validateable  {

    String id
    String name
    String parentId
    boolean hasChildren

    OrganizationCommnad() {}

    OrganizationCommnad(json) {
        this.id = json.id
        this.name = json.toString()
        this.parentId = json.parentId
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
