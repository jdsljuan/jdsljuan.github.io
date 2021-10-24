let msAnimation = 150
let msInterval = msAnimation + 50
var colums = 20

function createElements() {

	var layer1 = document.createElement("div")
	layer1.setAttribute('id', "layer1")
	layer1.style.position = "fixed"
	layer1.style.top = "0px"
	layer1.style.left = "0px"

	for (var i = 0; i < colums; i++) {
		var divAux = document.createElement("div")
		divAux.setAttribute("id", i+"-")
		divAux.style.transition = "background-color "+msAnimation+"ms linear"
		divAux.style.color = "white"
		divAux.style.opacity = "90%"
		layer1.appendChild(divAux)
	}

	var bannerText = document.createElement("div")
	bannerText.setAttribute("id", "banner")
	bannerText.style.color = getPaletteColor("BASE")
	bannerText.style.textAlign = "center"
	bannerText.innerHTML += "CIMARRON"
	bannerText.style.fontFamily = "'Roboto', sans-serif"

	layer1.appendChild(bannerText)

	document.body.appendChild(layer1)

	//Next Layer
	var layer2 = document.createElement("div")
	layer2.setAttribute('id', "layer2")
	layer2.style.position = "fixed"
	layer2.style.top = "0px"
	layer2.style.left = "0px"

	var promoText = document.createElement("div")
	promoText.setAttribute("id", "promotext")
	promoText.style.color = getPaletteColor("NEUTRAL")
	promoText.innerHTML += "Bienvenido al Cancionerio Cimarron"
	promoText.style.fontFamily = "'Roboto', sans-serif"
	promoText.style.transition = "top "+msAnimation+"ms linear"
	promoText.style.transition = "top "+msAnimation+"ms linear"
	layer2.appendChild(promoText)

	for (var i = 1; i < 9; i++) {
		var img = new Image()
		img.style.transition = "top "+msAnimation+"ms linear"
		img.src = "./img/img"+i+".png"
		img.setAttribute("id", "img"+i)
		img.style.position = "fixed"
		img.style.boxShadow = "2px 2px 10px black, -2px -2px 10px black"
		layer2.appendChild(img)
	}

	document.body.appendChild(layer2)
}

function reSizeAndDrawElements() { 

	var columSize =  parseInt(window.innerWidth, 10)/colums
	var rowSize = parseInt(window.innerHeight,10)
	var ratio = window.innerWidth/window.innerHeight
	//Contruye los Divs y los coloca
	for (var i = 0; i < colums; i++) {
		var divAux = document.getElementById(i+"-")
		divAux.style.backgroundColor = getPaletteColor("BASE")
		divAux.style.width = columSize+"px"
		divAux.style.height = rowSize+"px"
		divAux.style.position = "fixed"
		divAux.style.top = 0+"px"
		divAux.style.left = (columSize*i)+"px"			
	}

	//Se encarga de crear el banner y ponerlo
	var bannerText = document.getElementById("banner")
	bannerText.style.position = "fixed"
	bannerText.style.right = 0+"px"
	bannerText.style.width = window.innerWidth+"px"
	
	var promoText = document.getElementById("promotext")
	promoText.style.position = "fixed"
	promoText.style.left = window.innerWidth*0.05+"px"
	promoText.style.width = window.innerWidth*0.90+"px"
	promoText.style.top = window.innerHeight*0.03+"px"

	for (var i = 1; i < 9; i++) {
		var img = document.getElementById("img"+i)
		img.style.position = "fixed"
		if(ratio<1){//Celulares
			img.style.left = window.innerWidth*0.05+"px"
			img.style.width = window.innerWidth*0.90+"px"
			img.style.height = window.innerWidth*0.90+"px"
			img.style.top = window.innerWidth*0.50+"px"
		}else{//Pantallas
			img.style.left = window.innerWidth*0.3+"px"
			img.style.width = window.innerWidth*0.40+"px"
			img.style.height = window.innerHeight*0.60+"px"
			img.style.top = window.innerHeight*0.25+"px"
		}
	}


	if(ratio<1){//Celulares
		bannerText.style.fontSize = window.innerHeight*0.05+"px"
		bannerText.style.top = (window.innerHeight*0.45)+"px"
		bannerText.style.height = window.innerHeight*0.05+"px"

		promoText.style.fontSize = window.innerWidth*0.1+"px"
		promoText.style.height = window.innerWidth*0.1+"px"
		promoText.style.textAlign = "left"
	}else{//Pantallas Grandes.
		bannerText.style.top = (window.innerHeight*0.4)+"px"
		bannerText.style.fontSize = window.innerHeight*0.15+"px"
		bannerText.style.height = window.innerHeight*0.15+"px"

		promoText.style.fontSize = window.innerWidth*0.05+"px"
		promoText.style.height = window.innerWidth*0.05+"px"
		promoText.style.textAlign = "center"
	}

	var layer1 = document.getElementById("layer1")
	layer1.style.backgroundColor = getPaletteColor("BASE")
	layer1.style.width = window.innerWidth+"px"
	layer1.style.height = window.innerHeight+"px"

	//Layer 2 Stuff
	var layer2 = document.getElementById("layer2")
	layer2.style.backgroundColor = getPaletteColor("BASE")
	layer2.style.width = window.innerWidth+"px"
	layer2.style.height = window.innerHeight+"px"
}

var actualColumn = -5
function reDrawWave() {
	var columSize =  parseInt(window.innerWidth, 10)/colums

	if(actualColumn > colums+3){
		actualColumn = -5
	}

	var divAux = document.getElementById(actualColumn+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("HEAVY")	
	}
	var divAux = document.getElementById(actualColumn+1+"-")
	if(divAux !== null){	
		divAux.style.backgroundColor = getPaletteColor("NEUTRAL")
	}
	var divAux = document.getElementById(actualColumn+2+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("LIGHT")
	}
	var divAux = document.getElementById(actualColumn+3+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("BASE")
	}
	var divAux = document.getElementById(actualColumn-1+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("NEUTRAL")
	}
	var divAux = document.getElementById(actualColumn-2+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("LIGHT")
	}
	var divAux = document.getElementById(actualColumn-3+"-")
	if(divAux !== null){
		divAux.style.backgroundColor = getPaletteColor("BASE")
	}

	actualColumn++
}

function getPaletteColor(colorName) {
	switch(colorName){
		case "HEAVY":
			return "rgb(179,104,6)"
		case "NEUTRAL":
			return "rgb(217,171,88"
		case "LIGHT":
			return "rgb(255,237,169)"
		case "BASE":
			return "rgb(255,255,255)"
		default:
		return ""
	}
}

var slideTimer
var activeLayer = 2
var msSlider = 3000
function changeLayer(){
	if(activeLayer == 1){
		var layer1 = document.getElementById("layer1")
		layer1.style.visibility = "hidden"
		var layer2 = document.getElementById("layer2")
		layer2.style.visibility = "visible"
		sliderOn()
		slideTimer = window.setInterval(sliderOn, msSlider)
		activeLayer = 2
	}else if (activeLayer == 2) {
		var layer2 = document.getElementById("layer2")
		layer2.style.visibility = "hidden"
		window.clearInterval(slideTimer)
		sliderOff()
		var layer1 = document.getElementById("layer1")
		layer1.style.visibility = "visible"
		activeLayer = 1
	}
}

var activeSlide = 1
var promoTextSlider = ["Todos tus Acordes en un solo lugar.",
					   "Filtra la coleccion al instante!",
					   "En Modo Eliminar tus click borran!",
					   "Pon Acordes a letras en el Editor.",
					   "Recuerda llenar todos los campos.",
					   "Dos pantallas para Expertos.",
					   "Edita la derecha, la otra es fija.",
					   "Versiones para imprimir!"]
function sliderOn(){
	if(activeSlide > 8){
		activeSlide = 1
	}
	for (var i = 1; i < 9; i++) {
		if(activeSlide == i){
			var divAux = document.getElementById("img"+i)
			divAux.style.visibility = "visible"
			var promoText = document.getElementById("promotext")
			promoText.innerHTML = promoTextSlider[i-1]
		}else{
			var divAux = document.getElementById("img"+i)
			divAux.style.visibility = "hidden"
		}
	}
	activeSlide++
}

function sliderOff() {
	for (var i = 1; i < 9; i++) {
		var divAux = document.getElementById("img"+i)
		divAux.style.visibility = "hidden"
	}
}

//Crea los Elementos
createElements()
//Les da Stylos y los posiciona
reSizeAndDrawElements()
//ACtiva el Layer1
changeLayer()

window.addEventListener("resize", reSizeAndDrawElements)
window.addEventListener("click", changeLayer)

window.setInterval(reDrawWave, msInterval)
