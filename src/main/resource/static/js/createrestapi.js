var app = angular.module('createRestAPISubmit', []);
	
	app.controller('CreateRestAPIController',[ '$scope', '$http', function($scope, $http) {
		$scope.methodtype = ["GET", "POST", "PUT"];

			
		$scope.list = [];
			$scope.headerText = 'AngularJS Post Form Spring MVC example: Submit below form';
			$scope.submit = function() {
				
				var formData = {
						"group" : $scope.group,
						"artifact" : $scope.artifact,
						"apiname" : $scope.apiname,
						"restapiurl" : $scope.restapiurl,
						"headervalues" : $scope.headervalues,
						"jsonreq" : $scope.jsonreq,
						"jsonres" : $scope.jsonres,
						"methodtype" : $scope.selectedMethodType
				};
				
				console.log("Balaji>>->"+formData +">>->"+formData.group);
				
				var response = $http.post('/createrestapi', formData);
				response.success(function(data, status, headers, config) {
					$scope.list.push(data);
				});
				response.error(function(data, status, headers, config) {
					//alert( "Exception details: " + JSON.stringify({data: data}));
				});
				
				//Empty list data after process
				$scope.list = [];
				
			};
		}]);
