function regist() {
    if ($('#telephone').val() == '' || $('#telephone').val() == null) {
        alert("手机号码不能为空！");
        return
    }
    if ($('#username').val() == '' || $('#username').val() == null) {
        alert("姓名不能为空！");
        return
    }
    if ($('#password').val() == '' || $('#password').val() == null) {
        alert("密码不能为空！");
        return
    }
    if ($('#institution option:selected').val() == '0' || $('#institution option:selected').val() == null) {
        alert("请选择机构！");
        return
    }

    if ($('#term_of_validity').val() == '' || $('#term_of_validity').val() == null) {
        alert("有效期不能为空！");
        return
    }



    let user_type = $("#usertype option:selected").val();
    let user_level = $("#userlevel option:selected").val();
    let telephone = $("#telephone").val();
    let username = $("#username").val();
    let password = $("#password").val();
    let organization_name = $("#institution option:selected").val();
    let term_of_validity = $("#term_of_validity").val();

    let dataJson = new Object();
    dataJson.user_type = user_type;
    dataJson.user_level = user_level;
    dataJson.telephone = telephone;
    dataJson.username = username;
    dataJson.password = password;
    dataJson.organization_id = organization_name;
    dataJson.term_of_validity = term_of_validity;

    registerUser(JSON.stringify(dataJson));
}

function registerUser(data) {
    $.ajax({
        type: "POST",
        url: ctxPath + "user/addUserInfo",
        dataType: 'json',
        data: data,
        contentType: 'application/json;charset=utf-8',
        success: function (res) {
            let code = res.code;
            if (code == 200) {
                alert("添加成功");
                location.href = ctxPath + "user/list";
            } else {
                alert("添加失败");
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });
    // $.ajax({
    //     type: "POST",
    //     url: ctxPath + "user/addUserInfo",
    //     dataType: "json",
    //     contentType: 'application/json;charset=utf-8',
    //     data: data,
    //     success: function (res) {
    //         console.log(res)
    //         // if (data.code == 1) {
    //         //     alert('添加成功！')
    //         //     location.href = "list";
    //         // } else {
    //         //     alert('添加失败！')
    //         // }
    //     },
    //     error: function (err) {
    //         console.log(err);
    //     }
    // });
}