function selectNumPlayers(){
    const selector = document.querySelector("#num-players");
    const n = +selector.value;
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    go("/lobby/unp/"+id, "POST", {"numPlayers":n});
}

function selectExplodingTime(){
    const selector = document.querySelector("#exploding-time");
    const n = +selector.value;
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    go("/lobby/uet/"+id, "POST", {"explodingTime":n});
}

function selectIfxLength(){
    const selector = document.querySelector("#ifx-length");
    const n = +selector.value;
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    go("/lobby/uil/"+id, "POST", {"ifxLength":n});
}

const alphabet = document.getElementById('alphabet-btns');
alphabet.addEventListener('click', (event) => {
  const isButton = event.target.nodeName === 'BUTTON';
  if (!isButton) {
    return;
  }
  let split = document.URL.split("/");
  let id = split[split.length - 1];
  console.log(event.target.id);
  go("/lobby/ua/".concat("", id), "POST", {'letter': event.target.id});
})

const topics = document.getElementById('topics-btns');
topics.addEventListener('click', (event) => {
  const isButton = event.target.nodeName === 'BUTTON';
  if (!isButton) {
    return;
  }
  console.log(event.target.id);
  let split = document.URL.split("/");
  let id = split[split.length - 1];
  console.log("/lobby/ut/".concat("", id));
})

function selectPrivate(val) {
  let split = document.URL.split("/");
  let id = split[split.length - 1];
  go("/lobby/up/"+id, "POST", {"priv":val});
}

function startGame() {
  let split = document.URL.split("/");
  let id = split[split.length - 1];
  go("/game/start/"+id, "POST", {});
}

function report(id){
  console.log(id);
  go("/lobby/report", "POST", {"msg":id});
}