(function(){
    angular.module('mainApp')
    .controller('ProfessorListarControlador', ['$scope', '$routeParams', '$location','Professor',function($scope, $routeParams, $location, Professor){
        Professor.query(function(professores){
           $scope.professores = professores;
           $scope.totalObjetosPaginacao = $scope.professores.length;
        });
        
        if($routeParams.mensagem)
            $scope.mensagensRegraNegocio = $routeParams.mensagem;
        
       $scope.incluirProfessor = function(){
            $location.path('/professorIncluir');
       };
       $scope.editarProfessor = function(professor){
            $location.path('/professorEditar').search({professor: professor});
       };
       $scope.excluirProfessor = function(professor){
            if(confirm('Deseja excluir '+professor.nome+'?')){
                professor.$delete(function(){
                    $scope.professores.splice($scope.professores.indexOf(professor), 1);
                    $scope.atualizaValoresPaginas($scope.professores);
                    $scope.mensagensRegraNegocio = [{tipo:'alert-success',mensagem:'Professor excluído com sucesso.'}];
                }, function(requestError){
                    $scope.mensagensRegraNegocio = requestError.data;
                });
               
            }
       };
   }])
    .controller('ProfessorConsultarControlador', ['$scope', '$routeParams', '$location', 'Professor', function($scope, $routeParams, $location, Professor){
        if($routeParams.professor)
            $scope.professor = $routeParams.professor;
        else
            $scope.professor = Professor.get({id:$routeParams.id});
       
        if($routeParams.mensagem)
            $scope.mensagensRegraNegocio = $routeParams.mensagem;
        
       $scope.editarProfessor = function(professor){
           $location.path('/professorEditar').search({professor: professor});
       }
       $scope.excluirProfessor = function(professor){
            if(confirm('Deseja excluir '+professor.nome+'?')){
                professor.$delete(function(){
                    $location.path('/professor').search({mensagem:[{tipo:'alert-success',mensagem:'Professor excluído com sucesso.'}]});
                }, function(requestError){
                    $scope.mensagensRegraNegocio = requestError.data;
                });
            }
       }
   }])
    .controller('ProfessorIncluirControlador', ['$scope', '$location', 'Professor', function($scope, $location, Professor){
        $scope.professor = new Professor();
        $scope.incluirProfessor = function(professor){
            professor.$save(function(professor){
                $location.path('/professor/'+professor.id).search({mensagem:[{tipo:'alert-success',mensagem:'Professor incluído com sucesso.'}]});
            }, function(requestError){
                $scope.mensagensRegraNegocio = requestError.data;
            });
        };
    }])
    .controller('ProfessorEditarControlador', ['$scope','$routeParams','$location','Professor', function($scope,$routeParams,$location,Professor){
       $scope.professor = Professor.get({id:$routeParams.professor.id});

       $scope.alterarProfessor = function(professor){
            professor.$update(function(professor){
                $location.path('/professor/'+professor.id).search({professor: professor, mensagem:[{tipo:'alert-success',mensagem:'Professor alterado com sucesso.'}]});
            }, function(requestError){
                $scope.mensagensRegraNegocio = requestError.data;
            });
        };
   }]);
})();
