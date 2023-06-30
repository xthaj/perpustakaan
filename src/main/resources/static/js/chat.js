var stompClient = null;
var isConnected = false;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#status-text").text("Connected");
    } else {
        $("#conversation").hide();
        $("#status-text").text("Disconnected");
    }
    $("#faq-list").html("");
    isConnected = connected;
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/responses', function (response) {
            showResponse(JSON.parse(response.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendQuestion() {
    var question = $("#question").val();
    stompClient.send("/app/question", {}, JSON.stringify({'question': question}));
}

function showQuestion(question) {
    $("#faq-list").append("<tr><td><strong>Pertanyaan:</strong> " + question + "</td></tr>");
}

function showResponse(response) {
    var question = $("#question").val();
    var responseHtml = "<tr><td><strong>Jawaban:</strong> " + response.response + "</td></tr>";
    var questionHtml = "<tr><td><strong>Pertanyaan:</strong> " + question + "</td></tr>";
    $("#faq-list").prepend(responseHtml);
    $("#faq-list").prepend(questionHtml);
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        if (!isConnected) {
            connect();
        }
    });
    $("#disconnect").click(function () {
        if (isConnected) {
            disconnect();
        }
    });
    $("#send").click(function () {
        sendQuestion();
    });
});
