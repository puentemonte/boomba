// tener una lista de players
// empezar un turno: elegir player + generar ifx
// get palabra escrita: escribir + enter
// comprobar que es válida: contiene ifx y está en wordlist
// si es válida: pasar turno + sumar palabra correcta + actualizar letras
// si no es válida: quitar vida
// comprobar todo el rato: numvidas + tiempo
// comprobar numplayers

let interfix = document.getElementById('interfix').innerText.toLowerCase();
let selector = document.getElementById('word');
selector.addEventListener('keyup', (event) => {
    if(event.key == 'Enter'){
        var word = selector.value.toLowerCase();
        // check if word has interfix
        if(word.includes(interfix)){
            console.log('ok');
        }
        else {
            console.log(':(');
        }
    }
});