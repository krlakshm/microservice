var app = angular.module('StubsSubmit', []);

	app.controller('StubsSubmitController',[ '$scope', '$http', '$window', function($scope, $http, $window) {
		
		$scope.projecttype = 'SOAP';
		$scope.showSOAPdiv = true;
		$scope.showDBdiv = false;
		$scope.showFILEdiv = false;
		$scope.showMQdiv = false;
		
		$scope.showToggle = function() {
			console.log("inside Toggele : "+$scope.projecttype);
			if($scope.projecttype == "SOAP"){
				$scope.showSOAPdiv = true;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = false;
			}else if($scope.projecttype == "DB"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = true;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = false;
			}else if($scope.projecttype == "FILE"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = true;
				$scope.showMQdiv = false;
			}else if($scope.projecttype == "MQ"){
				$scope.showSOAPdiv = false;
				$scope.showDBdiv = false;
				$scope.showFILEdiv = false;
				$scope.showMQdiv = true;
			}
				
		};
		
			$scope.submit = function() {
			 console.log('file is ' + $scope.file);
			 console.log("Balaji"+file.files[0]);
	         console.log('file is ' + $scope.file);
	         console.log('file1 is ' + $scope.file1);
	         console.dir(file);
	         console.dir(file1);
	         console.log("radio:"+$scope.projecttype);
				
				var formData1 = {
						"group" : $scope.group,
						"artifact" : $scope.artifact,
						"json" : $scope.json,
						"wsdlurl": $scope.wsdlurl,
						"projecttype": $scope.projecttype
						
				};
				
				console.log("Balaji>>->"+formData1 +">>->"+formData1.group);
				
				var formData=new FormData();
				var uploadUrl="/microservice/createstubs";
				console.log("balaji");
				formData.append("CreateStubsParam", JSON.stringify(formData1));
				formData.append("file",file.files[0]);
				formData.append("file1",file1.files[0]);
				$http({
				        method: 'POST',
				        url: uploadUrl,
				        headers: {'Content-Type': undefined},
				        responseType:'arraybuffer',
				        data: formData,
				        transformRequest: function(data, headersGetterFunction) {
				                        return data;
				         }
				     })
				    .success(function(responseData) { 
				    	console.log(">>>-> Sowjanya");
				    	console.log(">>>->"+responseData);
				    	var a = document.createElement('a');
				    	var blob = new Blob([responseData], {'type':"application/octet-stream"});
				    	a.href = URL.createObjectURL(blob);
				    	a.download = "SoapStubProject.zip";
				    	a.click();
				                   // alert(data);
				                    //$window.location.href = '/restapi.html';
				     })	
			};
		}]);
