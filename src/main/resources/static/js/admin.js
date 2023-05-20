function selectReportedUser(userId, numgames){
    console.log(userId);
    console.log(numgames);
    document.getElementById("games-card").innerHTML = `<div class = "even-lighter-cards">
                                                        <div class = "even-lighter-crd">
                                                            <div class = "col">
                                                                <div class = "row">
                                                                    <div class = "col">
                                                                        <p>PARTIDAS JUGADAS</p>
                                                                    </div>
                                                                    <div class = "col-md-auto">
                                                                        <p class = "white-purple-report-stats text-center">${numgames}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class = "text-center mt-btn">
                                                            <button onclick="seeReportedGames(${userId})" class="btn-form" type="submit">VER PARTIDAS</button>
                                                        </div>
                                                    </div>`;
    /*let chat = document.querySelector(".chat-scroll");
               chat.insertAdjacentHTML("beforeend", renderMsg(x));*/
}

function seeReportedGames(uid){
    console.log(uid);
    location.href = "/report/" + uid;
}