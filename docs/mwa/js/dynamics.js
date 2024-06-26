/**
 * @file: dynamics.js
 * @author: Juan David Sanchez Leal
 * */

function listenerSetBodyConfig(){
	document.body.style.paddingTop = calculateMenuHeight()+"px"
	document.getElementById("menuPanel").style.height = (window.innerHeight-calculateMenuHeight())+"px"
	document.getElementById("menuPanel").style.top = calculateMenuHeight()+"px"
}

var oldScreenPos = window.scrollY
function listenerMenuPosition(){
	var actualScreenPos = window.scrollY
	var diff = oldScreenPos - actualScreenPos
	if(diff < -5){
		document.getElementById("menuBar").style.top = "-"+calculateMenuHeight()+"px"
		document.getElementById("menuPanel").style.opacity = "0"
		document.getElementById("menuPanel").style.zIndex = -1
	}else if(diff > 0){
		document.getElementById("menuBar").style.top= "0px"
	}
	oldScreenPos = actualScreenPos
}

function calculateMenuHeight() {
	var menu = document.getElementById("menuBar")
	var menuCH = window.getComputedStyle(menu)
	var height = parseInt(menuCH.height.split("px")[0])
	var padding = parseInt(menuCH.paddingBottom.split("px")[0])
	return (height+(padding*2))
}

/**
 * This function do all init work.
 * */
function onLoad() {
	window.scrollTo(0,0)
	setGlobalListeners()
	listenerManageMenuPanel()
}


function listenerManageMenuPanel() {
	var menuPanel = document.getElementById("menuPanel")
	var menuPanelCS = window.getComputedStyle(menuPanel)
	var menuPanelCSOpacity = menuPanelCS.opacity
	//console.log("Last State: " + menuPanelCSOpacity)
	if(menuPanelCSOpacity == 1){
		document.getElementById("menuPanel").style.opacity = "0"
		document.getElementById("menuPanel").style.zIndex = -1
	}else{
		document.getElementById("menuPanel").style.zIndex = 1
		document.getElementById("menuPanel").style.opacity = "1"
	}
}

/**
 * TODO: Fix this stupid thing
 * */
function setGlobalListeners() {
	//Eventos Propios
	document.getElementById("menuBtn").addEventListener("click", listenerManageMenuPanel)
}

function hiddeById(id){
    document.getElementById(id).style.display = "none";
}

function showById(id, display){
    document.getElementById(id).style.display = display;
}

//Magic.....
onLoad();

