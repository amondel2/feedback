package com.lsdatabase

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class MNameController {

   static scaffold = MName
}
