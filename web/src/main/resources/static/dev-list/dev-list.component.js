'use strict';

angular.module('devList').component('devList', {
    templateUrl: 'dev-list/dev-list.template.html',
    controller: function DevController() {
        this.devs = ["Krisztián Erdei", "Attila Fekete", "Gábor Iványi-Nagy"];
    }
});