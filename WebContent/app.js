(function() {
    var app = angular.module('myApp', ['ngResource']);


   
    app.controller('LoginController', function($scope){

       

    });

    app.directive('regraNegocio', function() {
        return {
            templateUrl: 'regraNegocio.html',
            restrict: 'E',
            controller: function($rootScope){
                $rootScope.mensagensError = [];
                $rootScope.limpaEstaMensagemRegraNegocio = function(mensagem){
                    $rootScope.mensagensError.splice($rootScope.mensagensError.indexOf(mensagem), 1);
                    };
                }
            };
    });
    


    app.controller('TabController', ['$scope',function($scope){

        this.tab =1;
        
        this.selectTab = function(setTab){
            this.tab = setTab;    
        };
        
        this.isSelected = function(checkTab){
            return this.tab === checkTab;    
        };
    }]);
    
    app.controller('RegisterController', function(){
        
        
    });
        
  
    app.controller('FormController', ['$scope', '$window', '$http','Usuario','$rootScope', function($scope, $window, $http, Usuario, $rootScope) {
       
        $scope.usuario = new Usuario();
        
        $scope.submit = function() {

            if($scope.usuario.password != $scope.usuario.confirmPassword){
                $rootScope.mensagensError = [{mensagem:'As senhas não conferem',tipo:'alert-warning'}];
            }else{
                 $scope.usuario.$save(function (usuario){
                $rootScope.mensagensError = [{mensagem:'Usuário registrado com sucesso',tipo:'alert-success'}];
                },function(error){
                    $rootScope.mensagensError = error.data;
                });
            }
        };
    }]);
    
    
    


})();
