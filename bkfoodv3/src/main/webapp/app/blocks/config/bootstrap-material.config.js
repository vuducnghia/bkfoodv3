(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .config(bootstrapMaterialDesignConfig);

//    compileServiceConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
