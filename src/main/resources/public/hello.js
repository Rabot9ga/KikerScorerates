angular.module('demo', [])
    .controller('Hello', function($scope, $http) {
        $http.get('http://localhost:8080/kickerTable/getByID?id=59cba45dc4fd451f707e322e').
        then(function(response) {
            $scope.greeting = response.data;
        });
    });

