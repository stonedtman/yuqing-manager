//提交，打包数据，发送到后台
function update() {
    /*if ($('#telephone').val() == '' || $('#telephone').val() == null) {
        alert("手机号码不能为空！");
        return
    }*/
    if ($('#username').val() == '' || $('#username').val() == null) {
        alert("姓名不能为空！");
        return
    }
   /* if ($('#password').val() == '' || $('#password').val() == null) {
        alert("密码不能为空！");
        return
    }*/
    if ($('#institution').val() == '0' || $('#institution').val() == null) {
        alert("请选择机构！");
        return
    }
  /*  if ($('#institution_short').val() == '' || $('#institution_short').val() == null) {
        alert("机构简称不能为空！");
        return
    }*/
    if ($('#term_of_validity').val() == '' || $('#term_of_validity').val() == null) {
        alert("有效期不能为空！");
        return
    }
   /* if ($('#system_title').val() == '' || $('#system_title').val() == null) {
        alert("系统名称不能为空！");
        return
    }*/

    let userData = new Object();



    userData.telephone = data.user.telephone;

    userData.user_type = $("#usertype option:selected").val();

    userData.user_level = $("#userlevel option:selected").val();

    userData.username = $("#username").val();

    userData.organization_id = $("#institution option:selected").val();

    userData.term_of_validity = $("#term_of_validity").val();

    userData.oldTime = oldTime;
    if (userData.term_of_validity <= 0){
        userData.term_of_validity = '0';
    }



    //发送请求
    $.ajax({
        type: "post",
        url: "/user/updateUserByTelephone",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(userData),

        success : function (res) {


            if (res.message == "修改成功"){
                alert(res.message);
                location.href= "/user/list";
            }else{
                alert(res.message)}

        },
        error: function (res) {
            alert("出错了")
        }
    });
}

/*
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
}*/
