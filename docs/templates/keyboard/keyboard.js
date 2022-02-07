/**
 * Keyboard es un simulador de teclado enfocado en el uso 
 * rapido y concreto. Provee la capacidad de editar el valor
 * de las etiquetas input que poseen la clase keyboard-input
 * ofreciendo como entrada digitos, caracteres y simbolos
 * basicos.
 * 
 * Author: Juan David Sanchez Leal
 * Date: 06/02/2022
 */

/**
 * function init Inicia el traclado, crea los contenedores y
 * ejecuta las acciones para crear las teclas.
 */
function init(){
    var rangosASCII;
    var keyboardElement;
    var keyboardBackscreen;

    rangosASCII = {
        "ranges" : [
            {"low":48, "high": 57, "name":"numbers"},
            {"low":65, "high": 90, "name": "mayus"},
            {"low":209, "high": 209, "name": "mayus"},
            {"low":97, "high": 122,"name": "minus"},
            {"low":241, "high": 241,"name": "minus"},
            {"low":33, "high": 47, "name": "smb1"},
            {"low":58, "high": 64, "name":"smb2"}
        ]};

    keyboardBackscreen = document.createElement("div");
    keyboardBackscreen.setAttribute("id","keyboard-backscreen");
    keyboardBackscreen.addEventListener("click", hideKeyboard);

    keyboardElement = document.createElement("div");
    keyboardElement.setAttribute("id","keyboard");

    document.body.appendChild(keyboardBackscreen);
    keyboardBackscreen.appendChild(keyboardElement);

    createKeyboard(rangosASCII, keyboardElement);
    configKeyboardListeners();
}

/**
 * 
 * @param {JSON} ranges Objeto con un array llamado rangos
 * que contienen objectos con las parejas para low, high 
 * y name que son los limites inferior, superior y el nombre del rango.
 * <b>Ej. ranges = "ranges" : [{"low": 23, "high":25, "name":"charars"}]</b>
 * @param {HTMLObjectElement} keyboard El contenedor div que va a albergar
 * todas las teclas y sobre el cual se construyen las teclas
 * @returns devuelve 1 si todo sale bien.
 */
function createKeyboard(ranges, keyboard){

    //Crea todas las teclas apartir del Array ranges.ranges y las agrega a keyboard
    for(var actualRange = 0; actualRange < ranges.ranges.length; actualRange++){
        for(var itr = ranges.ranges[actualRange].low; itr <= ranges.ranges[actualRange].high; itr++){
            var key = document.createElement("span");
            key.classList.add("key");
            key.classList.add("key-group-"+ranges.ranges[actualRange].name);
            if(["numbers","minus"].includes(ranges.ranges[actualRange].name)){
                key.classList.add("key-status-on");
            }else{
                key.classList.add("key-status-off");
            }
            key.innerText = String.fromCharCode(itr);
            key.addEventListener("click", onKeyClick);
            keyboard.appendChild(key);
        }
    }

    //Tecla de Caracteres Especiales.
    var smbKey = document.createElement("span");
    smbKey.classList.add("key");
    smbKey.setAttribute("id","key-aux-smb");
    smbKey.classList.add("key-status-on");
    smbKey.innerText = "*/=";
    smbKey.addEventListener("click", showSymbols);
    keyboard.appendChild(smbKey);

    //Tecla de Cambio de Mayusculas y Minusculas.
    var mayusKey = document.createElement("span");
    mayusKey.classList.add("key");
    mayusKey.setAttribute("id","key-aux-shift");
    mayusKey.classList.add("key-status-on");
    mayusKey.innerText = String.fromCharCode(8613);
    mayusKey.addEventListener("click", toggleKeys);
    keyboard.appendChild(mayusKey);

    //Tecla de Espacio
    var spaceKey = document.createElement("span");
    spaceKey.classList.add("key");
    spaceKey.setAttribute("id","key-aux-space");
    spaceKey.classList.add("key-status-on");
    spaceKey.innerText = String.fromCharCode(9251);
    spaceKey.addEventListener("click", onKeyClick);
    keyboard.appendChild(spaceKey);

    //Tecla de Borrado
    var eraseKey = document.createElement("span");
    eraseKey.classList.add("key");
    eraseKey.setAttribute("id","key-aux-backspace");
    eraseKey.classList.add("key-status-on");
    eraseKey.innerText = String.fromCharCode(8592);
    eraseKey.addEventListener("click", onKeyClick);
    keyboard.appendChild(eraseKey);

    return 1;
}

/**
 * function toggleKeys Cambia la visibilidad para los grupos 
 * key-group-minus y key-group-mayus asemejando la accion de SHIFT 
 * en el teclado. Resalta el color de la tecla dando un estilo predefinido.
 * 
 * @param {Event} e Evento pasado por el callback de addEventListener.
 * @returns Retorna 0 si todo sale bien. 
 */
function toggleKeys(e){
    e.stopPropagation();
    if(isOnGroupByClass("key-group-minus")){
        turnOffGroupByClass("key-group-minus");
        turnOnGroupByClass("key-group-mayus");
        e.target.style.backgroundColor = "var(--keyboard-key-aux-hover-color)";
    }else if(isOnGroupByClass("key-group-mayus")){
        turnOffGroupByClass("key-group-mayus");
        turnOnGroupByClass("key-group-minus");
        e.target.style.backgroundColor = "var(--keyboard-key-aux-background-color)";
    }else if(isOffGroupByClass("key-group-mayus") && isOffGroupByClass("key-group-minus")){
        e.target.style.backgroundColor = "var(--keyboard-key-aux-background-color)";
    }
    return 0;
}

/**
 * function showSymbols Muestra los caracteres especiales. Resalta el color 
 * de la tecla dando un estilo predefinido.
 * 
 * @param {Event} e Evento pasado por el callback de addEventListener.
 * @returns Retorna 0 si se activaron, o  1 si se han desactivado. 
 */
function showSymbols(e){
    e.stopPropagation();
    if(isOnGroupByClass("key-group-smb1") && isOnGroupByClass("key-group-smb2")){
        turnOffGroupByClass("key-group-smb1");
        turnOffGroupByClass("key-group-smb2");
        turnOnGroupByClass("key-group-mayus");
        e.target.style.backgroundColor = "var(--keyboard-key-aux-background-color)";
        document.getElementById("key-aux-shift").click();
        return 1;
    }else if(isOffGroupByClass("key-group-smb1") && isOffGroupByClass("key-group-smb2")){
        turnOffGroupByClass("key-group-minus");
        turnOffGroupByClass("key-group-mayus");
        turnOnGroupByClass("key-group-smb1");
        turnOnGroupByClass("key-group-smb2");
        e.target.style.backgroundColor = "var(--keyboard-key-aux-hover-color)";
        document.getElementById("key-aux-shift").click();
        return 0;
    }
}

/**
 * function configKeyboardListeners Buscar en los input Tag la constante
 * flagClassName y les asigna un escucha en caso de ser clickeados, que
 * muestra el traclado.
 */
function configKeyboardListeners(){
    const flagClassName = "keyboard-input";
    var inputElements = document.getElementsByTagName("input");
    for(var itr = 0; itr < inputElements.length; itr++){
        if(inputElements.item(itr).classList.contains(flagClassName)){
            inputElements.item(itr).addEventListener("click", showKeyboard);
        }
    }
}

/**
 * function onKeyClick Se encarga de editar el texto dentro del input
 * que previamente ha activado el monstrar teclado.
 * 
 * @param {Event} e evento generado por e sistema.
 * @return integer Retorna 0 si todo sale bien, 1 de lo contrario.
 */
function onKeyClick(e){
    e.stopPropagation();
    if(__onEditInput == null && __onEditInputCursor == null){
        console.error("test press??");
        return 1;
    }else{
        var substr1 = __onEditInput.value.substring(0, __onEditInputCursor);
        var substr2 = __onEditInput.value.substring(__onEditInputCursor, __onEditInput.value.length);
        //Borrado
        if(e.target.innerText == String.fromCharCode(8592)){
            __onEditInput.value = substr1.substring(0, substr1.length-1)+substr2;
            if(__onEditInputCursor < 0){ 
                __onEditInputCursor=0;
            }else{
                __onEditInputCursor--;
            }
            return 0;
        //Espacio    
        }else if(e.target.innerText == String.fromCharCode(9251)){
            __onEditInput.value = substr1+" "+substr2;
            __onEditInputCursor++;
            return 0;
        //Tecla Cualquiera no funcional.
        }else{
            __onEditInput.value = substr1+e.target.innerText+substr2;
            __onEditInputCursor++;
            return 0;
        }
        //TEST: console.log("Keyboard Key Press : "+ e.target.innerText);
    }

}

//TOOL function 
var __onEditInput = null;
var __onEditInputCursor=null;
function showKeyboard(e){
    e.preventDefault();
    e.stopPropagation();
    e.stopImmediatePropagation();
    var keyboardBackscreen = document.getElementById("keyboard-backscreen");
    __onEditInput = e.target;
    __onEditInputCursor = e.target.selectionStart;
    if(__onEditInputCursor == null) __onEditInputCursor = 0;
    keyboardBackscreen.style.display = "initial";
    e.target.blur();
}

//TOOL function 
function hideKeyboard(e){
    var keyboardBackscreen = document.getElementById("keyboard-backscreen");
    keyboardBackscreen.style.display = "none";
    if(__onEditInput != null) __onEditInput = null;
    if(__onEditInputCursor != null) __onEditInputCursor = null;
}

//TOOL function 
function isOnGroupByClass(string){
    var elements = document.getElementsByClassName(string);
    return elements.item(0).classList.contains("key-status-on");
}

//TOOL function 
function isOffGroupByClass(string){
    var elements = document.getElementsByClassName(string);
    return elements.item(0).classList.contains("key-status-off");
}

//TOOL function 
function turnOnGroupByClass(string){
    var elements = document.getElementsByClassName(string);
    for(var itr = elements.length-1; itr >= 0; itr--){
        elements.item(itr).classList.replace("key-status-off", "key-status-on");   
    } 
}

//TOOL function 
function turnOffGroupByClass(string){
    var elements = document.getElementsByClassName(string);
    for(var itr = elements.length-1; itr >= 0; itr--){
        elements.item(itr).classList.replace("key-status-on", "key-status-off");   
    } 
}

//Crea el teclado.
init();
