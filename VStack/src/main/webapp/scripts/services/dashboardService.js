'use strict';

angular.module('yapp').factory('APIService',
		['$http','$rootScope','$timeout',function($http, $rootScope, $timeout) {
					var service = {};

					service.getProjects = function(callback) {
						$http({
							method : 'GET',
							url : 'getProject.htm'
						}).then(function(response) {
							// success callback
							callback(response);
						}, function(response) {
							// failure callback
						});
					};
					
					service.getFlavor = function(callback) {
						$http({
							method : 'GET',
							url : 'getFlavor.htm'
						}).then(function(response) {
							// success callback
							callback(response);
						}, function(response) {
							// failure callback
						});
					};

					return service;
				} ]);