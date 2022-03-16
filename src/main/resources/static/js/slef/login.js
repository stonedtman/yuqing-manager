$("body").keydown(function (event) {
    if (event.keyCode == "13") {
        $(".ladda-button").click();
    }
});

$('.btn').click(function () {
    var telephone = $("#username").val();
    var password = $("#password").val();
    if (telephone == null || telephone == '') {
        alert("用户名不能为空！")
        return;
    }
    if (password == null || password == '') {
        alert("密码不能为空！")
        return;
    }

    $.ajax({
        type: "POST",
        url: ctxPath + "verifyacount",
        data: {
            telephone: telephone,
            password: password
        },
        dataType: 'json',
        success: function (res) {
            let code = res.code;
            let msg = res.msg;
            if (code == 200) {
                window.location.href = ctxPath + "company/list";
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

