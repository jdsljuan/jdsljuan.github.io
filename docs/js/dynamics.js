
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
	console.log(layerName.target.id)
	if (layerName.target.id == "menuPanelHomeBtn") {
		document.getElementById("homeLayer").style.display = "block"
		document.getElementById("aboutMeLayer").style.display = "none"
		document.getElementById("contactMeLayer").style.display = "none"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelAboutMeBtn"){
		document.getElementById("homeLayer").style.display = "none"
		document.getElementById("aboutMeLayer").style.display = "block"
		document.getElementById("contactMeLayer").style.display = "none"
		listenerManageMenuPanel()
	}else if(layerName.target.id == "menuPanelContactMeBtn"){
		document.getElementById("homeLayer").style.display = "none"
		document.getElementById("aboutMeLayer").style.display = "none"
		document.getElementById("contactMeLayer").style.display = "block"
		listenerManageMenuPanel()
	}
}

//Eventos Propios
var menuBtn = document.getElementById("menuBtn")
var menuPanelHomeBtn = document.getElementById("menuPanelHomeBtn")
var menuPanelAboutMeBtn = document.getElementById("menuPanelAboutMeBtn")
var menuPanelContactMe = document.getElementById("menuPanelContactMeBtn")

menuPanelHomeBtn.addEventListener("click", listenerShowLayer, "home")
menuPanelAboutMeBtn.addEventListener("click", listenerShowLayer, "about")
menuPanelContactMe.addEventListener("click", listenerShowLayer, "contact")

menuBtn.addEventListener("click", listenerManageMenuPanel)
//Eventos del Sistema
window.addEventListener("scroll", listenerMenuPosition)
window.addEventListener("resize", listenerSetBodyConfig)

onLoad()
