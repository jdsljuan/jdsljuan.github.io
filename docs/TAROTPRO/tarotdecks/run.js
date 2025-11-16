let DECKENGINE = {
    /** ##################### FIELDS ###################################  */
    className : 'card',
    modalId : 'selectedCardsModal',
    btnClassName : 'btnSelect',
    deckSets : [
        {
            name : "Rider Waite",
            imageURI : "/tarotdecks/rider/img"
        },
        {
            name : "Sun & Moon",
            imageURI : "/tarotdecks/sun-moon/img"
        },
        {
            name : "White Numen",
            imageURI : "/tarotdecks/white-numen/img"
        },
        {
            name : "Sacred Art",
            imageURI : "/tarotdecks/sacred-art/img"
        },
        {
            name : "Omegaland",
            imageURI : "/tarotdecks/omegaland/img"
        },
        {
            name : "Napo",
            imageURI : "/tarotdecks/napo/img"
        },
        {
            name : "Golden Wheel",
            imageURI : "/tarotdecks/golden-wheel/img"
        },
        {
            name : "Golden Thread",
            imageURI : "/tarotdecks/golden-thread/img"
        },
        {
            name : "Fablemakers",
            imageURI : "/tarotdecks/fablemakers/img"
        },
    ],
    actualDeck: null, // Idx of the Actual Deck,

    /** ##################### METHODS ###################################  */
    initEngine : function () {
        //Verifications
        let arcana = localStorage.getItem('ACTUAL_ARCANA');
        if(arcana === null) localStorage.setItem('ACTUAL_ARCANA', '1');

        let deck = localStorage.getItem('ACTUAL_DECK');
        if(deck === null) localStorage.setItem('ACTUAL_DECK', '0');

        let cardback = localStorage.getItem('ACTUAL_CARDBACK');
        if(cardback === null) localStorage.setItem('ACTUAL_CARDBACK', '1');

        // Cards Events
        let cards = document.getElementsByClassName(this.className);
        for (let j = 0; j < cards.length; j++) {
            cards.item(j).addEventListener('click', function(){
                DECKENGINE.activeCard(cards.item(j));
            });
        }
        let btns = document.getElementsByClassName(this.btnClassName);
        for (let j = 0; j < btns.length; j++) {
            btns.item(j).addEventListener('click', function(){
                DECKENGINE.selectCard(btns.item(j).parentElement);
            });
        }
        // OTROS EVENTOS
        document.getElementById('bluredScreen').addEventListener('click', ()=>{ DECKENGINE.activeCard(null); });
        document.getElementById('actualDeckSelect').addEventListener('change', function(event) {
            DECKENGINE.setActualDeck(event.target.value);
        });
        document.getElementById('cardbackSelect').addEventListener('change', function(event) {
            DECKENGINE.setCardBack(event.target.value);
        });
        
        // DEL MENU
        document.getElementById('btnShuffleCards').addEventListener('click', ()=>{ DECKENGINE.shuffleCards(); });
        document.getElementById('btnShowHand').addEventListener('click', ()=>{ DECKENGINE.showSelectedCards(); });
        
        document.getElementById('btnNextArcana').addEventListener('click', ()=>{ DECKENGINE.nextArcana(); });
        document.getElementById('btnSelectDeck').addEventListener('click', ()=>{ 
            document.getElementById('actualDeckSelect').value = DECKENGINE.getActualDeck();
            document.getElementById('actualDeckSelectModal').showModal();
        });
        document.getElementById('btnSelectCardback').addEventListener('click', ()=>{ 
            document.getElementById('cardbackSelect').value = parseInt(localStorage.getItem('ACTUAL_CARDBACK'));
            document.getElementById('cardbackSelectModal').showModal();
        });
        document.getElementById(this.modalId).addEventListener('close', ()=>{ DECKENGINE.activeCard(null)});
        document.getElementById('btnHelp').addEventListener('click', ()=>{ DECKENGINE.showAboutModal(); });
        
        // Coloca los Ajustes Guardatos por el Usuario.
        this.setArcana(parseInt(localStorage.getItem('ACTUAL_ARCANA')))
        this.setActualDeck(parseInt(localStorage.getItem('ACTUAL_DECK')))
        this.setCardBack(parseInt(localStorage.getItem('ACTUAL_CARDBACK')))

        this.showAboutModal()
    },

    // Card Manage
    showSelectedCards : function(){
        this.activeCard(undefined);
        document.getElementById(this.modalId).showModal();
    },
    selectCard : function(element){
        element.style.left = '-200px';
        element.style.top  = '-200px';
        let imgNumber = element.id.toString().split('-')[1];
        let imgElement = document.createElement('img'); 
        imgElement.src = this.deckSets[this.getActualDeck()].imageURI+'/'+imgNumber+'.jpg'
        imgElement.style.transform = 'rotate('+(180*parseInt(element.dataset.rotated))+'deg)';
        imgElement.classList.add('group-'+element.dataset.deckgroup)
        let spanElement = document.createElement('a'); 
        spanElement.target = '_blank';
        spanElement.href = './docs.html?card='+imgNumber;
        spanElement.appendChild(imgElement)
        document.getElementById(this.modalId).appendChild(spanElement)
    },
    activeCard : function(element) {
        let cards = document.getElementsByClassName(this.className);
        if(element === null){
            for (let j = 0; j < cards.length; j++) {
                cards.item(j).classList.remove('active', 'inactive');
            }
            return null;
        }
        if(element === undefined){
            for (let j = 0; j < cards.length; j++) {
                cards.item(j).classList.add('inactive'); 
                cards.item(j).classList.remove('active');
            }
            return undefined;
        }
        let fstValueA = element.classList.contains('active');
        let fstValueI = element.classList.contains('inactive');
        for (let j = 0; j < cards.length; j++) {
            if(fstValueA || fstValueI){
                cards.item(j).classList.remove('active', 'inactive');
            }else{
                if(element === cards.item(j)){ 
                    cards.item(j).classList.add('active'); 
                    cards.item(j).classList.remove('inactive');
                }else{
                    cards.item(j).classList.add('inactive'); 
                }
            }
        }
    },
    //----------------------------------------------------------

    // Deck Manage
    getActualDeck : function(){
        if(this.actualDeck === null){
            this.actualDeck = 0; 
        }
        return this.actualDeck; 
    },
    setActualDeck : function(selectedDeck = 0){
        localStorage.setItem('ACTUAL_DECK', selectedDeck);
        document.getElementById('actualDeckSelectPreview').src = DECKENGINE.deckSets[selectedDeck].imageURI+'/0.jpg'
        this.actualDeck = selectedDeck;
        return this.actualDeck;
    },
    setCardBack : function(cardback) {
        localStorage.setItem('ACTUAL_CARDBACK', cardback);
        let cards = document.getElementsByClassName(this.className);
        for (let j = 0; j < cards.length; j++) {
            cards.item(j).classList.remove('card-1', 'card-2', 'card-3', 'card-4', 'card-5', 'card-6', 'card-7', 'card-8', 'card-9', 'card-10', 'card-11');
            cards.item(j).classList.add('card-'+cardback);
        }
        document.getElementById('cardbackSelectPreview').src = './cardbacks/'+cardback+'.jpg'
    },
    //----------------------------------------------------------
        
    // Arcanas Management
    nextArcana : function(){
        const MAX_ARCANA = 33;
        let actualArcana = parseInt(localStorage.getItem('ACTUAL_ARCANA'));; 
        actualArcana++; actualArcana = actualArcana > MAX_ARCANA ? 1 : actualArcana;
        this.setArcana(actualArcana);
    },
    setArcana : function(actualArcana){
        document.getElementById('bluredScreen').dataset.arcana = actualArcana;
        document.getElementById('bluredScreen').style.setProperty('background-image', "url('./arcanas/"+actualArcana+".jpg')", "important");
        localStorage.setItem('ACTUAL_ARCANA', actualArcana);
    },
    //----------------------------------------------------------

    // Shuffle Cards Methods
    shuffleCards : function(){
        this.centerCards();
        setTimeout(() => {
            this.disperseCards();
            setTimeout(() => {
                this.centerCards();
                setTimeout(() => {
                    this.disperseCards();
                    setTimeout(() => {
                        this.centerCards();
                        setTimeout(() => {
                            this.disperseCards();
                        }, 500);
                    }, 1500);
                }, 500);
            }, 1500);
        }, 1000);
        document.getElementById(this.modalId).innerHTML = '';
    },
    centerCards : function(){
        const w_half = (window.innerWidth/2)-50;
        const h_half = (window.innerHeight/2)-50;
        let cards = document.getElementsByClassName(this.className);
        for (let j = 0; j < cards.length; j++) {
            cards.item(j).style.left = w_half+(Math.floor(Math.random()*100))+'px';
            cards.item(j).style.top = h_half+(Math.floor(Math.random()*100))+'px';
        }
    },
    disperseCards : function () {
        const w_max = window.innerWidth-200;
        const h_max = window.innerHeight-250;
        let cards = document.getElementsByClassName(this.className);
        for (let j = 0; j < cards.length; j++) {
            cards.item(j).style.left = 50+(Math.floor(Math.random()*w_max))+'px';
            cards.item(j).style.top = 50+(Math.floor(Math.random()*h_max))+'px';
            let rotated = Math.round(Math.random());
            let zindex = Math.round(Math.random()*10); zindex = zindex < 3 ? 3 : zindex;
            //cards.item(j).style.transform = 'rotate('+(180*rotated)+'deg)';
            cards.item(j).dataset.rotated = rotated;
            cards.item(j).style.zIndex = zindex;
        }
    },
    //----------------------------------------------------------

    showAboutModal : function(){
        document.getElementById('tarotProModal').showModal();
    },
};




