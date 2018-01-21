var app = angular.module('kicker', []);

app.controller('getAllPlayers', function ($scope, $http) {
    $http.get('/player/getAll').then(function (response) {
        $scope.players = response.data;

    });
    $scope.clickCreateGame = function () {
        $http.put('/game/createGame', {
            attackerTeam1: $scope.attacker1,
            attackerTeam2: $scope.attacker2,
            defenderTeam1: $scope.defender1,
            defenderTeam2: $scope.defender2,
            scoreTeam1: $scope.score1,
            scoreTeam2: $scope.score2

        }).then(function (response) {
            $scope.success = true;
            $timeout(function () {
                $scope.success = false;
            }, 2000);
            console.log(response.data);
        }, function (response) {
            scope.fail = true;
            $timeout(function () {
                $scope.fail = false;
            }, 2000);
        });

    }
});

app.controller('createPlayer', function ($scope, $http, $timeout) {
    $scope.createPlayer = function () {
        $http.put('/player/createPlayer', $scope.name)

            .then(function (response) {
                $scope.success = true;
                $timeout(function () {
                    $scope.success = false;
                }, 2000);

            }, function (response) {
                $scope.fail = true;
                $timeout(function () {
                    $scope.fail = false;
                }, 2000);
            })
    }
});


app.controller('getRatings', function ($scope, $http) {
    $http.get('/stat/getPlayersRating')
        .then(function (response) {
            console.log(response.data);
            $scope.ratings = response.data;
        });
    $scope.getRatings = function () {
        $http.get('/stat/getPlayersRating')
            .then(function (response) {
                console.log(response.data);
                $scope.ratings = response.data;
            });
    }
});

