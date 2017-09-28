controllers.controller('MasterListController',
		['$scope','ProductResource','MasterListResource','$location','$filter',
		 function($scope,Product,MasterList,$location,$filter){
	var self = this;
	self.listName = $filter('date')(new Date(),"dd-MMM-yyyy");

	function fetchMasterList(){
		MasterList.query({"id":"59c1f7a0e4b01ba052e3b2d8"},function(response){
			$scope.masterList = response;
		},function(httpResponse){
			if(httpResponse.status == undefined){
				$scope.message = "Something went wrong !";
			}else{
				$scope.message = httpResponse.statusText;
				$scope.masterList = new MasterList({name:"Master List",items:[]});
			}

		});
	}

	fetchMasterList();

	$scope.message = "";

	$scope.saveList = function(){
		// TODO AUTH - Change the below id to customerId once the authentication is inplace
		$scope.masterList.$create({id:"59c1f7a0e4b01ba052e3b2d8"},function(data,headers){
			$scope.message = "MasterList Saved successfully !!!";
			fetchMasterList();
		},function(httpResponse){
			let data = httpResponse.data;
			$scope.message = data.message;
		});
	}

	$scope.addItemToList = function (item){
			$scope.masterList.items.push(item.name);
	}

	$scope.isNotEnlisted = function(item){
		return $scope.masterList.items.indexOf(item.name) == -1;
	}

}]);
