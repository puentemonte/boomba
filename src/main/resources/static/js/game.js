// tener una lista de players
// empezar un turno: elegir player + generar ifx
// get palabra escrita: escribir + enter
// comprobar que es válida: contiene ifx y está en wordlist
// si es válida: pasar turno + sumar palabra correcta + actualizar letras
// si no es válida: quitar vida
// comprobar todo el rato: numvidas + tiempo
// comprobar numplayers

function isInDic(word){
    var fs = require('fs');
    var linesArray = fs.readFileSync('/src/main/resources/static/docs/wordList.txt', 'utf8').split('\n');

}

// timer management
function startTimer(duration, display) {
    var timer = duration, seconds;
    setInterval(() => {
        seconds = parseInt(timer % 60, 10);

        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = seconds;

        if (--timer < 0) {
            timer = duration;
            console.log('se te acabó el tiempo compi');
        }
    }, 1000);
}

// se pueden hacer trampas 
window.onload = () => {
    startTimer(document.getElementById('timer').innerText, document.getElementById('timer'));
};

// get game id
let str = document.URL.split("/");
let id = str[str.length - 1];

// get interfix and user input
let interfix = document.getElementById('interfix').innerText.toLowerCase();
let selector = document.getElementById('word');
selector.addEventListener('keyup', (event) => {
    if(event.key == 'Enter'){
        var word = selector.value.toLowerCase();
        // check if interfix is in user input
        if(word.includes(interfix) && isInDic(word)){
            console.log('ok');
            // manage correct word: changes turn
            document.getElementById('player-rounds').innerHTML = parseInt(document.getElementById('player-rounds').innerHTML) + 1;
            go("/game/correctword/"+id, "POST", {'word': word.toUpperCase()});
        }
        else {
            // lose a life
            let currLives = document.getElementById('num-lives').innerHTML - 1;
            document.getElementById('num-lives').innerHTML = currLives;
            if(currLives <= 0){
                // manage dead player
                go("/game/deadplayer/"+id, "POST", {});
            }
            else{
                // update 
                go("/game/loselife/"+id, "POST", {});
            }
        }
    }
});

// por algún motivo js no deja que todas estas palabras vayan en el mismo array así que para cabezota yo
var wordList1 = ['de','la','que','el','en','los','del','se','las','por','un','para','con','no','una','su','al','lo','como','más','pero','sus','le','ya','fue','este','ha','sí','porque','esta','son','entre','está','cuando','muy','sin','sobre','ser','tiene','también','me','hasta','hay','donde','quien','desde','todo','nos','durante','todos','uno','les','ni','contra','otros','ese','eso','ante','ellos','esto','mí','antes','algunos','qué','unos','yo','otro','otras','otra','él','tanto','esa','estos','mucho','quienes','nada','muchos','cual','sea','poco','ella','estar','había','estas','estaba','nosotros','así','mismo','tú','oír','poder','hablar','cosa','tener','poner','dar','ver','saber','venir','vida','decir','ir','sentir','hacer','parecer','trabajo','persona','punto','tiempo','modo','mundo','palabra','ojos','mano','parte','casa','caso','número','grupo','problema','hecho','lado','forma','agua','país','lugar','proceso','empresa','niño','quedar','mes','alguno','mujer','nivel','luz','gente','pueblo','joven','sentido','familia','día','juego','acuerdo','sistema','ciudad','semana','obra','verdad','tarea','mesa','amigo','cambio','razón','historia','valor','derecho','momento','organización','situación','relación','efecto','idea','papel','carácter','fondo','interés','equipo','condición','medio','religión','total','espacio','acceso','acción','suelo','música','mar','imagen','título','universidad','espíritu','gusto','recurso','período','pista','nombre','fuerza','boca','aire','señor','norte','fin','distrito','calle','dirección','animal','voz','posibilidad','lengua','color','barrio','cultura','autor','paso','información','tratamiento','español','centro','ley','arte','escuela','año','juez','economía','seguridad','comunidad','proyecto','duda','análisis','posición','mayoría','salud','conocimiento','mayor','ejemplo','profesor','investigación','informe','confianza','objeto','preocupación','término','edad','criterio','experiencia','línea','bien','actividad','escritor','discusión','edificio','teléfono','cárcel','espectáculo','práctica','naturaleza','señora','mañana','crisis','ambiente','respeto','texto','esfuerzo','beneficio','producto','educación','doctor','producción','biblioteca','enfermedad','sentimiento','compañía','plan'];
var wordList2 = ['oportunidad','lucha','bebé','sujeto','defensa','compañero','cuenta','definición','piso','nacimiento','institución','uso','guerra','pequeño','reunión','zona','deporte','negocio','estudio','circunstancia','habilidad','intención','clase','programa','serie','estudiante','libro','hombre','material','capacidad','paz','preparación','principio','medida','cliente','barco','oficio','presencia','hijo','necesidad','dinero','periodista','introducción','reparto','clima','prueba','sabor','presente','química','gobierno','adecuación','ayuda','calidad','actitud','modelo','atención','jefe','ciencia','lectura','maestro','asunto','empleo','energía','consejo','aplicación','observación','periodismo','inglés','industria','dificultad','disposición','función','servicio','decisión','dolor','ganancia','realidad','página','miembro','objetivo','consumo','filosofía','herramienta','verano','falta','discurso','bolsa','escena','tendencia','profesión','oficial','pregunta','ruido','explicación','opinión','atmósfera','consecuencia','asociación','precio','costumbre','responsabilidad','desarrollo','ejecución','teoría','autoridad','conversación','diferencia','elemento','crítica','vuelta','sociedad','tierra','formación','juventud','expresión','investigador','método','militar','médico','abogado','plaza','funcionamiento','anuncio','protección','amor','esperanza','ciudadano','fruto','belleza','resto','historiador','operación','conjunto','abuela','riesgo','crimen','memoria','misterio','cerebro','emoción','estrategia','recuerdo','instinto','informática','significado','voluntad','comunicación','mensaje','futuro','padre','estructura','pensamiento','carrera','creencia','cuestión','ejercicio','enfermera','círculo','control','reforma','destino','riqueza','planta','conexión','construcción','ingeniero','tristeza','ceremonia','frase','estrella','personalidad','planteamiento','instrumento','vino','marido','conservación','hospital','miedo','amistad','pago','pasión','compromiso','comprensión','administración','partido','colaboración','libertad','restaurante','observador','participación','personaje','colección','marco','examen','periodo','ministro','creatividad','técnica','pareja','evolución','sensación','experto','solución','movimiento','representación','comercio','promoción','presión','juicio','propósito','potencial','agencia','equilibrio','víctima','jardín','norma','planificación','pintura','comentario','supervivencia','iglesia','felicidad','elección','fenómeno','resultado','crecimiento'];
var wordList3 = ['resistencia','declaración','colaborador','población','beneficiario','argumento','reflexión','inteligencia','realización','cuerpo','identidad','entrega','voto','apoyo','atmosfera','participante','contingente','hermano','gestión','conclusión','contribución','expectativa','materia','procedimiento','transformación','recuperación','factor','frontera','grado','justicia','transporte','interacción','importancia','proyección','identificación','creación','adopción','enfrentamiento','consenso','factura','imposibilidad','expansión','organismo','respuesta','enriquecimiento','indicación','sensibilidad','consecución','corresponsabilidad','transacción','coordinación','prestación','efectividad','interpretación','extensión','influencia','motivación','competencia','incidencia','alternativa','prestigio','transparencia','garantía','sustancia','satisfacción','distancia','innovación','infraestructura','inversión','contenido','trascendencia','aspecto','estabilidad','competitividad','cooperación','mecanismo','rendimiento','simultaneidad','jurisdicción','debería','solamente','cualquier','ningún','primer','siempre','después','nunca','menos','ahora','aunque','seguir','mientras','nuevo','pito','mérito','democracia','comunismo','capitalismo','tortilla','tortita'];