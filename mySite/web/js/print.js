var container = document.querySelector('.content');
let index = 0
function writing(dom) {
    if (index < data.length) {
        console.log(index)
        dom.innerHTML += data[index]
        if(data[index] == ' '){
            dom.innerHTML += '<br>'
            index++
            setTimeout(writing.bind(this), 500, dom)
        }
        else{
            index++
            setTimeout(writing.bind(this), 200, dom)
        }
        
    }
}

data = '把想念写成信 让晚风寄送给你 降落在你心底 轻声的低语 是我关切的语气 压抑的秘密 你是我挥之不去的惦记'.split('')


writing(container);
