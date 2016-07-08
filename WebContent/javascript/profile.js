(function(){
    var app = angular.module('mainApp');
    
    app.factory('Profile', ['$resource', function($resource){
        return $resource('http://localhost:8080/SlumServer/slum/usuario/:username',{username:'@username'}, {
            update:{
                method: 'PUT'
            }
        }); 
    }]);
})();