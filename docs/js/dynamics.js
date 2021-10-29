
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

function onLoad() {
	window.scrollTo(0,0)
	listenerSetBodyConfig()
	listenerManageMenuPanel()
	getDeviceData()
}


function listenerManageMenuPanel() {
	var menuPanel = document.getElementById("menuPanel")
	var menuPanelCS = window.getComputedStyle(menuPanel)
	var menuPanelCSOpacity = menuPanelCS.opacity
	console.log("Last State: " + menuPanelCSOpacity)
	if(menuPanelCSOpacity == 1){
		document.getElementById("menuPanel").style.opacity = "0"
		document.getElementById("menuPanel").style.zIndex = -1;
	}else{
		document.getElementById("menuPanel").style.zIndex = 1
		document.getElementById("menuPanel").style.opacity = "1"
	}
}

function listenerShowLayer(layerName) {
	document.getElementById("aboutMeLayerVideoADSI").pause()
	if (layerName.target.id == "menuPanelHomeBtn") {
		setLayersOff()
		document.getElementById("homeLayer").style.display = "block"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelAboutMeBtn"){
		setLayersOff()
		document.getElementById("aboutMeLayer").style.display = "block"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelContactMeBtn"){
		setLayersOff()
		document.getElementById("contactMeLayer").style.display = "block"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelStudiesBtn"){
		setLayersOff()
		document.getElementById("studiesLayer").style.display = "block"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelDeviceBtn"){
		setLayersOff()
		document.getElementById("deviceLayer").style.display = "block"
		listenerManageMenuPanel()
	}
}

function setLayersOff(){
	document.getElementById("homeLayer").style.display = "none"
	document.getElementById("aboutMeLayer").style.display = "none"
	document.getElementById("contactMeLayer").style.display = "none"
	document.getElementById("studiesLayer").style.display = "none"
	document.getElementById("deviceLayer").style.display = "none"
}

function getDeviceData(){
	document.getElementById("deviceLayerUserAgent").innerHTML += navigator.userAgent
	document.getElementById("deviceLayerWidth").innerHTML += window.innerWidth
	document.getElementById("deviceLayerHeight").innerHTML += window.innerHeight
	document.getElementById("deviceLayerViewport").innerHTML += document.documentElement.clientWidth

}

//Eventos Propios
var menuBtn = document.getElementById("menuBtn")
var menuPanelHomeBtn = document.getElementById("menuPanelHomeBtn")
var menuPanelAboutMeBtn = document.getElementById("menuPanelAboutMeBtn")
var menuPanelContactMe = document.getElementById("menuPanelContactMeBtn")
var menuPanelStudiesBtn = document.getElementById("menuPanelStudiesBtn")
var menuPanelDeviceBtn = document.getElementById("menuPanelDeviceBtn")

menuPanelHomeBtn.addEventListener("click", listenerShowLayer)
menuPanelAboutMeBtn.addEventListener("click", listenerShowLayer)
menuPanelContactMe.addEventListener("click", listenerShowLayer)
menuPanelStudiesBtn.addEventListener("click", listenerShowLayer)
menuPanelDeviceBtn.addEventListener("click", listenerShowLayer)

menuBtn.addEventListener("click", listenerManageMenuPanel)
//Eventos del Sistema
window.addEventListener("scroll", listenerMenuPosition)
window.addEventListener("resize", listenerSetBodyConfig)

onLoad()
