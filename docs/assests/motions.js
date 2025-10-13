function changeMode() {
    let container = document.getElementById('jcontainer');
    let styleStatus = window.getComputedStyle(container).getPropertyValue('filter');
    if (styleStatus == 'invert(1)') {
        container.style.filter = 'none';
    } else {
        container.style.filter = 'invert(1)';
    }
}

function changeFontSize(increment) {
    let container = document.getElementById('jcontainer');
    let styleStatus = window.getComputedStyle(container).getPropertyValue('font-size');
    container.style.fontSize = (parseInt(styleStatus.split('px')[0]) + increment) + 'px';
}

const btnDark = document.getElementById('btn-dark-mode');
btnDark.addEventListener('click', (event) => { changeMode() });

const btnHome = document.getElementById('btn-home');
btnHome.addEventListener('click', (event) => { window.location = '../index.html' });

const btnFontDecrease = document.getElementById('btn-font-decrease');
btnFontDecrease.addEventListener('click', (event) => { changeFontSize(-1) });

const btnFontIncrease = document.getElementById('btn-font-increase');
btnFontIncrease.addEventListener('click', (event) => { changeFontSize(1) });