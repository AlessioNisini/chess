const canvas = document.getElementById('scacchiera');
const ctx = canvas.getContext('2d');
const pezziLayer = document.getElementById('pezzi-layer'); 
const aElement = document.getElementById('a'); 
const daElement = document.getElementById('da'); 
const mossaElement = document.getElementById('mossa'); 
const statoElement = document.getElementById('stato'); 
const pezziSVG = {
  WP: WP_svg,
  BP: BP_svg,
  /* 
  WR: WR_svg,
  WN: WN_svg,
  // ... (aggiungi tutti gli altri pezzi)
  BP: BP_svg,
  BR: BR_svg,
  BN: BN_svg, */
  // ...
};
var statoPartita = 
[
  { "piece": "WP", "position": "a2" },
  { "piece": "WP", "position": "b2" },
  { "piece": "WP", "position": "c2" },
  { "piece": "WP", "position": "d2" },
  { "piece": "WP", "position": "e2" },
  { "piece": "WP", "position": "f2" },
  { "piece": "WP", "position": "g2" },
  { "piece": "WP", "position": "h2" },
  { "piece": "BP", "position": "a7" },
  { "piece": "BP", "position": "b7" },
  { "piece": "BP", "position": "c7" },
  { "piece": "BP", "position": "d7" },
  { "piece": "BP", "position": "e7" },
  { "piece": "BP", "position": "f7" },
  { "piece": "BP", "position": "g7" },
  { "piece": "BP", "position": "h7" }
];/* 
[
  { "piece": "WR", "position": "a1" },
  { "piece": "WN", "position": "b1" },
  { "piece": "WB", "position": "c1" },
  { "piece": "WQ", "position": "d1" },
  { "piece": "WK", "position": "e1" },
  { "piece": "WB", "position": "f1" },
  { "piece": "WN", "position": "g1" },
  { "piece": "WR", "position": "h1" },
  { "piece": "WP", "position": "a2" },
  { "piece": "WP", "position": "b2" },
  { "piece": "WP", "position": "c2" },
  { "piece": "WP", "position": "d2" },
  { "piece": "WP", "position": "e2" },
  { "piece": "WP", "position": "f2" },
  { "piece": "WP", "position": "g2" },
  { "piece": "WP", "position": "h2" },

  { "piece": "BR", "position": "a8" },
  { "piece": "BN", "position": "b8" },
  { "piece": "BB", "position": "c8" },
  { "piece": "BQ", "position": "d8" },
  { "piece": "BK", "position": "e8" },
  { "piece": "BB", "position": "f8" },
  { "piece": "BN", "position": "g8" },
  { "piece": "BR", "position": "h8" },
  { "piece": "BP", "position": "a7" },
  { "piece": "BP", "position": "b7" },
  { "piece": "BP", "position": "c7" },
  { "piece": "BP", "position": "d7" },
  { "piece": "BP", "position": "e7" },
  { "piece": "BP", "position": "f7" },
  { "piece": "BP", "position": "g7" },
  { "piece": "BP", "position": "h7" }
]; */

const latoCasella = 80;
let primaCasella = null;

let infoPezzo = {
  casella: 'd2',
  posizione: {
    riga: 2, // Riga 2 (dalla base)
    colonna: 'd' // Colonna 'd'
  }
};

function showStato(da, a, mossa, stato) {

}

function disegnaScacchiera() {
  //return;
  for (let i = 0; i < 8; i++) {
    for (let j = 0; j < 8; j++) {
      const x = i * latoCasella;
      const y = j * latoCasella;
      const colore = (i + j) % 2 === 0 ? 'white' : '#b0b0b0';
      ctx.fillStyle = colore;
      ctx.fillRect(x, y, latoCasella, latoCasella);
    }
  }
}

function getCasella(x, y) {
  const colonna = String.fromCharCode(97 + Math.floor(x / latoCasella));
  const riga = 8 - Math.floor(y / latoCasella);
  return { 'casella': colonna + riga, posizione: { riga: riga, colonna: colonna}};
}

canvas.addEventListener('click', (event) => {
  const x = event.offsetX;
  const y = event.offsetY;
  const info = getCasella(x, y);
  const casella = info.casella;

  if (primaCasella === null) {
    // Cerca il pezzo nella casella cliccata
    const pezzo = trovaPezzo(casella); // <--- Nuova funzione di ricerca pezzo

    if (pezzo) { // Se il pezzo esiste
      daElement.textContent = 'da:' + casella;
      primaCasella = casella;
      pezzoSelezionato = pezzo; // <--- Memorizza il pezzo selezionato
      console.log("Prima casella (con pezzo):", primaCasella, pezzoSelezionato);
    } else {
      daElement.textContent = casella + "(invalid)";
      console.log("Primo click non valido (nessun pezzo qui).");
    }
  } else {
    aElement.textContent = 'a:' + casella;
    const mossa = primaCasella + casella;
    console.log("Mossa:", mossa);
    mossaElement.textContent = "Mossa: " + mossa;
    aggiornaStato(info, pezzoSelezionato); 
    //svuotaScacchiera();
    //disegnaPezzi();
    const jsonString = JSON.stringify(statoPartita, null, 2).replace("[", "[<br/>").replace("]", "<br/>]").replaceAll('},\n', "},<br/>");
    statoElement.innerHTML = jsonString;
    primaCasella = null;
    pezzoSelezionato = null; // <--- Resetta il pezzo selezionato
    fetch('http://localhost:8080/api/chess?move=' + mossa)
        .then(response => response.json())
        .then(stato => {
          statoPartita = stato;
          svuotaScacchiera();
          disegnaPezzi();
            //document.getElementById('stato').textContent = JSON.stringify(statoPartita);
            // ... (codice per visualizzare la scacchiera con statoPartita)
        });
  }
});

function trovaPezzo(posizione) {
  // Itera sullo stato della partita e restituisce il pezzo trovato nella posizione
  for (const pezzo of statoPartita) {
    if (pezzo.position === posizione) {
      return pezzo; // Restituisce l'oggetto pezzo
    }
  }
  return null; // Nessun pezzo trovato in quella posizione
}

function svuotaScacchiera() {
  pezziLayer.innerHTML = "";
}

function disegnaPezzi() {
  statoPartita.forEach(pezzo => {
    var size = pezzo.piece.substring(1) == "P" ? 0.75 : 0.9;
    const x = (pezzo.position.charCodeAt(0) - 'a'.charCodeAt(0)) * latoCasella + latoCasella * (1 - size) / 2;
    const y = (8 - pezzo.position.substring(1)) * latoCasella + latoCasella * (1 - size) / 2;
    const pezzoSVG = creaPezzoSVG(pezziSVG[pezzo.piece], latoCasella * size, latoCasella * size);
    const div = document.createElement('div');
    div.style.position = 'absolute';
    div.style.left = x + 'px';
    div.style.top = y + 'px';
    div.appendChild(pezzoSVG);
    pezziLayer.appendChild(div);
  })
}

function aggiornaStato(info, pezzo) {
  infoPezzo = info;
  for (const p of statoPartita) {
    if (p.position === pezzo.position) {
      p.position = info.casella;
      break;
    }
  }
}

disegnaScacchiera();
disegnaPezzi();