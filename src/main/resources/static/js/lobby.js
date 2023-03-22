console.log("ok");
document.addEventListener("load", ()=>{
    const selector = document.querySelector("#num-players");
    selector.addEventListener(() => {
        const n = +selector.value;
        console.log(n);
        go("/lobby/id-de-juego", "POST", {n});
    })
});