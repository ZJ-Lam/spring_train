/**
 * 全局方法
 */


/**
 * 打开新的窗口
 * @param url 地址
 * @param name 窗口名字
 * @param w  窗口宽度
 * @param h  窗口高度
 * 如：openwinx(url,"submsgwin",960,860);
 */
function openwinx(url,name,w,h,custTop,custLeft)
{
    var top=20;
    var left=400;
    if(custTop&&custTop>0){
        top=custTop;
    }
    if(custLeft&&custLeft>0){
        left=custLeft;
    }

    window.open(url,name,"top="+top+",left="+left+",width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}

/**
 * 关闭当前窗口
 */
function closeWin(){
    window.opener=null;
    window.open('','_self');
    window.close();
}

/**
 *添加成功后关闭窗口
 */
function countterToCloseWin(timeout,msg){
    $("body").html('<h3 style="width: 441px;color:green;font-weight:bold;position:absolute;left:0;right:0;top: 300px;margin:auto;text-align: center;">'+msg+','+timeout+'秒后关闭窗口</h3>');
    timeout--;
    if (timeout == 0) {
        if(window.opener.addorupdatecontesti18nwin){
            window.opener.addorupdatecontesti18nwin.location.reload(true);
        }else{
            window.opener.location.reload(true);
        }

        window.opener=null;
        window.open('','_self');
        window.close();
    }
    else {
        setTimeout("countterToCloseWin()", 1000);
    }
}

/**
 * 验证当前元素的值是否为网址
 * @param e 当前元素
 * @returns {boolean}
 */
function validateURLAddr(e){
    if($(e).val().startsWith("http://")||$(e).val().startsWith("https://")){
        return true;
    }else{
        alert("请输入http://或者https://开头的网址");
        $(e).focus().select();
        return false;
    }
}
