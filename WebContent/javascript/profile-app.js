(function(){
	angular.module('mainApp')
		.controller('ProfileController', ['$scope','$http', '$location', 'Profile','auth','$rootScope', function($scope,$http,$location,Profile,auth,$rootScope){

			$scope.usuario = Profile.get({username: auth.username});

			$scope.salvar = function(usuarioEditado) { 

				usuarioEditado.$update(function (usuarioEditado){
					$rootScope.mensagensRegraNegocio = [{mensagem:'Usuário alterado com sucesso',tipo:'alert-success'}];

				},function(error){

					$rootScope.mensagensRegraNegocio = [{mensagem:'Já existe um usuário com este username',tipo:'alert-warning'}];
				});
			};

	}]);
})();
