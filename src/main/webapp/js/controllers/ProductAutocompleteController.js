app.controller('ProductAutocompleteController',['$scope','ProductResource',
    function($scope,Product){

   var self = this;

   self.products = Product.query();
   self.selectedItemChange = selectedItemChange;
   self.searchTextChange = searchTextChange;
   self.productSearch = productSearch;

   self.newProduct = newProduct;

   function newProduct(productName) {
     new Product({name:productName}).$save(function(value){
       self.products = Product.query();
     });
   }

   function searchTextChange(text) {
   }

   function resetAutocomplete() {
     self.selectedItem = null;
     self.searchText = "";
    //  $scope.$$childHead.$mdAutocompleteCtrl.clear();
   }

   function isValidItem(item){
    return item != "" && item != undefined
   }

   function selectedItemChange(item) {
     // TODO Create a new entry in the row
     if(isValidItem(item) && $scope.isNotEnlisted(item)){
       $scope.addItemToList(item);
       resetAutocomplete();
       self.products.splice(self.products.indexOf(item),1);
     }
   }

   function productSearch(query){
     var results  = (query == undefined || query == "")?self.products:self.products.filter(createFilterFor(query));
    return results;
   }

  function createFilterFor(query) {
    var lowercaseQuery = angular.lowercase(query);
    return function filterFn(product) {
      return (angular.lowercase(product.name).indexOf(lowercaseQuery) === 0);
    };

  }

}]);
