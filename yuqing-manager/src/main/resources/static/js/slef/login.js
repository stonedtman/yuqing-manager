$("body").keydown(function (event) {
    if (event.keyCode == "13") {
        $(".ladda-button").click();
    }
});

var captchaImg = document.getElementById("graph_img");
// 为验证码图片绑定点击事件
captchaImg.onclick = function() {
    // 生成新的时间戳
    var timestamp = new Date().getTime();
    // 替换验证码图片的src属性
    captchaImg.src = ctxPath + "code?_t=" + timestamp;
};

$('.btn').click(function () {
    var telephone = $("#username").val();
    var password = $("#password").val();
    var code = $("#code").val();
    if (telephone == null || telephone == '') {
        alert("用户名不能为空！")
        return;
    }
    if (password == null || password == '') {
        alert("密码不能为空！")
        return;
    }
    if (code == null || code == '') {
        alert("验证码不能为空！")
        return;
    }

    $.ajax({
        type: "POST",
        url: ctxPath + "verifyacount",
        data: {
            telephone: telephone,
            password: password,
            code: code
        },
        dataType: 'json',
        success: function (res) {
            let code = res.code;
            let msg = res.msg;
            if (code == 200) {
                window.location.href = ctxPath + "home";
            } else if (code == 500) {
                alert(msg);
            } else if (code == 201) {
                alert(msg);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
});

