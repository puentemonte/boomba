let selector = document.getElementById('code');
selector.addEventListener('keyup', (event) => {
    if (event.key == 'Enter') {
        var code = +selector.value;
        go("/lobby/join/" + code, "POST", {});
        location.href = "/lobby/" + code;
        $("#user-list").reload(location.href + " #user-list");
    }
});