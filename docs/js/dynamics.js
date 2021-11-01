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
	listenerSetBodyConfig()
	listenerManageMenuPanel()
	hideOnAppContent()
	document.getElementById("menuPanelHomeBtn").click()
}


function listenerManageMenuPanel() {
	var menuPanel = document.getElementById("menuPanel")
	var menuPanelCS = window.getComputedStyle(menuPanel)
	var menuPanelCSOpacity = menuPanelCS.opacity
	console.log("Last State: " + menuPanelCSOpacity)
	if(menuPanelCSOpacity == 1){
		document.getElementById("menuPanel").style.opacity = "0"
		document.getElementById("menuPanel").style.zIndex = -1
	}else{
		document.getElementById("menuPanel").style.zIndex = 1
		document.getElementById("menuPanel").style.opacity = "1"
	}
}

/**
 * @param layerName Event generated for the click of the panel's divs
 * */
function listenerShowLayer(layerName) {
	setLayersOff()

	var colorOnLayer = "darkcyan"
	if (layerName.target.id == "menuPanelHomeBtn") {
		document.getElementById("menuPanelHomeBtn").style.color = colorOnLayer
		document.getElementById("homeLayer").style.display = "block"
	}else if(layerName.target.id == "menuPanelAboutMeBtn"){
		document.getElementById("menuPanelAboutMeBtn").style.color = colorOnLayer
		document.getElementById("aboutMeLayer").style.display = "block"
	}else if(layerName.target.id == "menuPanelContactMeBtn"){
		document.getElementById("menuPanelContactMeBtn").style.color = colorOnLayer
		document.getElementById("contactMeLayer").style.display = "block"
	}else if(layerName.target.id == "menuPanelStudiesBtn"){
		document.getElementById("studiesLayer").style.display = "block"
		document.getElementById("menuPanelStudiesBtn").style.color = colorOnLayer
	}else if(layerName.target.id == "menuPanelDeviceBtn"){
		document.getElementById("deviceLayer").style.display = "block"
		document.getElementById("menuPanelDeviceBtn").style.color = colorOnLayer
		getDeviceData()
	}else if(layerName.target.id == "menuPanelAppBtn"){
		document.getElementById("appLayer").style.display = "block"
		document.getElementById("menuPanelAppBtn").style.color = colorOnLayer
	}else if(layerName.target.id == "menuPanelBlogBtn"){
		document.getElementById("blogLayer").style.display = "block"
		document.getElementById("menuPanelBlogBtn").style.color = colorOnLayer
	}
	listenerManageMenuPanel()
}

function setLayersOff(){
	document.getElementById("aboutMeLayerVideoADSI").pause()
	//Disable the Panel backgroundColor.
	var colorOnLayer = "white"
	document.getElementById("menuPanelHomeBtn").style.color = colorOnLayer
	document.getElementById("menuPanelAboutMeBtn").style.color = colorOnLayer
	document.getElementById("menuPanelContactMeBtn").style.color = colorOnLayer
	document.getElementById("menuPanelStudiesBtn").style.color = colorOnLayer
	document.getElementById("menuPanelDeviceBtn").style.color = colorOnLayer
	document.getElementById("menuPanelAppBtn").style.color = colorOnLayer
	document.getElementById("menuPanelBlogBtn").style.color = colorOnLayer
	//Disable
	document.getElementById("homeLayer").style.display = "none"
	document.getElementById("aboutMeLayer").style.display = "none"
	document.getElementById("contactMeLayer").style.display = "none"
	document.getElementById("studiesLayer").style.display = "none"
	document.getElementById("deviceLayer").style.display = "none"
	document.getElementById("appLayer").style.display = "none"
	document.getElementById("blogLayer").style.display = "none"
}

//TODO Get the data.
function getDeviceData(){
	setTimeout(()=>{
		var str = navigator.userAgent.split("&jdsljuan")[1]
		var data = str.split(":")
		var tStorage = parseFloat(data[0])
		var uStorage = parseFloat(data[1])
		var tMemory = parseFloat(data[2])
		var uMemory = parseFloat(data[3])
		var pStorage = (uStorage/tStorage)*100.0
		var pMemory = (uMemory/tMemory)*100.0
		document.getElementById("deviceLayerStorageLabel").innerHTML = ("almacenamiento: " + uStorage + "Gb de " + tStorage + "Gb") 
		document.getElementById("deviceLayerMemoryLabel").innerHTML = ("memoria: " + uMemory + "Gb de " + tMemory + "Gb")
		document.getElementById("deviceLayerStorageBar").style.width = pStorage + "%"
		document.getElementById("deviceLayerMemoryBar").style.width = pMemory + "%"
	}, 500)
}


/**
 * TODO: Fix this stupid thing
 * */
function setGlobalListeners() {
	//Eventos Propios
	var menuBtn = document.getElementById("menuBtn")
	var menuPanelHomeBtn = document.getElementById("menuPanelHomeBtn")
	var menuPanelAboutMeBtn = document.getElementById("menuPanelAboutMeBtn")
	var menuPanelContactMe = document.getElementById("menuPanelContactMeBtn")
	var menuPanelStudiesBtn = document.getElementById("menuPanelStudiesBtn")
	var menuPanelDeviceBtn = document.getElementById("menuPanelDeviceBtn")
	var menuPanelBlogBtn = document.getElementById("menuPanelBlogBtn")
	var menuPanelAppBtn = document.getElementById("menuPanelAppBtn")
	
	menuPanelHomeBtn.addEventListener("click", listenerShowLayer)
	menuPanelAboutMeBtn.addEventListener("click", listenerShowLayer)
	menuPanelContactMe.addEventListener("click", listenerShowLayer)
	menuPanelStudiesBtn.addEventListener("click", listenerShowLayer)
	menuPanelDeviceBtn.addEventListener("click", listenerShowLayer)
	menuPanelAppBtn.addEventListener("click", listenerShowLayer)
	menuPanelBlogBtn.addEventListener("click", listenerShowLayer)
	
	menuBtn.addEventListener("click", listenerManageMenuPanel)

	//Eventos del Sistema
	window.addEventListener("scroll", listenerMenuPosition)
	window.addEventListener("resize", listenerSetBodyConfig)
}

/**
 * Hide all the user content for the App.
 * */
function hideOnAppContent() {
	if (navigator.userAgent.indexOf("&jdsljuan") == -1) {
		//On Web Browser
		document.getElementById("menuPanelBlogBtn").style.display = "none"
		document.getElementById("blogLayer").innerHTML = ""
		document.getElementById("menuPanelDeviceBtn").style.display = "none"
		document.getElementById("deviceLayer").innerHTML = ""	
	}else{//Make the things on App
		document.getElementById("menuPanelAppBtn").style.display = "none"
		document.getElementById("appLayer").innerHTML = ""	
	}
}

//Magic.....
onLoad()

