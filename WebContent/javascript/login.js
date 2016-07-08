(function(){
    
    var app = angular.module('mainApp');
    
    app.factory('Usuario', ['$resource', function($resource){
        return $resource('http://localhost:8080/SlumServer/slum/loginService/:id',{id:'@id'}, {
            update:{
                method: 'PUT'
            }
        }); 
    }]);
})();