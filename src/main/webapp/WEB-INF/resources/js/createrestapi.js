var app = angular.module('createRestAPISubmit', []);
	
	app.controller('CreateRestAPIController',[ '$scope', '$http','$window',  function($scope, $http, $window) {
		$scope.methodtype = ["GET", "POST", "PUT"];
		$scope.servicetype = 'SOAP';
		
		
		$scope.showSOAPdiv = true;
		$scope.showDBdiv = false;
		$scope.showFILEdiv = false;
		$scope.showMQdiv = false;
		
		$scope.showToggle = function() {
			console.log("inside Toggele : "+$scope.servicetype);
			if($scope.servicetype == "SOAP"){
				$scope.showSOAPdiv = true;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = false;
			}else if($scope.servicetype == "DB"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = true;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = false;
			}else if($scope.servicetype == "FILE"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = true;
				$scope.showMQdiv = false;
			}else if($scope.servicetype == "MQ"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = true;
			}
				
		};
		
		$scope.createstub = function() {
			alert("createstub");
			var url = "http://localhost:8080/microservice/stubs";
                $window.open(url, '_blank');
		};
		
			$scope.submit = function() {
				
				var formData1 = {
						"group" : $scope.group,
						"artifact" : $scope.artifact,
						"version" : $scope.version,
						"servicetype" : $scope.servicetype,
				};
				
				console.log("Balaji>>->"+formData1 +">>->"+formData1.group);
				
				
				var formData=new FormData();
				var uploadUrl="/microservice/createrestapi";
				console.log("balaji");
				formData.append("createapiparm", JSON.stringify(formData1));
				formData.append("file",file.files[0]);
				$http({
				        method: 'POST',
				        url: uploadUrl,
				        headers: {'Content-Type': undefined},
				        data: formData,
				        transformRequest: function(data, headersGetterFunction) {
				                        return data;
				         }
				     })
				    .success(function(data, status) {   
				                    alert(data);
				                     
				                   // $window.location.href = '/restapi.html';
				     })	
				
				/*var response = $http.post('/microservice/createrestapi', formData);
				response.success(function(data, status, headers, config) {
					$scope.list.push(data);
				});
				response.error(function(data, status, headers, config) {
					//alert( "Exception details: " + JSON.stringify({data: data}));
				});*/
			};
		}]);
