// tener una lista de players
// empezar un turno: elegir player + generar ifx
// get palabra escrita: escribir + enter
// comprobar que es válida: contiene ifx y está en wordlist
// si es válida: pasar turno + sumar palabra correcta + actualizar letras
// si no es válida: quitar vida
// comprobar todo el rato: numvidas + tiempo
// comprobar numplayers

// timer management
function startTimer(duration, display) {
    let timer = duration,
        seconds;
    return setInterval(() => {
        seconds = parseInt(timer % 60, 10);

        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = seconds;

        if (--timer < 0) {
            timer = duration;
            console.log('se te acabó el tiempo compi');
            clearInterval(config.timer)
        }
    }, 1000);
}

window.onload = () => {
    setTimeout(myFunction, +(document.getElementById('timer').innerText) * 1000);
    config.timer = startTimer(document.getElementById('timer').innerText, document.getElementById('timer'));
};

function myFunction() {
    go("/game/timeout/" + id, "POST", {});
}

// get game id
let str = document.URL.split("/");
let id = str[str.length - 1];

// get user input
let selector = document.getElementById('word');
selector.addEventListener('keyup', (event) => {
    if (event.key == 'Enter') {
        var word = selector.value.toLowerCase();
        go("/game/enterword/" + id, "POST", { 'word': word });
    }
});

function updateIfx(ifx) {
    let selector = document.getElementById('interfix');

}