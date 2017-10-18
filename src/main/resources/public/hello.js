var app = angular.module('demo', []);

app.controller('getAllPlayers', function($scope, $http) {
    $http.get('/player/getAll').
    then(function(response) {
        $scope.players = response.data;

    });
    $scope.clickCreateGame = function () {
        attackerTeam1 = $scope.attacker1;
        console.log($scope.attacker1.id);
        console.log($scope.attacker1.name);
        console.log($scope.score1);
        console.log($scope.score2);

    }
});