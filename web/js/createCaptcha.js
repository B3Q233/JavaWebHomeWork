
const canvas = document.querySelector("canvas");
const ctx = canvas.getContext("2d");
canvas.width = 900;
canvas.height = 600;
canvas.style.backgroundColor="#393c44";
canvas.style.border="1px solid #000000";
canvas.style.borderRadius = "3px";
const width = canvas.width;
const height = canvas.height;



//生成随机数
function getRandomNumber(N, M) {
    if (N > M) {
        let tmp = N;
        N = M;
        M = N;
    }
    return Math.floor(Math.random() * (M - N + 1)) + N;
}

// 生成随机颜色
function getRandomColor(flag = true) {
    if (flag) {
        let color = "#";
        for (let i = 1; i <= 6; i++) {
            let tmpChar = Math.floor(Math.random() * 16);
            if (tmpChar <= 9) color += tmpChar;
            else color += String.fromCharCode('a'.charCodeAt(0) + tmpChar - 10);
        }
        return color;
    } else {
        let red = Math.floor(Math.random() * 256);
        let green = Math.floor(Math.random() * 256);
        let blue = Math.floor(Math.random() * 256);
        return `rgb(${red},${green},${blue})`
    }
}

//绘制坐标轴
function drawAxoi() {
    const ctx = canvas.getContext("2d");
    ctx.beginPath();
    ctx.moveTo(0, 301);
    ctx.lineTo(900, 301);
    ctx.strokeStyle = "teal";
    ctx.stroke();

    ctx.moveTo(450, 0);
    ctx.lineTo(450, 600);
    ctx.stroke();

    ctx.fillRect(445, 295, 10, 10);
}

//绘制验证码
function drawCode(ctx, str) {
    let minFontSize = Math.floor(300 * (1 - (str.length - 4) / 10));
    let maxFontSize = Math.floor(380 * (1 - (str.length - 4) / 10));
    let interval = Math.floor(width / str.length);
    let intervalLeft = 0;
    let intervalRight = interval;
    for (let i in str) {
        let color = getRandomColor();
        let fontSize = getRandomNumber(minFontSize, maxFontSize);
        let left = intervalLeft;
        let right = Math.max(intervalLeft, intervalRight - Math.floor(Math.sqrt(height)) * 10);
        let top = Math.floor(Math.sqrt(height)) * 10;
        let bottom = height - 4 * Math.floor(Math.sqrt(height));
        let x = getRandomNumber(left, right);
        let y = getRandomNumber(top, bottom);
        intervalLeft += interval;
        intervalRight += interval;
        drawChar(str[i], x, y, color, fontSize);
    }
}

//绘制单个字符
function drawChar(char, x, y, color, fontSize) {
    ctx.font = `${fontSize}px "微软雅黑`;
    ctx.fillStyle = color;
    ctx.textBaseline = "alphabetic";
    ctx.fillText(char, x, y);
}

//绘制函数
function drawCaptcha(str){
    ctx.clearRect(0,0,width,height);
    drawCode(ctx, str);
}
