console.log("ok");
function selectNumPlayers(){
    const selector = document.querySelector("#num-players");
    const n = +selector.value;
    console.log(n);
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    console.log("/lobby/unp/".concat("", id));
    // go("/lobby/unp".concat("", id), "POST", {n});
}

function selectExplodingTime(){
    const selector = document.querySelector("#exploding-time");
    const n = +selector.value;
    console.log(n);
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    console.log("/lobby/uet/".concat("", id));
    // go("/lobby/uet".concat("", id), "POST", {n});
}

function selectIfxLength(){
    const selector = document.querySelector("#ifx-length");
    const n = +selector.value;
    console.log(n);
    let split = document.URL.split("/");
    let id = split[split.length - 1];
    console.log("/lobby/uil/".concat("", id));
    // go("/lobby/uil".concat("", id), "POST", {n});
}

const alphabet = document.getElementById('alphabet-btns');
alphabet.addEventListener('click', (event) => {
  const isButton = event.target.nodeName === 'BUTTON';
  if (!isButton) {
    return;
  }
  console.log(event.target.id);
  let split = document.URL.split("/");
  let id = split[split.length - 1];
  console.log("/lobby/ua/".concat("", id));
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

/*document.addEventListener("load", ()=>{
    const selector = document.querySelector("#num-players");
    selector.addEventListener("change", () => {
        const n = +selector.value;
        console.log(n);
        let split = document.URL().split("/");
        let id = split[split.length - 1];
        console.log("/lobby/".concat("", id));
        // go("/lobby/".concat("", id), "POST", {n});
    })
});*/