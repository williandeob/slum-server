(function(){
   var app = angular.module('mainApp');

   app.controller('LoginController',['$scope','$http','Usuario','$rootScope','$location','$window','auth', function($scope,$http,Usuario,$rootScope,$location, $window,auth){
	   
	    $scope.usuario = new Usuario();
		
	    $scope.login = function() {
			
			
			auth.login($scope.usuario.username, $scope.usuario.password).then(function(loggedInUser) {
				
				
			}).catch(function(error) {
				
				$rootScope.mensagensRegraNegocio = [{mensagem:'Usuário ou senha incorretos',tipo:'alert-warning'}];
				$rootScope.$apply();
				
			});
       };
	   
	   $scope.registrar = function() { 
		   if($scope.usuario.password != $scope.usuario.confirmPassword){
			   $rootScope.mensagensRegraNegocio = [{mensagem:'As senhas não conferem',tipo:'alert-warning'}];
		   }else if($scope.usuario.password.length <= 5){
			   $rootScope.mensagensRegraNegocio = [{mensagem:'A senha deve conter mais que 5 caracteres',tipo:'alert-warning'}];
		   }else{
			   $scope.usuario.$save(function (usuario){
				   $rootScope.mensagensRegraNegocio = [{mensagem:'Usuário registrado com sucesso',tipo:'alert-success'}];

				   $scope.usuario.nome = '';
				   $scope.usuario.username = '';
				   $scope.usuario.email = '';
				   $scope.usuario.password = '';
				   $scope.usuario.confirmPassword = '';

				   $scope.registerForm.$setPristine();
				   $scope.registerForm.$setUntouched();

			   },function(error){
				
					$rootScope.mensagensRegraNegocio = [{mensagem:'J&aacute; existe um usuário com este username',tipo:'alert-danger'}];
			   });
		   }
	   };
    }]).controller('LogoutController', ['$scope','$http', '$location', 'Usuario','auth', function($scope,$http,$location,Usuario,auth){
		 $scope.logout = function() {
			auth.logout();
       };
	
    }]);
})();
