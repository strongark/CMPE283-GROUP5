'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
angular.module('yapp')
  .controller('DashboardCtrl', function($scope, $state, $rootScope, APIService) {
    $scope.$state = $state;
    
    var projectList;
    APIService.getProjects(function(response) {
        if(response.data) {
        	$scope.projects = response.data;
	    } else {
	        $scope.error = response.message;
	        $scope.dataLoading = false;
	        alert("Projects not found");
	    }
    });
    
    APIService.getFlavor(function(response) {
        if(response.data) {
        	$scope.flavors = response.data;
	    } else {
	        $scope.error = response.message;
	        $scope.dataLoading = false;
	        alert("Favors not found");
	    }
    });
    
    
    $scope.menuItems = [];
    angular.forEach($state.get(), function (item) {
        if (item.data && item.data.visible) {
            $scope.menuItems.push({name: item.name, text: item.data.text});
        }
    });
  });
