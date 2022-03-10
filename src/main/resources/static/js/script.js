$(document).ready(function () {
    const PLAYERA = "PLAYER_A";
    const PLAYERB = "PLAYER_B";
    const NO_WINNER = "NO_WINNER";
    const ENABLED_CLASS = "enabled";
    const DISABLED_CLASS = "disabled";

    loadGame(false);

    $(".pit").click(function () {
        if ($(this).hasClass(DISABLED_CLASS)) return;

        let gameID = localStorage.getItem('gameId');
        $.ajax({
            url: "mancala/move",
            type: "GET",
            dataType: "json",
            data: { gameId: gameID, pitId : $(this).attr('id').replace("pit","")},
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (result) {
                drawBoard(result);
            },
            error: function (result) {
                alert("Status: " + JSON.stringify(result));
            }
        });
    });

    $("#start-button").click(function () {
        loadGame(true);
    });

    function loadGame(isNewGame = false) {
        $("#winner").hide();
        let gameID = null;
        if (isNewGame) {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "mancala/create",
                success: function (result) {
                    drawBoard(result);
                },
                error: function (result) {
                    alert("Status: " + JSON.stringify(result));
                }
            });
        } else {
            gameID = localStorage.getItem('gameId');
            $.ajax({
                url: "mancala/get",
                type: "GET",
                dataType: "json",
                data: { gameId: gameID},
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function (result) {
                    drawBoard(result);
                },
                error: function (result) {
                    alert("Status: " + JSON.stringify(result));
                }
            });
        }


    }

    function drawBoard(result) {
        localStorage.setItem('gameId', result.id);

        $("#pit0").html(result.pits[0].numberOfStones);
        $("#pit1").html(result.pits[1].numberOfStones);
        $("#pit2").html(result.pits[2].numberOfStones);
        $("#pit3").html(result.pits[3].numberOfStones);
        $("#pit4").html(result.pits[4].numberOfStones);
        $("#pit5").html(result.pits[5].numberOfStones);
        $("#treasureA > span").html(result.pits[6].numberOfStones);

        $("#pit7").html(result.pits[7].numberOfStones);
        $("#pit8").html(result.pits[8].numberOfStones);
        $("#pit9").html(result.pits[9].numberOfStones);
        $("#pit10").html(result.pits[10].numberOfStones);
        $("#pit11").html(result.pits[11].numberOfStones);
        $("#pit12").html(result.pits[12].numberOfStones);
        $("#treasureB > span").html(result.pits[13].numberOfStones);

        switch (result.playerTurn) {
            case PLAYERA:
                $("#playerA .pit").removeClass(DISABLED_CLASS).addClass(ENABLED_CLASS);
                $("#playerB .pit").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
                $("#turnInfo > span").html(PLAYERA);
                break;
            case PLAYERB:
                $("#playerA .pit").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
                $("#playerB .pit").removeClass(DISABLED_CLASS).addClass(ENABLED_CLASS);
                $("#turnInfo > span").html(PLAYERB);
                break;
        }
        if (result.winner !== NO_WINNER) {
            $("#playerA div, #playerB div").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
            $('#winner').show().children("span").html(result.winner);
        }
    }
});
