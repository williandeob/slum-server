(function(){
    var app = angular.module('mainApp');
    
    app.factory('Professor', ['$resource', function($resource){
        return $resource('http://localhost:8080/gerenciadorDeEscolas/rest/professor/:id',{id:'@id'}, {
            update:{
                method: 'PUT'
            }
        }); 
    }]);
})();