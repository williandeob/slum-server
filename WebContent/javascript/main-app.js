(function(){
   var mainApp = angular.module('mainApp', ['ngResource','ui.router','willcrisis.angular-auth']);
	
     mainApp.config(function(authConfProvider) {
	   authConfProvider.setEndpointUrl('http://localhost:8080/SlumServer/slum/loginService/logar');
//	   authConfProvider.setLogoutEndpointUrl('http://localhost:8080/SlumServer/slum/loginService/logout');
	   authConfProvider.setLoginState('login');
	   authConfProvider.setUsernameFormProperty('username');
	   authConfProvider.setPasswordFormProperty('password');
	   authConfProvider.setUsernameProperty('username');
	   authConfProvider.setTokenProperty('token');
	   authConfProvider.setRolesProperty('roles');
	   authConfProvider.setRefreshTokenProperty('refresh_token');
	   authConfProvider.setTokenTypeProperty('token_type');
       
    });
    
	mainApp.run(function(authConf,$state,$rootScope){
		
		
		authConf.setFunctionIfAuthenticated(function(authService, responseData) {
		
		  $state.go('dashboard');
		   
	   });
		
	   authConf.setFunctionIfLoggedOff(function() {
		 $state.go('login');
	   });
		
	   authConf.setFunctionIfDenied(function(stateService, toState) {
		   
    	 $state.go('login');
   	   });
	});
	
	mainApp.config(function($stateProvider, $urlRouterProvider) {
	  
	  // For any unmatched url, redirect to /state1
	  $urlRouterProvider.otherwise("/login");
	  
	  $stateProvider
		    .state('login', {
		     url: "/login",
		     templateUrl: "paginas/login/login.html",
		     controller: "LoginController",
		  	 auth: false
		})
			.state('registrar', {
			url: "/registrar",
			templateUrl: "paginas/login/registrar.html",
		    auth: false
		  
		})
		  .state('dashboard', {
			url: "/dashboard",
			templateUrl: "paginas/dashboard/dashboard.html",
		    auth: true
		  
		})
			.state('remedios', {
			  url: "/remedios",
			  templateUrl: "paginas/remedios/listar.html",
			  controller: function($scope) {
				$scope.things = ["A", "Set", "Of", "Things"];
			  }
		 
		})
			.state('profile', {
			url: "/profile",
			templateUrl: "paginas/profile/profile.html",
			controller: "ProfileController"
		 
		}).state('logout', {
			url: "/logout",
			templateUrl: "paginas/login/login.html",
			controller: "LogoutController"
		 
		});
	});
   
     
    mainApp
        .filter('startFrom', function() {
        return function(input, start) {
            start = +start;
            try{
                return input.slice(start);
            }catch(e){}
        }
    });
    
    mainApp
    .directive("regraNegocio", [function(){
        return {
            restrict: 'E',
            templateUrl: 'paginas/geral/regraNegocio.html',
            controller: function($scope){
				
                $scope.limpaMensagensRegraNegocio = function(){
                    $scope.mensagensRegraNegocio = [];
                };
                $scope.limpaEstaMensagemRegraNegocio = function(mensagem){
					$scope.mensagensRegraNegocio.splice($scope.mensagensRegraNegocio.indexOf(mensagem), 1);
                };
            }
        };
    }])
    .directive("paginacao", function(){
        return {
            restrict: 'E',
            templateUrl: 'paginas/geral/paginacao.html',
            scope: false,
            controller: function($scope){
                $scope.currentPage = 0;
                $scope.pageSize = 10;
                
                $scope.paginasPaginacao = function(){
                    totalDePaginas = Math.ceil($scope.totalObjetosPaginacao/$scope.pageSize);
                    
                    paginas = [];
                    for(cont = 0; cont <= totalDePaginas; cont++){
                        paginas.push(cont);
                    }
                    
                    inicio = $scope.currentPage-2;
                    if(inicio < 0)
                        inicio = 0;
                       
                     fim = inicio+5
                     if(fim > totalDePaginas){
                         fim = totalDePaginas;
                         inicio = fim -5;
                     }
                    
                    $scope.totalDePaginas = paginas.length-2;
                    
                    paginas = paginas.slice(inicio, fim);
                    
                    return paginas;
                };
                
                $scope.paginaAnterior = function(){
                    if($scope.currentPage > 0)
                        $scope.currentPage = $scope.currentPage-1;
                };
                
                $scope.proximaPagina = function(){
                    if($scope.currentPage < ($scope.totalObjetosPaginacao/$scope.pageSize - 1)){
                        $scope.currentPage = $scope.currentPage+1;
                    }
                };
                
                $scope.setCurrentPage = function(valor){
                    $scope.currentPage = valor;
                };
                
                $scope.atualizaValoresPaginas = function(objetos){
                    $scope.currentPage = 0;
                    $scope.totalObjetosPaginacao = objetos.length;
                };
            }
        };
    })
	.directive('select2', ['$timeout', function ($timeout) {
		return {
			require: 'ngModel',
			restrict: 'A',
			link: function (scope, element, attrs) {
				element.select2();
				element.select2Initialized = true;

				var recreateSelect = function () {
					if (!element.select2Initialized)
						return;

					$timeout(function() {
						element.select2('destroy');
						element.select2();
					});
				};

				scope.$watch(attrs.ngModel, recreateSelect);

				if (attrs.ngOptions) {
					var list = attrs.ngOptions.match(/ in ([^ ]*)/)[1];
					// watch for option list change
					scope.$watch(list, recreateSelect);
				}

				if (attrs.ngDisabled)
					scope.$watch(attrs.ngDisabled, recreateSelect);
			}
		};
	}]);
})();
