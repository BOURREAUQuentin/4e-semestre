class Render{
    #list = [];

    constructor(list) {
        this.#list = list;
    }

    add(factory){
        this.#list.push(factory);
    }

    display(){
        let resultat = "<ul>";
        for (let evenement of this.#list) {
            resultat += "<li>"+evenement.title()+"</li>";
        }
        return resultat + "</ul>";
    }
}

class Factory{

    constructor(){
        // rien dedans
    }

    createEvent(evenement) {
        if (evenement.type === 'sport') {
            return new Sport(evenement.date, evenement.sport, evenement.teamA, evenement.teamB);
        }
        else if (evenement.type === 'concert') {
            return new Concert(evenement.date, evenement.titre, evenement.artist);
        }
    }
}

class Event{
    #date = "";
    #titre = "";

    constructor(date, titre) {
        this.#date = date;
        this.#titre = titre;
    }

    get titre(){
        return this.#titre;
    }

    title(){
        return this.#date + this.#titre;
    }
}

class Concert extends Event{
    #artiste = "";

    constructor(date, titre, artiste){
        super(date, titre);
        this.#artiste = artiste;
    }

    get artiste(){
        return this.#artiste;
    }

    title(){
        return "Concert de "+ this.#artiste;
    }
}

class Sport extends Event{
    #teamA = "";
    #teamB = "";

    constructor(date, titre, teamA, teamB){
        super(date, titre);
        this.#teamA = teamA;
        this.#teamB = teamB;
    }

    title(){
        return "Rencontre de "+ this.titre +" entre "+ this.#teamA +" et "+ this.#teamB;
    }
}

let events = [
    {
      type: 'sport', 
      sport: 'basket', 
      teamA: 'OLB', 
      teamB: 'LA ROCHELLE', 
      date: '2023-12-25'
    },
    {
      type: 'sport', 
      sport: 'foot', 
      teamA: 'USO', 
      teamB: 'PSG', 
      date: '2024-01-10'
    },
    {
      type: 'concert', 
      artist: 'Machin',
      titre: 'concert de machin',
      date: '2024-01-10'
    },
  ];

let render = new Render([]);
factory = new Factory();
for (let event of events) {
  render.add(factory.createEvent(event));
}
let resultDiv = document.getElementById('result');
resultDiv.innerHTML = render.display();