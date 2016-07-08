(function(){
    
    var app = angular.module('myApp');
    
    app.factory('Usuario', ['$resource', function($resource){
        return $resource('http://localhost:8080/SlumServer/slum/usuario/:id',{id:'@id'}, {
            update:{
                method: 'PUT'
            }
        }); 
    }]);
})();