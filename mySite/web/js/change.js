window.onload=function(){
    var button = document.querySelector(".change");
    //按钮触发事件
    button.onclick = function () {
        var ajax = new XMLHttpRequest();//异步请求
        ajax.onreadystatechange = function(){
            if(ajax.readyState==4){
                if(ajax.status==200){
                    //只有当ajax.readyState==4 和 ajax.status==200时
                    //说明数据已经安全返回
                    console.log(ajax.response);
                    document.querySelector("#oneWord").innerHTML = ajax.response;
                    //用div获取返回的内容
                }
            }
        }
        ajax.open("get","https://v1.hitokoto.cn?encode=text&c=i");
        ajax.send();
    }
    button.click();
}

 